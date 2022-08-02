package com.example.peliculaspopulares.repositorio

import com.example.peliculaspopulares.data.Pagina
import com.example.peliculaspopulares.data.PeliculaID
import com.example.peliculaspopulares.service.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PeliculasDataSource {

    fun getPagina(listener  : ResponseListener<Pagina>){

        val service = RetrofitService.instance.create(PeliculaInterface::class.java).getPeliculas()

        service.enqueue(object : Callback<Pagina> {
            override fun onResponse(call: Call<Pagina>, response: Response<Pagina>) {
                val activity: Pagina? = response.body()
                if (response.isSuccessful && activity != null){
                    listener.onResponse(
                        RepositoryResponse(
                            data = activity,
                            source = Source.REMOTE
                        )
                    )
            }else {
                    listener.onError(
                            RepositoryError(
                                message = "Clave de API no válida: se le debe otorgar una clave válida",
                                code = response.code(),
                                source = Source.REMOTE
                            )
                        )


            }}

            override fun onFailure(call: Call<Pagina>, t: Throwable) {
                listener.onError(
                    RepositoryError(
                        message = t.message ?: "Error inesperado",
                        code = -1 ,
                        source = Source.REMOTE
                    )
                )
            }
        })
    }





    fun getPeliculaid(listener  : ResponseListener<PeliculaID>, idpelicula : String ){

        val service = RetrofitService.instance.create(PeliculaInterface::class.java).getPeliculasID(idpelicula)

        service.enqueue(object : Callback<PeliculaID> {
            override fun onResponse(call: Call<PeliculaID>, response: Response<PeliculaID>) {
                val activity: PeliculaID? = response.body()
                if (response.isSuccessful && activity != null){
                    listener.onResponse(
                        RepositoryResponse(
                            data = activity,
                            source = Source.REMOTE
                        )
                    )
                }else {
                    listener.onError(
                        RepositoryError(
                            message = "no hubo conexion",
                            code = response.code(),
                            source = Source.REMOTE
                        )
                    )
                }}

            override fun onFailure(call: Call<PeliculaID>, t: Throwable) {
                listener.onError(
                    RepositoryError(
                        message = t.message ?: "Error inesperado",
                        code = -1 ,
                        source = Source.REMOTE
                    )
                )
            }
        })
    }


}