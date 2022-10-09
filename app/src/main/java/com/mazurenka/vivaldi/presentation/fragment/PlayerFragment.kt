package com.mazurenka.vivaldi.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.mazurenka.vivaldi.R
import com.mazurenka.vivaldi.databinding.FragmentPlayerBinding
import com.mazurenka.vivaldi.utils.makeFitSystemUI
import com.mazurenka.vivaldi.utils.makeFullScreen

class PlayerFragment : BaseFragment() {

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

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