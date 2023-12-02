package com.mateusz.jasiak.activetimespendingsystem.ui.fragments.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mateusz.jasiak.activetimespendingsystem.R
import com.mateusz.jasiak.activetimespendingsystem.common.BaseFragment
import com.mateusz.jasiak.activetimespendingsystem.databinding.FragmentMapBinding

class MapFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapBinding
    override val viewModel: MapViewModel by lazy {
        viewModelOf(
            MapViewModel::class
        )
    }

    private lateinit var googleMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_map,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        this.googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}