package com.example.frutawatch.model

import com.example.frutawatch.R
import com.google.android.gms.maps.model.LatLng

object MockFruitTrees {

    val amora = FruitTree(
        nome = "Amora",
        descricao = "Com sua cor vibrante, a amora é uma verdadeira fonte de juventude! Rica em antioxidantes, essa pequena fruta ajuda a combater o envelhecimento precoce e melhora a circulação sanguínea. Além disso, fortalece o sistema imunológico e traz uma explosão de sabor a cada mordida!",
        localizacoes = listOf(
            LatLng(-19.8751824, -43.9179415),
            LatLng(-19.89047, -43.93042),
            LatLng(-19.8896, -43.95008),
            LatLng(-19.86827, -43.96579),
            LatLng(-19.85688, -43.95022),
            LatLng(-19.85576, -43.96234),
            LatLng(-19.85553, -43.96835),
            LatLng(-19.87592, -44.03361),
            LatLng(-19.8745235, -43.9218856),
            LatLng(-19.874825, -43.918613), // Nova localização
            LatLng(-19.878435, -43.920474)  // Nova localização
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro"),
        icone = R.drawable.ic_amora
    )

    val manga = FruitTree(
        nome = "Manga",
        descricao = "Sabor tropical e benefícios imbatíveis! A manga é rica em fibras que ajudam na digestão, além de ser uma excelente fonte de vitaminas A e C, fortalecendo o sistema imunológico e a saúde dos olhos. Uma fruta que é pura energia e refrescância em dias quentes!",
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
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro", "Janeiro", "Fevereiro", "Março"),
        icone = R.drawable.ic_manga
    )

    val pitanga = FruitTree(
        nome = "Pitanga",
        descricao = "Pequena no tamanho, gigante nos benefícios! A pitanga é um tesouro nutricional, rica em vitaminas A, B e C, com poderosas propriedades anti-inflamatórias e antioxidantes. Seu sabor exótico e levemente azedo é irresistível!",
        localizacoes = listOf(
            LatLng(-19.87464, -43.95362),
            LatLng(-19.86446, -43.95552),
            LatLng(-19.86716, -43.9615),
            LatLng(-19.86941, -43.96966),
            LatLng(-19.86553, -43.983),
            LatLng(-19.90684, -43.90734),
            LatLng(-19.91124, -43.96647),
            LatLng(-19.879496, -43.921077),
            LatLng(-19.874959, -43.917505) // Nova localização
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro", "Janeiro"),
        icone = R.drawable.ic_pitanga
    )

    val romã = FruitTree(
        nome = "Romã",
        descricao = "Fruta mística e poderosa! A romã é conhecida por suas propriedades medicinais há milênios, sendo uma aliada do coração por reduzir inflamações e melhorar a circulação. Repleta de antioxidantes, é uma joia da natureza para a saúde cardiovascular!",
        localizacoes = listOf(
            LatLng(-19.88619, -43.91047),
            LatLng(-19.79633, -43.91563),
            LatLng(-19.93259, -43.96857),
            LatLng(-19.93744, -43.94357),
            LatLng(-19.92275, -43.99595)
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro", "Dezembro"),
        icone = R.drawable.ic_roma
    )

    val abacate = FruitTree(
        nome = "Abacate",
        descricao = "Além de ser uma excelente fonte de energia, o abacate é rico em potássio, vitaminas A e C. Seus antioxidantes combatem o envelhecimento e o estresse, ajudando a manter a mente e o corpo em harmonia.",
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
        descricao = "A jabuticaba é uma fruta rica em vitamina C e antioxidantes, que combatem o envelhecimento e ajudam a prevenir doenças graves como câncer e doenças cardíacas. Além de deliciosa, é um verdadeiro escudo protetor para a sua saúde.",
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

    val goiaba = FruitTree( // Nova árvore adicionada
        nome = "Goiaba",
        descricao = "A goiaba é um superalimento tropical! Rica em fibras e vitamina C, é excelente para a digestão e fortalece o sistema imunológico. Com seu sabor doce e aroma único, a goiaba é uma explosão de saúde em cada mordida.",
        localizacoes = listOf(
            LatLng(-19.874821, -43.917911) // Nova localização para goiaba
        ),
        epocaDoAno = listOf("Outubro", "Novembro", "Dezembro"),
        icone = R.drawable.ic_frutas
    )

    val calabura = FruitTree( // Atualizada localização para calabura
        nome = "Calabura",
        descricao = "Conhecida por suas pequenas frutas vermelhas, a calabura é uma verdadeira potência nutricional. Rica em vitamina C e antioxidantes, ela ajuda a combater o estresse oxidativo e fortalece a imunidade. Uma fruta rara e cheia de benefícios!",
        localizacoes = listOf(
            LatLng(-19.877363, -43.920760) // Nova localização substituída
        ),
        epocaDoAno = listOf("Setembro", "Outubro", "Novembro", "Dezembro"),
        icone = R.drawable.ic_frutas
    )

    val allFruitTrees = listOf(amora, manga, pitanga, romã, abacate, jabuticaba, goiaba, calabura)
}

data class FruitTree(
    val nome: String,
    val descricao: String,
    val localizacoes: List<LatLng>,
    val epocaDoAno: List<String>,
    val icone: Int
)
