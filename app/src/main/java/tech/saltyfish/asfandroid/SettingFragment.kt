package tech.saltyfish.asfandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*
import tech.saltyfish.asfandroid.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as MainActivity).bottom_nav.visibility = View.VISIBLE
        val binding = FragmentSettingBinding.inflate(inflater)
        binding.asfConfCardView.setOnClickListener {view: View ->
            (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE
            view.findNavController()
                .navigate(SettingFragmentDirections.actionSettingFragmentToASFSettingFragment())
        }

        binding.appConfCardView.setOnClickListener { view:View ->
            (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE
            view.findNavController()
                .navigate(SettingFragmentDirections.actionSettingFragmentToAppSettingFragment())
        }

        binding.aboutCardview.setOnClickListener { view:View->
            (requireActivity() as MainActivity).bottom_nav.visibility = View.GONE
            view.findNavController()
                .navigate(SettingFragmentDirections.actionSettingFragmentToAboutkFragment())
        }


        return binding.root
    }


}
