package tech.saltyfish.asfandroid.appSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.saltyfish.asfandroid.MainActivity
import tech.saltyfish.asfandroid.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.VISIBLE
        val binding = FragmentSettingBinding.inflate(inflater)

        // navigate to AppSettingFragment
        binding.appConfCardView.setOnClickListener { view: View ->
            (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE
            view.findNavController()
                .navigate(SettingFragmentDirections.actionSettingFragmentToAppSettingFragment())
        }

        // navigate to AboutFragment
        binding.aboutCardview.setOnClickListener { view: View ->
            (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE
            view.findNavController()
                .navigate(SettingFragmentDirections.actionSettingFragmentToAboutkFragment())// aboytk is a typo!!
        }


        return binding.root
    }


}

