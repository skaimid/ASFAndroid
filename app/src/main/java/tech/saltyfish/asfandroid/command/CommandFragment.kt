package tech.saltyfish.asfandroid.command

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.FragmentCommandBinding
import tech.saltyfish.asfandroid.overview.OverviewViewModel

class CommandFragment : Fragment() {
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

        binding.exeButton.setOnClickListener {
            viewModel.updateCommandLine(binding.commandInput.text.toString())
        }

        return binding.root
    }


}
