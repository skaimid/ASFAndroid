package tech.saltyfish.asfandroid.botStatus

import android.content.Intent
import android.net.Uri
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
        // hide nav bar
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

        // click to go to browser to view the game detail
        // not capable with steam game client, it do recognise the url, but have trouble view it.
        binding.listFarmGame.adapter = FarmGameAdapter(FarmGameAdapter.OnClickListener {
            val url = "https://store.steampowered.com/app/${it.appID}"
            val webpage: Uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webpage)

            startActivity(intent)

        })

        // pause and start the bot
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

        // delete bot
        binding.botStatusToolBar.menu.findItem(R.id.delete_text).setOnMenuItemClickListener {

            viewModel.deleteBot((viewModel.bot.value ?: Bot()).botName)
            viewModel.changeOperator("delete")
            true
        }

        // edit bot
        binding.botStatusToolBar.menu.findItem(R.id.edit_bot_text).setOnMenuItemClickListener {

            this.findNavController().navigate(
                BotStatusFragmentDirections.actionBotStatusFragmentToEditBotFargment(botName)
            )

            true
        }


        // if some operation was done, then have some response to it.
        // e.g. make toasts
        viewModel.result.observe(viewLifecycleOwner, Observer {
            viewModel.getBotInfo((viewModel.bot.value ?: Bot()).botName)
            Toast.makeText(
                application.applicationContext,
                viewModel.result.value?.message.toString(),
                Toast.LENGTH_SHORT
            ).show()

            // if operation is delete, then navigate to override fragment
            if (viewModel.operator.value == "delete") {
                this.findNavController().navigate(
                    R.id.overviewFragment
                )
            }
        })


        /**
         * show the bot status with dynamic change
         * using data binding with object Bot is not a good option
         *
         * if some change happened with vm.bot
         * the data like @{vm.bot} may change but @{vm.bot.name} will not change
         */
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
                    "00:00:00" -> getText(R.string.farm_complete_text)

                    null -> "-"
                    else -> getString(
                        R.string.Farming_text,
                        viewModel.bot.value!!.cardsFarmer.timeRemaining
                    )
                }

            (binding.listFarmGame.adapter as FarmGameAdapter).submitList(
                (it.cardsFarmer.gamesToFarm)
            )

            viewModel.changeLoadStatus()

        })

        /**
         * change load status
         * depend on vm.loading
         *
         * view model is sth with status, but not view
         */
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (viewModel.loading.value == true) {
                binding.progressBar.visibility = View.VISIBLE
            }

            if (viewModel.loading.value == false) {
                binding.progressBar.visibility = View.GONE
                if (viewModel.operator.value == "delete" && viewModel.result.value?.success == true) {
                    this.findNavController().navigate(
                        R.id.overviewFragment
                    )
                }
            }
        })

        return binding.root
    }
}
