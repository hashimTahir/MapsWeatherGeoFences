/*
 * Copyright (c) 2021/  4/ 1.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hashim.mapswithgeofencing.databinding.FragmentAllContactsBinding

class AllContactsFragment : Fragment() {

    lateinit var hAllContactsBinding: FragmentAllContactsBinding
    val hContactsSharedViewModel: ContactsSharedViewModel by activityViewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        hAllContactsBinding = FragmentAllContactsBinding.inflate(
                inflater,
                container,
                false
        )

        return hAllContactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}