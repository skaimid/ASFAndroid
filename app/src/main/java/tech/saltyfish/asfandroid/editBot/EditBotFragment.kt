package tech.saltyfish.asfandroid.editBot

import android.os.Bundle
import android.util.Log
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
import tech.saltyfish.asfandroid.databinding.FragmentEditBotBinding
import tech.saltyfish.asfandroid.listToCsv

class EditBotFragment : Fragment() {
    private lateinit var viewModel: EditBotViewModel
    private lateinit var binding: FragmentEditBotBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE

        binding = FragmentEditBotBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        val application = requireNotNull(activity).application
        val botName = EditBotFragmentArgs.fromBundle(requireArguments()).botName

        viewModel = ViewModelProvider(
            this,
            EditBotViewModelFactory(botName, application)
        ).get(
            EditBotViewModel::class.java
        )


        binding.editBotHeader.text = botName
        Log.d("bot Name", botName)

        viewModel.bot.observe(viewLifecycleOwner, Observer {
            binding.steamUserNameEditText.setText(it.nickname)// steam log in
            binding.steamPasswordEditText.setText(it.botConfig.steamPassword)
            binding.gamesPlayedWhileIdleEditText.setText(listToCsv(it.botConfig.gamesPlayedWhileIdle))
            binding.customGamePlayedWhileFarmingEditText.setText(it.botConfig.customGamePlayedWhileFarming)
            binding.customGamePlayedWhileIdleEditText.setText(it.botConfig.customGamePlayedWhileIdle)
            binding.hoursUntilCardDropsEditText.setText(it.botConfig.hoursUntilCardDrops.toString())
//            Log.d("sss",it.botConfig.hoursUntilCardDrops.toString())
        })

        binding.submitConfigButton.setOnClickListener {
            val sb = StringBuilder("{\"botConfig\":{")

            if (binding.steamUserNameEditText.text.isNotEmpty())
                sb.append("\"SteamLogin\":\"${binding.steamUserNameEditText.text}\",")

            if (binding.steamPasswordEditText.text.isNotEmpty())
                sb.append("\"SteamPassword\":\"${binding.steamPasswordEditText.text}\",")

            if (binding.gamesPlayedWhileIdleEditText.text.isNotEmpty())
                sb.append("\"GamesPlayedWhileIdle\":[${binding.customGamePlayedWhileIdleEditText.text}],")

            if (binding.customGamePlayedWhileFarmingEditText.text.isNotEmpty())
                sb.append("\"CustomGamePlayedWhileFarming\":\"${binding.customGamePlayedWhileFarmingEditText.text}\",")

            if (binding.customGamePlayedWhileIdleEditText.text.isNotEmpty())
                sb.append("\"CustomGamePlayedWhileIdle\":\"${binding.customGamePlayedWhileIdleEditText.text}\",")

            if (binding.hoursUntilCardDropsEditText.text.isNotEmpty())
                sb.append("\"HoursUntilCardDrops\":${binding.hoursUntilCardDropsEditText.text},")


            viewModel.changeBorConfig(
                botName,
                if (sb[sb.lastIndex] == '{')
                    sb.append("}}").toString()
                else
                    sb.deleteCharAt(sb.lastIndex).append("}}").toString()
//                viewModel.bot.value?.botConfig?:BotConfig()
            )
        }

        viewModel.bot.observe(viewLifecycleOwner, Observer {
            viewModel.changeLoadStatus()
        })

        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                if (it.success) {
                    Toast.makeText(
                        context,
                        getText(R.string.update_success_text),
                        Toast.LENGTH_SHORT
                    ).show()

                    this.findNavController().navigate(
                        R.id.overviewFragment
                    )
                }
                viewModel.setRSNull()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            if (viewModel.loading.value == true) {
                binding.editBotProgressBar.visibility = View.VISIBLE
                binding.submitConfigButton.isEnabled = false
            }

            if (viewModel.loading.value == false) {
                binding.editBotProgressBar.visibility = View.GONE
                binding.submitConfigButton.isEnabled = true
            }
        })


        return binding.root
    }
}