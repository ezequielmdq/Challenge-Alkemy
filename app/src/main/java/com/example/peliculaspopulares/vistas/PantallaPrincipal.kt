package com.example.peliculaspopulares.vistas

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peliculaspopulares.databinding.FragmentPantallaPrincipalBinding
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModelFactory


class PantallaPrincipal : Fragment() {

    private lateinit var binding : FragmentPantallaPrincipalBinding

    private lateinit var adapter : AdaptadorRecyclerView

    private val viewModel : PeliculasPopularesViewModel by activityViewModels(
        factoryProducer = { PeliculasPopularesViewModelFactory() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPantallaPrincipalBinding.inflate(layoutInflater)
        binding.recyclerPeliculas.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { AdaptadorRecyclerView(it) }!!
        binding.recyclerPeliculas.adapter = adapter

        // Consulta a api

        viewModel.cargar()

        // Observer que actualiza lista de peliculas

        viewModel.pelis.observe(viewLifecycleOwner) {
                newPeliculas -> adapter?.setPeliculas(newPeliculas)

        }

        adapter.onClick = {
            var a = it
            val intent = Intent(context, DetallesPeliculas::class.java).also {
                it.putExtra("pelicula_id", a.id)
                it.putExtra("poster_id", a.backdrop)}
            startActivity(intent)
        }



            return binding.root
        }
    }