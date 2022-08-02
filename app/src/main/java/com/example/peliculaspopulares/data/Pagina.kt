package com.example.peliculaspopulares.data

import com.google.gson.annotations.SerializedName

data class Pagina(

    val page: String,
    val results : List<Peliculas>,

    )
