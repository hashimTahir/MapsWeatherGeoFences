package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var hFragmentMainBinding: FragmentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        hFragmentMainBinding = FragmentMainBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentMainBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}