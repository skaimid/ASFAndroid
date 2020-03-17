package tech.saltyfish.asfandroid.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import tech.saltyfish.asfandroid.R
import tech.saltyfish.asfandroid.databinding.FragmentOverviewBinding


class OverviewFragment : Fragment() {

    private lateinit var viewModel: OverviewViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOverviewBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(OverviewViewModel::class.java)
        binding.overviewViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        return binding.root
    }


}