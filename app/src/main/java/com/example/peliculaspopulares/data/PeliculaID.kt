package com.example.peliculaspopulares.data

import com.google.gson.annotations.SerializedName

class PeliculaID(

    

    val id : String,
    @SerializedName("original_title")
    val titulo : String,
    @SerializedName("overview")
    val descipcion : String,
    @SerializedName("vote_average")
    val porcenjatevotos : Float,
    @SerializedName("original_language")
    val lenguaje : String,
    @SerializedName("genres")
    val genero : List<Generodetalles>,
    @SerializedName("release_date")
    val fechalanzamiento : String,
    @SerializedName("backdrop_path")
    val poster : String




    )
