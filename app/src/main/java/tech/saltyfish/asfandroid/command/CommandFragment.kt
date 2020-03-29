package tech.saltyfish.asfandroid.command

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tech.saltyfish.asfandroid.databinding.FragmentCommandBinding

class CommandFragment : Fragment() {
    private var COMMAND = arrayOf(
        "2fa",
        "2fano",
        "2faok",
        "addlicense",
        "balance",
        "bgr",
        "bl",
        "bladd",
        "blrm",
        "exit",
        "farm",
        "input",
        "ib",
        "ibadd",
        "ibrm",
        "iq",
        "iqadd",
        "iqrm",
        "level",
        "loot",
        "loot@",
        "loot%",
        "loot^",
        "nickname",
        "owns",
        "password",
        "pause",
        "pause~",
        "pause&",
        "play",
        "privacy",
        "redeem",
        "redeem^",
        "reset",
        "restart",
        "resume",
        "start",
        "stats",
        "status",
        "stop",
        "transfer",
        "transfer@",
        "transfer%",
        "transfer^",
        "unpack",
        "update",
        "version",
        "commands",
        "help"
    )

    private lateinit var viewModel: CommandViewModel
    private lateinit var binding: FragmentCommandBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommandBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(CommandViewModel::class.java)
        binding.commandViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        if (container != null) {
            binding.commandInput.setAdapter(
                ArrayAdapter(
                    container.context,
                    android.R.layout.simple_dropdown_item_1line,
                    COMMAND
                )
            )
        }

        binding.exeButton.setOnClickListener {
            viewModel.updateCommandLine(binding.commandInput.text.toString())
        }

        return binding.root
    }


}
