package com.sakr.prayertimesapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat





fun Int.toBitmap(context: Context, @ColorRes tintColor: Int? = null): Bitmap? {

    val drawable = ContextCompat.getDrawable(context, this) ?: return null
    drawable.setBounds(0, 0, 100, 100)
    val bm = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888)

    // add the tint if it exists
    tintColor?.let {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
    }
    // draw it onto the bitmap
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return bm
}

fun Int.toBitmapHuge(context: Context, @ColorRes tintColor: Int? = null): Bitmap? {

    val drawable = ContextCompat.getDrawable(context, this) ?: return null
    drawable.setBounds(0, 0, 400, 400)
    val bm = Bitmap.createBitmap(400,400, Bitmap.Config.ARGB_8888)

    // add the tint if it exists
    tintColor?.let {
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, it))
    }
    // draw it onto the bitmap
    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return bm
}