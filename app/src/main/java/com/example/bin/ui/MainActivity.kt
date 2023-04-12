package com.example.bin.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bin.R
import com.example.bin.databinding.ActivityMainBinding
import com.example.bin.ui.adapter.CardMetadataAdapter
import com.example.bin.ui.viewmodel.CardMetadataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val cardMetadataAdapter = CardMetadataAdapter(this)
    private val viewModel: CardMetadataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cardMetadataDataset.collect {
                    cardMetadataAdapter.dataSet = it
                }
            }
        }


        recyclerViewInit()
        sendButtonClickListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.clear_history) viewModel.deleteAllHistory()
        return super.onOptionsItemSelected(item)
    }

    private fun sendButtonClickListener() {
        binding.sendButton.setOnClickListener {
            if (TextUtils.isEmpty(binding.binET.text)) {
                binding.binET.error = "This field cannot be empty"
            } else {
                viewModel.requestCardMetadataByBIN(binding.binET.text.toString().toInt())
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(
                    binding.sendButton.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        }
    }

    private fun recyclerViewInit() {
        val linearLayoutManager = LinearLayoutManager(this).apply {
            reverseLayout = true
            stackFromEnd = true
        }
        binding.requestHistoryRecyclerView.layoutManager = linearLayoutManager
        binding.requestHistoryRecyclerView.adapter = cardMetadataAdapter
    }

}