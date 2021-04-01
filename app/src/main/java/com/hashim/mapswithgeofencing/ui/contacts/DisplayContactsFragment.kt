/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hashim.mapswithgeofencing.databinding.FragmentDisplayContactsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DisplayContactsFragment : Fragment() {
    lateinit var hFragmentDisplayContactsBinding: FragmentDisplayContactsBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        hFragmentDisplayContactsBinding = FragmentDisplayContactsBinding.inflate(
                inflater,
                container,
                false
        )
        return hFragmentDisplayContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}