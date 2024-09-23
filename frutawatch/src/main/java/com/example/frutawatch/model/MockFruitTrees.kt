package com.example.frutawatch.model

import com.example.frutawatch.R
import com.google.android.gms.maps.model.LatLng

object MockFruitTrees {

    val amora = FruitTree(
        nome = "Amora",
        descricao = "Previne o envelhecimento precoce, melhora a circulação sanguínea, e é rica em antioxidantes.",
        localizacoes = listOf(
            LatLng(-19.89047, -43.93042),
            LatLng(-19.8896, -43.95008),
            LatLng(-19.8881, -43.9315),
            LatLng(-19.8751824, -43.9179415)
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro"),
        icone = R.drawable.ic_amora
    )

    val manga = FruitTree(
        nome = "Manga",
        descricao = "É aliada do intestino, rica em vitaminas A e C, e possui propriedades antioxidantes.",
        localizacoes = listOf(
            LatLng(-19.92246, -43.92487),
            LatLng(-19.9273, -43.9231),
            LatLng(-19.9257, -43.9220)
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro", "Janeiro", "Fevereiro", "Março"),
        icone = R.drawable.ic_manga
    )

    val pitanga = FruitTree(
        nome = "Pitanga",
        descricao = "Possui propriedades anti-inflamatórias e é rica em vitaminas.",
        localizacoes = listOf(
            LatLng(-19.89042, -43.93992),
            LatLng(-19.8912, -43.9405),
            LatLng(-19.8890, -43.9420)
        ),
        epocaDoAno = listOf("Janeiro", "Fevereiro", "Março"),
        icone = R.drawable.ic_pitanga
    )

    val romã = FruitTree(
        nome = "Romã",
        descricao = "Rica em antioxidantes e anti-inflamatórios, ajuda a melhorar a saúde do coração.",
        localizacoes = listOf(
            LatLng(-19.8895, -43.9401),
            LatLng(-19.8898, -43.9410),
            LatLng(-19.8900, -43.9425)
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro"),
        icone = R.drawable.ic_roma
    )

    val abacate = FruitTree(
        nome = "Abacate",
        descricao = "Além de ser uma boa fonte de energia, é rico em potássio e vitaminas.",
        localizacoes = listOf(
            LatLng(-19.86915, -43.94995),
            LatLng(-19.95467, -43.90835),
            LatLng(-19.96008, -43.99449),
            LatLng(-19.92367, -43.95647),
            LatLng(-19.92343, -43.94183),
            LatLng(-19.9213, -43.93327),
            LatLng(-19.94742, -43.92809),
            LatLng(-19.84638, -43.93171)
        ),
        epocaDoAno = listOf("Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto"),
        icone = R.drawable.ic_abacate
    )

    val jabuticaba = FruitTree(
        nome = "Jabuticaba",
        descricao = "Rica em vitamina C e antioxidantes, ajuda a prevenir doenças.",
        localizacoes = listOf(
            LatLng(-19.87903, -44.03621),
            LatLng(-19.91718, -43.97125),
            LatLng(-19.90553, -43.93923),
            LatLng(-19.86668, -43.95861),
            LatLng(-19.87648, -44.03402),
            LatLng(-19.79634, -43.91648)
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro"),
        icone = R.drawable.ic_jabuticaba
    )

    val allFruitTrees = listOf(amora, manga, pitanga, romã, abacate, jabuticaba)
}


data class FruitTree(
    val nome: String,
    val descricao: String,
    val localizacoes: List<LatLng>,
    val epocaDoAno: List<String>,
    val icone: Int
)

