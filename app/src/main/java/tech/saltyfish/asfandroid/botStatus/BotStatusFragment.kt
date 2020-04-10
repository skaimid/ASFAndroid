package tech.saltyfish.asfandroid.botStatus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.FragmentBotStatusBinding
import tech.saltyfish.asfandroid.network.Bot


class BotStatusFragment : Fragment() {

    private lateinit var viewModel: BotStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.INVISIBLE
        val binding = FragmentBotStatusBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(activity).application
        val botName = BotStatusFragmentArgs.fromBundle(requireArguments()).botName


        viewModel =
            ViewModelProvider(this, BotStatusViewModelFactory(botName, application)).get(
                BotStatusViewModel::class.java
            )
        binding.botStatusViewModel = viewModel

        binding.botStatusToolBar.menu.findItem(R.id.pause_start).setOnMenuItemClickListener {

            if ((viewModel.bot.value ?: Bot()).steamID != 0L) {
                if ((viewModel.bot.value ?: Bot()).cardsFarmer.paused) {
                    viewModel.resumeBot((viewModel.bot.value ?: Bot()).botName)
                } else {
                    viewModel.pauseBot((viewModel.bot.value ?: Bot()).botName)
                }
            }
            true
        }

        binding.botStatusToolBar.menu.findItem(R.id.delete_text).setOnMenuItemClickListener {
            if ((viewModel.bot.value ?: Bot()).steamID != 0L) {
                viewModel.deleteBot((viewModel.bot.value ?: Bot()).botName)
            }
            true
        }
        binding.botStatusToolBar.menu.findItem(R.id.edit_bot_text).setOnMenuItemClickListener {
            if ((viewModel.bot.value ?: Bot()).steamID != 0L) {
                this.findNavController().navigate(
                    BotStatusFragmentDirections.actionBotStatusFragmentToBotSettingFragment(
                        (viewModel.bot.value ?: Bot()).botName
                    )
                )
            }
            true
        }

        viewModel.result.observe(viewLifecycleOwner, Observer {
            viewModel.getBotInfo((viewModel.bot.value ?: Bot()).botName)
            Toast.makeText(
                application.applicationContext,
                viewModel.result.value?.message.toString(),
                Toast.LENGTH_SHORT
            ).show()
        })



        viewModel.bot.observe(viewLifecycleOwner, Observer {

            if (viewModel.bot.value?.botConfig?.enabled == true) {
                if (viewModel.bot.value?.isConnectedAndLoggedOn == true) {
                    if (viewModel.bot.value?.cardsFarmer?.paused == true) {
                        binding.statusBotStatusText.text = getString(R.string.pause_text)
                    } else {
                        binding.statusBotStatusText.text = getString(R.string.online_text)
                    }
                } else {
                    binding.statusBotStatusText.text = getText(R.string.offline_text)
                }
            } else {
                binding.statusBotStatusText.text = getText(R.string.disable_text)
            }

            binding.botStatusTimeLeftText.text =
                when (viewModel.bot.value?.cardsFarmer?.timeRemaining) {
                    "00:00:00" -> MainActivity.context?.getText(R.string.farm_complete_text)
                        ?: "complete"
                    null -> "-"
                    else -> MainActivity.context?.getString(
                        R.string.Farming_text,
                        viewModel.bot.value!!.cardsFarmer.timeRemaining
                    )
                }
        })

        return binding.root
    }
}
