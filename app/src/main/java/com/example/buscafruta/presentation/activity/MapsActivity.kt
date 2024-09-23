package com.example.buscafruta.presentation.activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.BuscaFruta.R
import com.example.BuscaFruta.databinding.ActivityMapsBinding
import com.example.buscafruta.domain.model.FruitTree
import com.example.buscafruta.presentation.viewmodel.FruitTreeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val viewModel: FruitTreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkLocationPermission()) requestLocationPermission()
        if (!checkNotificationPermission()) requestNotificationPermission()

        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        observeViewModel()
    }

    private fun observeViewModel() {
        val filterFruit = intent.getStringExtra("filterFruit") ?: "all"

        CoroutineScope(Dispatchers.Main).launch {
            viewModel.getFilteredFruitTrees(filterFruit).collect { fruitTrees ->
                displayMarkers(fruitTrees)
            }
        }
    }

    private fun displayMarkers(fruitTrees: List<FruitTree>) {
        mMap.clear()
        val boundsBuilder = LatLngBounds.Builder()

        fruitTrees.forEach { fruitTree ->
            fruitTree.localizacoes.forEach { location ->
                val icon = getFruitIconResource(fruitTree.nome)
                val resizedBitmap = Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(resources, icon), 100, 100, false
                )

                mMap.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(fruitTree.nome)
                        .icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap))
                )
                boundsBuilder.include(location)
            }
        }

        val bounds = boundsBuilder.build()
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
    }

    private fun checkLocationPermission(): Boolean =
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }

    private fun checkNotificationPermission(): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        else true

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                REQUEST_NOTIFICATION_PERMISSION_CODE
            )
        }
    }

    private fun getFruitIconResource(fruitName: String): Int = when (fruitName) {
        "Amora" -> R.drawable.ic_amora
        "Manga" -> R.drawable.ic_manga
        "Pitanga" -> R.drawable.ic_pitanga
        "Romã" -> R.drawable.ic_roma
        "Abacate" -> R.drawable.ic_abacate
        "Jabuticaba" -> R.drawable.ic_jabuticaba
        else -> R.drawable.capa
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION_CODE = 1002
        private const val REQUEST_NOTIFICATION_PERMISSION_CODE = 1001
    }
}
