package tech.saltyfish.asfandroid.botStatus

import android.os.Bundle
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import tech.saltyfish.asfandroid.databinding.FragmentBotStatusBinding


class BotStatusFragment : Fragment() {

    private lateinit var viewModel: BotStatusViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBotStatusBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        val application = requireNotNull(activity).application
        val botName = BotStatusFragmentArgs.fromBundle(requireArguments()).botName

        binding.botStatusViewModel =
            ViewModelProvider(this, BotStatusViewModelFactory(botName, application)).get(
                BotStatusViewModel::class.java
            )


        return binding.root
    }


}
