package com.mazurenka.vivaldi.presentation.fragment

import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.exoplayer2.ExoPlayer
import com.mazurenka.vivaldi.R
import com.mazurenka.vivaldi.data.model.AudioModel
import com.mazurenka.vivaldi.databinding.FragmentPlayerBinding
import com.mazurenka.vivaldi.presentation.viewmodel.PlayerViewModel
import com.mazurenka.vivaldi.service.PlayerService
import com.mazurenka.vivaldi.utils.makeFitSystemUI
import com.mazurenka.vivaldi.utils.makeFullScreen
import com.mazurenka.vivaldi.utils.startForeground
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : BaseFragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerViewModel by viewModel()

    private var player: ExoPlayer? = null

    private val serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.i(TAG, "${this::class.java} serviceConnection")

            if (service is PlayerService.PlayerServiceBinder) {
                player = service.getExoPlayerInstance()
            }
        }

        override fun onServiceDisconnected(className: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAudioList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i(TAG, "${this::class.java} onCreateView")
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
        val view = binding.root

        makeFullScreen(requireActivity())
        makeFitSystemUI(binding.appbarPlayer.toolbar)

        val statusBarColor: Int = R.color.color_statusbar_primary
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), statusBarColor)

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            binding.progressPlayer.isIndeterminate = state.isLoading

            if (state.audioList.isNotEmpty() && !isServiceRunning()) {
                startService(state.audioList as ArrayList<AudioModel>)
            }
        }

        return view
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "${this::class.java} onStop")
        requireActivity().unbindService(serviceConnection)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun startService(audioList: ArrayList<AudioModel>) {
        val serviceIntent = Intent(requireActivity(), PlayerService::class.java)
        serviceIntent.putExtra(AUDIO_LIST, audioList)

        requireActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE)
        startForeground(requireActivity(), serviceIntent)
    }

    private fun isServiceRunning(): Boolean {
        val activityManager = requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        for (item in activityManager.getRunningServices(Int.MAX_VALUE)) {
            if (PlayerService::class.java.name.equals(item.service.className)) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TAG = "PLAYER_APP"
        private const val AUDIO_LIST = "AUDIO_LIST"
    }
}