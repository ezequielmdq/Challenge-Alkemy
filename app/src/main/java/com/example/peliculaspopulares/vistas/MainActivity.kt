package com.example.peliculaspopulares.vistas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.databinding.ActivityMainBinding
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : PeliculasPopularesViewModel by viewModels(
        factoryProducer = {PeliculasPopularesViewModelFactory()}
    )


    val adapter = AdaptadorRecyclerView(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Carga de recycler view y adapter
        
        binding.recyclerPeliculas.layoutManager = LinearLayoutManager(this)
        binding.recyclerPeliculas.adapter = adapter

        // Consulta a api

        viewModel.cargar()

        // Observer que actualiza lista de peliculas

        viewModel.pelis.observe(this){
            newPeliculas -> adapter.setPeliculas(newPeliculas)
        }

        //Listener que verifica la pelicula seleccionada

        adapter.setOnclicklistener(object : PeliculaClickListener {
            override fun itemClick(position: Int) {
                val intent = Intent(this@MainActivity, DetallesPeliculas::class.java).also {
                    it.putExtra("pelicula_id", viewModel.pelis.value?.get(position)?.id)
                    it.putExtra("poster_id", viewModel.pelis.value?.get(position)?.backdrop)
                }
                startActivity(intent)
            }
        })

        //Observe de viewmodel cuando se genera un error y muestra el el error en pantalla

        viewModel.errorconexion.observe(this){
            binding.fondo.setImageDrawable(getDrawable(R.drawable.ic_connection_error))
            val mensaje = getString(R.string.error_conexion)
            binding.error.text = viewModel.errorconexion.value + "\n" + mensaje
        }

    }






}


