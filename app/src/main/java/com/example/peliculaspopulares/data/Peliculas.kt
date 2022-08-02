package com.example.peliculaspopulares.data

import com.google.gson.annotations.SerializedName

data class Peliculas(

    val id : String,
    @SerializedName("original_title")
    val titulo : String,
    @SerializedName("poster_path")
    val posterpath : String,
    @SerializedName("backdrop_path")
    val backdrop : String,


    )
