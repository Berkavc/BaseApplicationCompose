package com.task.movieapp.common.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.SystemClock
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:throttleClick")
fun View.clickWithThrottle(action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0
        private val throttleTime = 1250L
        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < throttleTime) {
                return
            } else {
                action()
            }
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}

fun convertDateFormatToLong(date: String): Long {
    val formatter = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    val dateTime = formatter.parse(date) as Date
    return dateTime.time
}

fun convertLongToDateFormat(date: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    val formattedDate = dateFormat.format(date)
    val day = formattedDate.substringBefore("/")
    val year = formattedDate.substringAfterLast("/")
    return getMonth(date) + " " + day + ", " + year + " ."
}

private fun getMonth(date: Long): String {
    val dateFormat = SimpleDateFormat("MMM", Locale.getDefault())
    dateFormat.isLenient = false
    return dateFormat.format(date)
}

fun sendToHyperLink(activity: Activity, movieId: String, movieDetailId: String) {
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    intent.data = Uri.parse(movieDetailId + movieId)
    activity.startActivity(intent)
}

fun shareUrl(activity: Activity, title: String, body: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, body)
    startActivity(activity, Intent.createChooser(intent, title), null)
}



