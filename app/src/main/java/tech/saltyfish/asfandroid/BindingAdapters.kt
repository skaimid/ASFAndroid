package tech.saltyfish.asfandroid

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tech.saltyfish.asfandroid.network.Bot
import tech.saltyfish.asfandroid.overview.BotGridAdapter

@BindingAdapter("botListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Bot>?) {
    val adapter = recyclerView.adapter as BotGridAdapter
    adapter.submitList(data)
}

@BindingAdapter("userAvatarHash")
fun bindImage(imageView: ImageView, userAvatarHash: String?) {
    userAvatarHash?.let {
        val imgUrl =
            "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/8f/" + userAvatarHash + "_full.jpg"
        Glide.with(imageView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imageView)
    }
}

