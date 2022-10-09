package com.mazurenka.vivaldi.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mazurenka.vivaldi.R
import com.mazurenka.vivaldi.databinding.FragmentPlayerBinding
import com.mazurenka.vivaldi.presentation.viewmodel.PlayerViewModel
import com.mazurenka.vivaldi.utils.makeFitSystemUI
import com.mazurenka.vivaldi.utils.makeFullScreen
import org.koin.androidx.viewmodel.ext.android.viewModel


class PlayerFragment : BaseFragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getAudioList(requireContext())
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
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "PLAYER_APP"
    }
}