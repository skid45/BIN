package com.example.bin.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.UnderlineSpan
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout.LayoutParams
import androidx.core.text.toSpannable
import com.example.bin.data.local.entities.CardMetadata
import com.example.bin.data.network.model.CardMetadataFromJSON
import com.example.bin.ui.model.CardMetadataItem

fun Boolean?.booleanToString(): String {
    return when (this) {
        true -> "Yes"
        false -> "No"
        else -> ""
    }
}

fun String.toSpannableWithSpan(): Spannable {
    val spannable = this.toSpannable()
    spannable.setSpan(UnderlineSpan(), 0, this.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}

fun Double.toStringWithCheckRemain(): String {
    return if (this % 1.0 == .0) this.toInt().toString() else this.toString()
}

fun CardMetadataFromJSON.asCardMetadata(bin: Int) = CardMetadata(
    bin = bin,
    scheme = scheme?.replaceFirstChar(Char::uppercase),
    brand = brand?.replaceFirstChar(Char::uppercase),
    length = number?.length,
    luhn = number?.luhn,
    type = type?.replaceFirstChar(Char::uppercase),
    prepaid = prepaid,
    country = country?.name,
    latitude = country?.latitude,
    longitude = country?.longitude,
    bankName = bank?.name,
    city = bank?.city,
    bankURL = bank?.url,
    bankPhoneNumber = bank?.phone
)

fun CardMetadata.asCardMetadataItem() = CardMetadataItem(
    id = id!!,
    bin = "BIN: $bin",
    scheme = scheme ?: "",
    brand = brand ?: "",
    length = length?.toString() ?: "",
    luhn = luhn.booleanToString(),
    type = type ?: "",
    prepaid = prepaid.booleanToString(),
    country = country ?: "",
    coordinates = if (latitude == null || longitude == null) SpannableString("")
    else ("(latitude: ${latitude.toStringWithCheckRemain()}, " +
            "longitude: ${longitude.toStringWithCheckRemain()})").toSpannableWithSpan(),
    latitude = latitude,
    longitude = longitude,
    bankName = bankName ?: "",
    city = city ?: "",
    url = bankURL ?: "",
    phone = bankPhoneNumber ?: ""
)

fun View.expand() {
    val matchParentMeasureSpec =
        View.MeasureSpec.makeMeasureSpec((this.parent as View).width, View.MeasureSpec.EXACTLY)
    val wrapContentMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    this.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
    val targetHeight = this.measuredHeight

    this.layoutParams.height = 1
    this.visibility = View.VISIBLE
    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            this@expand.layoutParams.height =
                if (interpolatedTime == 1f) LayoutParams.WRAP_CONTENT
                else (targetHeight * interpolatedTime).toInt()
            this@expand.requestLayout()
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    animation.duration = (targetHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(animation)
}

fun View.collapse() {
    val initialHeight = this.measuredHeight

    this.layoutParams.height = 1
    this.visibility = View.VISIBLE
    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            if (interpolatedTime == 1f) this@collapse.visibility = View.GONE
            else {
                this@collapse.layoutParams.height =
                    initialHeight - (initialHeight * interpolatedTime).toInt()
                this@collapse.requestLayout()
            }
        }

        override fun willChangeBounds(): Boolean {
            return true
        }
    }

    animation.duration = (initialHeight / this.context.resources.displayMetrics.density).toLong()
    this.startAnimation(animation)
}

fun View.toggle() {
    if (this.visibility == View.VISIBLE) {
        this.collapse()
    } else if (this.visibility == View.GONE) {
        this.expand()
    }
}