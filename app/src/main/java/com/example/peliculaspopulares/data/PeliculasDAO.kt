package com.example.peliculaspopulares.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "pelicula_table")
data class PeliculasDAO(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "ID")
    val id : String,

    @ColumnInfo(name = "original_title")
    val titulo : String,

    @ColumnInfo(name = "poster_path")
    val posterpath : String,

    @ColumnInfo(name = "backdrop_path")
    val backdrop : String,

    )

