package com.android.developer.cleanapparchitecture.presentation.adapter

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.developer.cleanapparchitecture.R
import com.android.developer.cleanapparchitecture.domain.MarsProperty
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/*@BindingAdapter("listData")
fun RecyclerView.bindRecyclerView(data: List<MarsProperty>?) {
    val adapter = this.adapter as MarsRecyclerAdapter
    adapter.submitList(data)
}*/

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun ImageView.bindImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(this)
    }
}