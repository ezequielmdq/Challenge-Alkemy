package com.example.peliculaspopulares.repositorio


interface ResponseListener<T> {

    fun onResponse(response: RepositoryResponse<T>)

    fun onError(error: RepositoryError)

}