package com.example.bin

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import com.example.bin.databinding.CardMetadataItemBinding

class CardMetadataAdapter(private val context: Context) :
    RecyclerView.Adapter<CardMetadataAdapter.ViewHolder>() {
    var dataSet: List<CardMetadata> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newDataSet) {
            field = newDataSet
            notifyDataSetChanged()
        }

    class ViewHolder(view: View, private val context: Context) : RecyclerView.ViewHolder(view) {
        private val binding = CardMetadataItemBinding.bind(view)

        fun bind(cardMetadata: CardMetadata, position: Int) = with(binding) {
            "BIN: ${cardMetadata.bin}".also { binTextView.text = it }
            schemeNetworkTextView.text = cardMetadata.scheme
            brandTextView.text = cardMetadata.brand
            lengthTextView.text =
                if (cardMetadata.length == null) "" else cardMetadata.length.toString()
            luhnTextView.text = cardMetadata.booleanToString(cardMetadata.luhn)
            typeTextView.text = cardMetadata.type
            prepaidTextTitle.text = cardMetadata.booleanToString(cardMetadata.prepaid)

            countryTextView.text = cardMetadata.country
            if (cardMetadata.latitude == null && cardMetadata.longitude == null) {
                latlongTextView.text = ""
            } else {
                "(latitude: ${cardMetadata.latitude}, longitude: ${cardMetadata.longitude})".also {
                    val spanLatLong = it.toSpannable()
                    spanLatLong.setSpan(
                        UnderlineSpan(), 0, it.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    latlongTextView.text = spanLatLong
                    latlongTextView.setOnClickListener {
                        val uri = "geo:${cardMetadata.latitude},${cardMetadata.longitude}"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                        context.startActivity(intent)
                    }
                }
            }

            bankNameTextView.text =
                if (cardMetadata.bankName == null) ""
                else cardMetadata.bankName + if (cardMetadata.city != null) "," else ""

            cityTextView.visibility = if (cardMetadata.city == null) View.GONE else View.VISIBLE
            cityTextView.text = cardMetadata.city
            bankUrlTextView.text = cardMetadata.bankURL
            bankPhoneTextView.text = cardMetadata.bankPhoneNumber

            if (position == 0) expandableLayout.visibility = View.VISIBLE
            binConstraintLayout.setOnClickListener {
                if (expandableLayout.visibility == View.VISIBLE) {
                    expandableLayout.visibility = View.GONE
                } else if (expandableLayout.visibility == View.GONE) {
                    expandableLayout.visibility = View.VISIBLE
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.card_metadata_item, parent, false),
            context
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }
}