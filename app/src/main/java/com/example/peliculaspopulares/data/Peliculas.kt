package com.example.peliculaspopulares.data


import com.squareup.moshi.Json

data class Peliculas(

    val id : String,
    //@SerializedName("original_title")
    @Json(name = "original_title")
    val titulo : String,
    //@SerializedName("poster_path")
    @Json(name = "poster_path")
    val posterpath : String,
    //@SerializedName("backdrop_path")
    @Json(name = "backdrop_path")
    val backdrop : String,


    )
