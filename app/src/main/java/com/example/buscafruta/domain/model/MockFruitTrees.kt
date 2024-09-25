package com.example.buscafruta.domain.model

import com.google.android.gms.maps.model.LatLng

object MockFruitTrees {

    val amora = FruitTree(
        nome = "Amora",
        descricao = "Previne o envelhecimento precoce. Melhora o funcionamento do intestino. Ajuda na saciedade. Faz bem aos ossos. É benéfica para a saúde do coração. Regula a pressão arterial. Fortalece o sistema imunológico e melhora humor.",
        localizacoes = listOf(
            LatLng(-19.8751824,-43.9179415),
            LatLng(-19.89047, -43.93042),
            LatLng(-19.8896, -43.95008),
            LatLng(-19.86827, -43.96579),
            LatLng(-19.85688, -43.95022),
            LatLng(-19.85576, -43.96234),
            LatLng(-19.85553, -43.96835),
            LatLng(-19.87592, -44.03361),
            LatLng(-19.8745235,-43.9218856)

        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro")
    )

    val manga = FruitTree(
        nome = "Manga",
        descricao = "É aliada do intestino: rica em fibras, evita prisão de ventre. Reforço no sistema imunológico: vitaminas A e C aumentam a imunidade.",
        localizacoes = listOf(
            LatLng(-19.92246, -43.92487),
            LatLng(-19.92687, -43.93216),
            LatLng(-19.89524, -43.90163),
            LatLng(-19.88929, -43.93983),
            LatLng(-19.88769, -43.95422),
            LatLng(-19.86911, -43.9772),
            LatLng(-19.88674, -44.00502),
            LatLng(-19.8931, -44.03144)
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro", "Janeiro", "Fevereiro", "Março")
    )

    val pitanga = FruitTree(
        nome = "Pitanga",
        descricao = "Pitanga é uma fruta rica em vitaminas A, B e C, cálcio, fósforo, ferro e antioxidantes, com propriedades anti-inflamatórias e analgésicas.",
        localizacoes = listOf(
            LatLng(-19.87464, -43.95362),
            LatLng(-19.86446, -43.95552),
            LatLng(-19.86716, -43.9615),
            LatLng(-19.86941, -43.96966),
            LatLng(-19.86553, -43.983),
            LatLng(-19.90684, -43.90734),
            LatLng(-19.91124, -43.96647),
            LatLng(-19.879496, -43.921077),
            LatLng(-19.87464, -43.95362)
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro", "Janeiro")
    )

    val roma = FruitTree(
        nome = "Romã",
        descricao = "Os flavonoides ajudam a manter a saúde das artérias, reduzir o colesterol e prevenir ataques cardíacos, além de melhorar funções cognitivas.",
        localizacoes = listOf(
            LatLng(-19.88619, -43.91047),
            LatLng(-19.79633, -43.91563),
            LatLng(-19.93259, -43.96857),
            LatLng(-19.93744, -43.94357),
            LatLng(-19.92275, -43.99595),
            LatLng(-19.93744, -43.94357)
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro", "Dezembro")
    )

    val abacate = FruitTree(
        nome = "Abacate",
        descricao = "Além de ser uma boa fonte de energia para o organismo, o abacate contém potássio, vitaminas A e C, e equivalente de folato (ácido fólico). Com potencial antioxidante, a fruta é um bom auxiliar para a saúde de ossos e dentes e ainda atua na redução da fadiga mental.",
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
        epocaDoAno = listOf("Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto")
    )

    val jabuticaba = FruitTree(
        nome = "Jabuticaba",
        descricao = "A jabuticaba é uma fruta rica em vitamina C, quercetina e tanino, compostos com ação antioxidante, que combatem o excesso de radicais livres no organismo, ajudando a evitar rugas e flacidez. Além disso, previne o surgimento de doenças, como câncer, infarto e aterosclerose.",
        localizacoes = listOf(
            LatLng(-19.87903, -44.03621),
            LatLng(-19.91718, -43.97125),
            LatLng(-19.90553, -43.93923),
            LatLng(-19.86668, -43.95861),
            LatLng(-19.87648, -44.03402),
            LatLng(-19.79634, -43.91648)
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro")
    )

    val allFruitTrees = listOf(amora, manga, pitanga, roma, abacate, jabuticaba)
}
