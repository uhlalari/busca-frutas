package com.example.buscafruta.presentation.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.BuscaFruta.R
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.presentation.geofencing.GeofenceHelper
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val viewModel: FruitTreeViewModel by viewModels()
    private lateinit var geofenceHelper: GeofenceHelper
    private val LOCATION_PERMISSION_REQUEST_CODE = 1001

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        geofenceHelper = GeofenceHelper(requireContext())

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (checkLocationPermissions()) {
            simulateMockLocation()
            observeFruitTrees()
        } else {
            requestLocationPermissions()
        }
    }

    private fun observeFruitTrees() {
        MainScope().launch {
            viewModel.fruitTrees.onEach { trees ->
                map.clear()
                if (trees.isNotEmpty()) {
                    trees.forEach { fruitTree ->
                        fruitTree.localizacoes.forEach { location ->
                            map.addMarker(MarkerOptions().position(location).title(fruitTree.nome))
                        }

                        addGeofencesForFruitTree(fruitTree)
                    }
                }
            }.launchIn(this)
        }
    }

    private fun addGeofencesForFruitTree(fruitTree: FruitTree) {
        val fruitData = mapOf(fruitTree.nome to fruitTree.localizacoes.map { Pair(it.latitude, it.longitude) })
        geofenceHelper.addGeofences(fruitData, 3000f,
            onSuccess = {
            },
            onFailure = { errorMessage ->
            }
        )
    }

    private fun simulateMockLocation() {
        val mockLocation = Location("mockprovider").apply {
            latitude = -19.91718
            longitude = -43.97125
            accuracy = 10f
        }

        val mockLatLng = LatLng(mockLocation.latitude, mockLocation.longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(mockLatLng, 15f))
    }

    private fun checkLocationPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(map)
            }
        }
    }
}
