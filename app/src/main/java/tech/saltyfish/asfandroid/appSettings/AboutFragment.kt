package tech.saltyfish.asfandroid.appSettings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import tech.saltyfish.asfandroid.databinding.FragmentAboutBinding
import tech.saltyfish.asfandroid.toSpanned


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)

        // provide info about app
        val about = "<h1>ASF Android</h1>\n" +
                "<h2>什么是 ASF Android </h2>\n" +
                "<p>ASF Android 是为ASF 设计的一个Android 前端，可以轻松操作 ASF。为了正确使用本软件，你可能需要自行架设并配置一个ASF后端——包括但不限于将 ASF IPC 部署在一个反向代理背后、设置 <code>IPCPassword</code>。</p>\n" +
                "<h2>ASF是什么</h2>\n" +
                "<p>ASF 是一个帮助您轻松获得Steam卡牌掉落的程序，它还提供了一些其他功能。</p>\n" +
                "<p>详见<a href='https://github.com/JustArchiNET/ArchiSteamFarm/wiki'>ASF wiki</a></p>"


        /**
         * @see toSpanned()
         *
         * using String.toSpanned to make text with style
         */
        binding.aboutTextView.text = about.toSpanned()

        return binding.root
    }
}
