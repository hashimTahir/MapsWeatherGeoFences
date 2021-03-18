package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    lateinit var hFragmentMainBinding: FragmentMainBinding


    private val hMapCallBack = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }


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
        hInitMap()


    }

    private fun hInitMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.hGoogleMap) as SupportMapFragment?
        mapFragment?.getMapAsync(hMapCallBack)
    }
}