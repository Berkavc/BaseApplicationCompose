package com.task.movieapp.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import kotlinx.coroutines.*
import com.bumptech.glide.request.target.Target
import timber.log.Timber

fun glideWithBitmap(
    context: Context,
    url: String?,
    imageView: ImageView,
    placeholder: Drawable? = null,
    errorPlaceHolder: Drawable? = null
) {
    try {
        val coroutineCallGlideWithBitmap = CoroutineScope(Dispatchers.IO)
        coroutineCallGlideWithBitmap.async {
            Glide.with(context)
                .asBitmap()
                .placeholder(placeholder)
                .error(errorPlaceHolder)
                .load(url)
                .listener(object : RequestListener<Bitmap> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Bitmap?,
                        model: Any?,
                        target: Target<Bitmap>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Timber.e("glide_resource_ready!!")
                        coroutineCallGlideWithBitmap.launch {
                            withContext(Dispatchers.Main) {
                                imageView.setImageBitmap(resource)
                            }
                        }
                        return false
                    }
                })
                .submit()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun glideWithDrawable(
    context: Context,
    url: String?,
    imageView: ImageView,
    placeholder: Drawable? = null,
    errorPlaceHolder: Drawable? = null
) {
    try {
        val coroutineCallWithDrawable = CoroutineScope(Dispatchers.IO)
        coroutineCallWithDrawable.async {
            val glide = GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(errorPlaceHolder)
//                    .listener(object : RequestListener<Drawable> {
//                        override fun onLoadFailed(
//                                e: GlideException?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                isFirstResource: Boolean
//                        ): Boolean {
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                                resource: Drawable?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                dataSource: DataSource?,
//                                isFirstResource: Boolean
//                        ): Boolean {
//                            Timber.e("glide_resource_ready!!")
////                            coroutineCallGlideWithDrawable.launch {
////                                withContext(Dispatchers.Main) {
////                                    imageView.setImageDrawable(resource)
////                                }
////                            }
//                            return false
//                        }
//                    })
            withContext(Dispatchers.Main) {
                glide.into(imageView)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

fun glideWithDrawableCircle(
    context: Context,
    url: String?,
    imageView: ImageView,
    placeholder: Drawable? = null,
    errorPlaceHolder: Drawable? = null
) {
    try {
        val coroutineCallWithDrawable = CoroutineScope(Dispatchers.IO)
        coroutineCallWithDrawable.async {
            val glide = GlideApp.with(context)
                .load(url)
                .circleCrop()
                .placeholder(placeholder)
                .error(errorPlaceHolder)
//                    .listener(object : RequestListener<Drawable> {
//                        override fun onLoadFailed(
//                                e: GlideException?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                isFirstResource: Boolean
//                        ): Boolean {
//                            return false
//                        }
//
//                        override fun onResourceReady(
//                                resource: Drawable?,
//                                model: Any?,
//                                target: Target<Drawable>?,
//                                dataSource: DataSource?,
//                                isFirstResource: Boolean
//                        ): Boolean {
//                            Timber.e("glide_resource_ready!!")
////                            coroutineCallGlideWithDrawable.launch {
////                                withContext(Dispatchers.Main) {
////                                    imageView.setImageDrawable(resource)
////                                }
////                            }
//                            return false
//                        }
//                    })
            withContext(Dispatchers.Main) {
                glide.into(imageView)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

fun glideWithListenerDrawable(
    context: Context,
    url: String?,
    imageView: ImageView,
    placeholder: Drawable? = null,
    errorPlaceHolder: Drawable? = null,
    uiAction: () -> Unit
) {
    try {
        val coroutineCallWithDrawable = CoroutineScope(Dispatchers.IO)
        coroutineCallWithDrawable.async {
            val glide = GlideApp.with(context)
                .load(url)
                .placeholder(placeholder)
                .error(errorPlaceHolder)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        Timber.e("glide_resource_ready!!")
                        coroutineCallWithDrawable.launch {
                            withContext(Dispatchers.Main) {
                                imageView.setImageDrawable(resource)
                                uiAction.invoke()
                            }
                        }
                        return false
                    }
                }).submit()

        }
    } catch (e: Exception) {
        e.printStackTrace()
    }


}

@GlideModule
internal class MovieAppAppGlideModule : AppGlideModule()