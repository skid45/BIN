package com.example.bin

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.bin.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val database by lazy { AppDatabase.getDB(this) }
    private val cardMetadataDao by lazy { database.getCardMetadataDao() }
    private val cardMetadataAdapter = CardMetadataAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardMetadataDao.getAllCardMetadata().asLiveData().observe(this) {
            cardMetadataAdapter.dataSet = it.reversed()
        }

        recyclerViewInit()
        sendButtonClickListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_history) lifecycleScope.launch {
            cardMetadataDao.deleteAllCardMetadata()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendButtonClickListener() {
        binding.sendButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.binET.text)) {
                binding.binET.error = "This field cannot be empty"
            } else {
                requestCardMetadataByBIN(binding.binET.text.toString().toInt())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    binding.sendButton.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    private fun recyclerViewInit() {
        binding.requestHistoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestHistoryRecyclerView.adapter = cardMetadataAdapter
    }

    private fun requestCardMetadataByBIN(bin: Int) {
        val url = getString(R.string.api_url) + bin
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            url,
            { response ->
                if (response.isNotBlank()) {
                    lifecycleScope.launch {
                        cardMetadataDao.insert(parseCardMetadataFromJson(bin, response))
                    }
                }
            },
            { error ->
                Toast.makeText(this, "BIN: \"$bin\" is not valid", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(stringRequest)
    }

    private fun parseCardMetadataFromJson(bin: Int, data: String): CardMetadata {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<CardMetadataFromJSON?> =
            moshi.adapter(CardMetadataFromJSON::class.java)
        val cardMetadata = adapter.fromJson(data)
        return CardMetadata(
            bin = bin,
            scheme = cardMetadata?.scheme?.replaceFirstChar(Char::uppercase),
            brand = cardMetadata?.brand?.replaceFirstChar(Char::uppercase),
            length = cardMetadata?.number?.length,
            luhn = cardMetadata?.number?.luhn,
            type = cardMetadata?.type?.replaceFirstChar(Char::uppercase),
            prepaid = cardMetadata?.prepaid,
            country = cardMetadata?.country?.name,
            latitude = cardMetadata?.country?.latitude,
            longitude = cardMetadata?.country?.longitude,
            bankName = cardMetadata?.bank?.name,
            city = cardMetadata?.bank?.city,
            bankURL = cardMetadata?.bank?.url,
            bankPhoneNumber = cardMetadata?.bank?.phone,
        )

    }
}