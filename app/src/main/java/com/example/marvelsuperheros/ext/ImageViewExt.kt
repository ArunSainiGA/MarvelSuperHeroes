package com.example.marvelsuperheros.ext

import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.lang.Exception

inline fun ImageView.loadUrl(url: String, defaultDrawableResId: Int){
    val builder = Picasso.Builder(this.context)
    builder.listener { picasso, uri, exception -> Log.i("Picasso Ex", exception?.localizedMessage.toString()) }
    builder.build().load(url).error(defaultDrawableResId).placeholder(defaultDrawableResId).fit().into(this)
//    Picasso .get()
}