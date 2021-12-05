package com.task.movieapp.common.utils

import android.app.Activity
import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

import android.content.Intent
import android.net.Uri
import com.task.movieapp.domain.BASE_URL
import com.task.movieapp.domain.MOVIE
import com.task.movieapp.domain.MOVIE_DETAIL_URL


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

fun convertDateFormatToLong(date:String):Long{
    val formatter = SimpleDateFormat("yyyy-mm-dd" , Locale.getDefault())
    val dateTime = formatter.parse(date) as Date
    return dateTime.time
}
fun convertLongToDateFormat(date: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    dateFormat.isLenient = false
    val formattedDate  = dateFormat.format(date)
    val day = formattedDate.substringBefore("/")
    val year = formattedDate.substringAfterLast("/")
    return getMonth(date) + " " + day + ", " +  year + " ."
}


private fun getMonth(date: Long):String{
    val dateFormat = SimpleDateFormat("MMM" , Locale.getDefault())
    dateFormat.isLenient = false
    return dateFormat.format(date)
}

fun sendToHyperLink(activity:Activity, movieId:String){
    val intent = Intent()
    intent.action = Intent.ACTION_VIEW
    intent.addCategory(Intent.CATEGORY_BROWSABLE)
    intent.data = Uri.parse(MOVIE_DETAIL_URL  + movieId)
    activity.startActivity(intent)
}



