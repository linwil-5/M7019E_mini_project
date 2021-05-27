package com.example.test

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.FragmentAboutBinding
import com.example.test.utils.Constants.ID_RICK_VIDEO_URL
import com.example.test.viewmodel.AboutViewModel
import com.example.test.viewmodel.AboutViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


/**
 * A simple [Fragment] subclass.
 * Use the [AboutFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AboutFragment : Fragment() {


    private lateinit var viewModelAbout: AboutViewModel
    private lateinit var viewModelAboutFactory: AboutViewModelFactory

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentAboutBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        viewModelAboutFactory = AboutViewModelFactory(application)
        viewModelAbout =
            ViewModelProvider(this, viewModelAboutFactory).get(AboutViewModel::class.java)


        binding.aboutVideoYoutubePlayerView.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    val videoId = ID_RICK_VIDEO_URL
                    youTubePlayer.loadVideo(videoId, 0f)
                    youTubePlayer.pause()
                }
            })


        return binding.root
    }

    private fun getShareIntent(location: String): Intent {
        val gmmIntentUri = Uri.parse(location)
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        return mapIntent
    }

    private fun shareSuccess(location: String) {
        startActivity(getShareIntent(location))
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModelAbout.detailLocation.observe(viewLifecycleOwner, {
            shareSuccess(it)
        })

        binding.aboutLocationB.setOnClickListener {
            viewModelAbout.getLocation("") // Future specific coordinate here
        }

    }
}