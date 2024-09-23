package com.example.buscafruta.domain.model

import com.google.android.gms.maps.model.LatLng

data class FruitTree(
    val nome: String,
    val descricao: String,
    val localizacoes: List<LatLng>,
    val epocaDoAno: List<String>
)
