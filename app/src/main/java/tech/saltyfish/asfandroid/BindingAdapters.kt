package tech.saltyfish.asfandroid

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import tech.saltyfish.asfandroid.network.Bot
import tech.saltyfish.asfandroid.overview.BotGridAdapter

/*
binding adapter for bot in overview fragment
 */

@BindingAdapter("botListData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Bot>?) {
    val adapter = recyclerView.adapter as BotGridAdapter
    adapter.submitList(data)
}

/*
binding adapter for user Avatar
using glide
 */

@BindingAdapter("userAvatarHash")
fun bindImage(imageView: ImageView, userAvatarHash: String?) {

    // for handle the userAvatarHash == null (" ")
    // if userAvatarHash == null it will be transformed to " "
    userAvatarHash?.let {
        val imgUrl = if (userAvatarHash == " ")

        // this is a "?" sign picture
            "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/0b/0b46945851b3d26da93a6ddba3ac961206cc191d_full.jpg"
        else
        // picture of user
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

/*
binding adapter for game photo
using glide
 */

@BindingAdapter("gamePicID")
fun bindImage(imageView: ImageView, gamePicID: Int?) {
    gamePicID?.let {
        val imgUrl =
            "https://media.st.dl.eccdnx.com/steam/apps/$gamePicID/header.jpg"
        // e.g.  https://media.st.dl.eccdnx.com/steam/apps/978520/header.jpg


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
