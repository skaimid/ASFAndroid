package tech.saltyfish.asfandroid.botStatus


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.GameItemBinding
import tech.saltyfish.asfandroid.network.Games

class FarmGameAdapter(private val onClickListener: OnClickListener) : ListAdapter<Games,
        FarmGameAdapter.GamePropertyViewHolder>(DiffCallback) {


    class GamePropertyViewHolder(private var binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(gameProperty: Games) {
            binding.gameProperty = gameProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()

            binding.botCardLeft.text = MainActivity.context?.getString(
                R.string.bot_card_left_text,
                gameProperty.cardsRemaining
            )
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Games>() {
        override fun areItemsTheSame(oldItem: Games, newItem: Games): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Games, newItem: Games): Boolean {
            return oldItem.appID == newItem.appID
        }
    }


    override fun onBindViewHolder(holder: GamePropertyViewHolder, position: Int) {
        val gameProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(gameProperty)
        }
        holder.bind(gameProperty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GamePropertyViewHolder {
        return GamePropertyViewHolder(GameItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    class OnClickListener(val clickListener: (gameProperty: Games) -> Unit) {
        fun onClick(gameProperty: Games) = clickListener(gameProperty)
    }
}
