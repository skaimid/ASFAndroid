package tech.saltyfish.asfandroid.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.FragmentOverviewBinding
import tech.saltyfish.asfandroid.network.Bot


class OverviewFragment : Fragment() {

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // This Fragment has a Bottom Bar
        (requireActivity() as MainActivity).bottom_nav.visibility = View.VISIBLE

        val binding = FragmentOverviewBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        binding.overviewViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.botRecyclerView.adapter = BotGridAdapter(BotGridAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(
                    OverviewFragmentDirections.actionOverviewFragmentToBotStatusFragment(it.botName)
                )
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })


        viewModel.bots.observe(viewLifecycleOwner, Observer {
            binding.overviewGameRemaining.text =
                getString(R.string.remaining_game_text, viewModel.getGameLeft())
            binding.overviewTimeRemaining.text =
                getString(R.string.remaining_text_words, viewModel.getTimeLeft())
            binding.overviewCardRemaining.text =
                getString(R.string.card_left_text, viewModel.getCardLeft())

            var cOnline = 0
            var cPaused = 0
            var cOffline = 0
            var cDisabled = 0;

            viewModel.bots.value?.forEach { bot: Bot ->
                if (bot.botConfig.enabled) {
                    if (bot.isConnectedAndLoggedOn) {
                        if (bot.cardsFarmer.paused) {
                            cPaused++
                        } else {
                            cOnline++
                        }
                    } else {
                        cOffline++
                    }
                } else {
                    cDisabled++
                }
            }

            binding.overviewBotStatus.text =
                getString(
                    R.string.overview_bot_status_text,
                    cOnline,
                    cPaused,
                    cOffline,
                    cDisabled
                )
        })


        return binding.root
    }


}
