package com.example.frutawatch.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frutawatch.R
import com.example.frutawatch.model.MockFruitTrees
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import android.view.View
import android.widget.ImageButton

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val buttonBack: ImageButton = findViewById(R.id.button_back)
        buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        for (fruitTree in MockFruitTrees.allFruitTrees) {
            for (location in fruitTree.localizacoes) {
                val markerIcon = BitmapFactory.decodeResource(resources, fruitTree.icone)
                val resizedIcon = Bitmap.createScaledBitmap(markerIcon, 25, 25, false) // Redimensionar para 25x25 pixels

                map.addMarker(MarkerOptions()
                    .position(location)
                    .title(fruitTree.nome)
                    .snippet(fruitTree.descricao)
                    .icon(BitmapDescriptorFactory.fromBitmap(resizedIcon)) // Usar ícone redimensionado
                )
            }
        }

        val initialLocation = MockFruitTrees.allFruitTrees.first().localizacoes.first()
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 10f))
    }
}
