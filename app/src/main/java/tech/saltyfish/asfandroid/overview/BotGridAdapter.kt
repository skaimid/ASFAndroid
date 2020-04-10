package tech.saltyfish.asfandroid.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.saltyfish.asfandroid.MainActivity.Companion.context
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.BotItemBinding
import tech.saltyfish.asfandroid.network.Bot

class BotGridAdapter(private val onClickListener: OnClickListener) : ListAdapter<Bot,
        BotGridAdapter.BotPropertyViewHolder>(DiffCallback) {


    class BotPropertyViewHolder(private var binding: BotItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(botProperty: Bot) {
            binding.botProperty = botProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
            binding.timeRemaining.text = when (botProperty.cardsFarmer.timeRemaining) {
                "00:00:00" -> context?.getText(R.string.farm_complete_text) ?: "complete"
                else -> context?.getString(
                    R.string.Farming_text,
                    botProperty.cardsFarmer.timeRemaining
                )
            }
            if (botProperty.botConfig.enabled) {
                if (botProperty.isConnectedAndLoggedOn) {
                    if (botProperty.cardsFarmer.paused) {
                        binding.botStatus.text = context?.getText(R.string.pause_text) ?: "Paused"
                    } else {
                        binding.botStatus.text = context?.getText(R.string.online_text) ?: "Online"
                    }
                } else {
                    binding.botStatus.text = context?.getText(R.string.offline_text) ?: "Offline"
                }
            } else {
                binding.botStatus.text = context?.getText(R.string.disable_text) ?: "Disabled"
            }


        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Bot>() {
        override fun areItemsTheSame(oldItem: Bot, newItem: Bot): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Bot, newItem: Bot): Boolean {
            return oldItem.botName == newItem.botName
        }
    }


    override fun onBindViewHolder(holder: BotPropertyViewHolder, position: Int) {
        val botsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(botsProperty)
        }
        holder.bind(botsProperty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotPropertyViewHolder {
        return BotPropertyViewHolder(BotItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    class OnClickListener(val clickListener: (botProperty: Bot) -> Unit) {
        fun onClick(botProperty: Bot) = clickListener(botProperty)
    }

}
