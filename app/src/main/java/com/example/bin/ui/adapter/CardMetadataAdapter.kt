package com.example.bin.ui.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bin.R
import com.example.bin.data.local.entities.CardMetadata
import com.example.bin.databinding.CardMetadataItemBinding
import com.example.bin.utils.asCardMetadataItem
import com.example.bin.utils.toggle


class CardMetadataAdapter(private val context: Context) :
    RecyclerView.Adapter<CardMetadataAdapter.ViewHolder>() {
    var dataSet: List<CardMetadata> = emptyList()
        set(newDataSet) {
            val diffCallback = CardMetadataDiffCallback(field, newDataSet)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newDataSet
            diffResult.dispatchUpdatesTo(this)
        }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = CardMetadataItemBinding.bind(view)

        fun bind(cardMetadata: CardMetadata, position: Int) = with(binding) {
            val cardMetadataItem = cardMetadata.asCardMetadataItem()
            binTextView.text = cardMetadataItem.bin
            schemeNetworkTextView.text = cardMetadataItem.scheme
            brandTextView.text = cardMetadataItem.brand
            lengthTextView.text = cardMetadataItem.length
            luhnTextView.text = cardMetadataItem.luhn
            typeTextView.text = cardMetadataItem.type
            prepaidTextView.text = cardMetadataItem.prepaid
            countryTextView.text = cardMetadataItem.country
            coordinatesTextView.text = cardMetadataItem.coordinates
            if (cardMetadataItem.latitude != null && cardMetadataItem.longitude != null) {
                coordinatesTextView.setOnClickListener {
                    val uri = "geo:${cardMetadataItem.latitude},${cardMetadataItem.longitude}"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                    context.startActivity(intent)
                }
            }
            bankNameTextView.text = cardMetadataItem.bankName
            cityTextView.text = cardMetadataItem.city
            bankUrlTextView.text = cardMetadataItem.url
            bankPhoneTextView.text = cardMetadataItem.phone

            if (position == itemCount - 1) expandableLayout.visibility = View.VISIBLE
            binTextView.setOnClickListener {
                expandableLayout.toggle()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.card_metadata_item, parent, false)
        )
    }

    override fun getItemCount(): Int = dataSet.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position], position)
    }

    class CardMetadataDiffCallback(
        private val oldDataSet: List<CardMetadata>,
        private val newDataSet: List<CardMetadata>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldDataSet.size

        override fun getNewListSize(): Int = newDataSet.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldDataSet[oldItemPosition].id == newDataSet[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldDataSet[oldItemPosition] == newDataSet[newItemPosition]
        }

    }
}