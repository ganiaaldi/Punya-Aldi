package com.aldi.punyaaldi.menu


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.aldi.punyaaldi.R
import com.aldi.punyaaldi.`interface`.ToolbarTitleListener
import com.aldi.punyaaldi.slidepage.PrefManager
import com.aldi.punyaaldi.slidepage.SlideScreen
import kotlinx.android.synthetic.main.fragment_first_menu.*


class FirstMenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as ToolbarTitleListener).showToolbar(true)
        (activity as ToolbarTitleListener).updateTitle("Fragment Menu 1")
        showSlider()
    }

    private fun showSlider() {
        btn_ShowSlider.setOnClickListener {
            findNavController().navigate(R.id.slideScreen)
        }
    }

}
