package com.example.peliculaspopulares.vistas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.content.res.AppCompatResources.getDrawable


import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.databinding.ActivityDetallesPeliculasBinding
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModelFactory
import kotlin.math.roundToInt

class DetallesPeliculas : DialogFragment() {



    private lateinit var binding: ActivityDetallesPeliculasBinding
    private val viewModel : PeliculasPopularesViewModel by activityViewModels(
        factoryProducer = { PeliculasPopularesViewModelFactory() }
    )

    var peliculaid = "hola"
    var posterid = "hola"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityDetallesPeliculasBinding.inflate(inflater, container, false)

        setFragmentResultListener("requestKey1",) { key, bundle ->
            val resultImagen = bundle.getString("bundleKey1")
            if (resultImagen != null) {
                posterid = resultImagen
                println(posterid)
                if (peliculaid != null) {
                    viewModel.cargarID(peliculaid)
                }
            }
        }
        setFragmentResultListener("requestKey2",) { key, bundle ->
            val resultNombre = bundle.getString("bundleKey2")
            if (resultNombre != null) {
                peliculaid = resultNombre
            }
            println(peliculaid)

        }

        println(posterid)
        println(peliculaid)

        cargarDatos()


        viewModel.pelisid.observe(viewLifecycleOwner) { pelisid ->
            binding.tvDescripcion.text = pelisid
        }
        //{
          //  binding.tvDescripcionValor.text = viewModel.pelisid.value.toString()

        //}

        viewModel.pelisfechalanzamiento.observe(viewLifecycleOwner) {
            binding.tvFechaEstrenoValor.text = viewModel.pelisfechalanzamiento.value
        }
        viewModel.peliscalificacion.observe(viewLifecycleOwner) {
            val porcentaje = viewModel.peliscalificacion.value
            if (porcentaje != null) {
                binding.tvCalificacionValor.text = porcentaje.roundToInt().toString()
            }
            val redondeo = porcentaje?.roundToInt()
            if (redondeo != null) {
                if (redondeo == 1) {
                    binding.estrella1.setImageDrawable(context?.let { it1 -> getDrawable(it1, R.drawable.estrellita) })
                } else if (redondeo <= 3) {
                    binding.apply {
                        estrella1.setImageDrawable(context?.let { it1 -> getDrawable(it1, R.drawable.estrellita) })
                        estrella2.setImageDrawable(context?.let { it1 -> getDrawable(it1, R.drawable.estrellita) })
                    }
                } else if (redondeo <= 6) {
                    binding.apply {
                        estrella1.setImageDrawable(context?.let { it1 -> getDrawable(it1, R.drawable.estrellita) })
                        estrella2.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella3.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                    }
                } else if (redondeo <= 9) {
                    binding.apply {
                        estrella1.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella2.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella3.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella4.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                    }
                } else {
                    binding.apply {
                        estrella1.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella2.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella3.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella4.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                        estrella5.setImageDrawable(context?.let { it1 -> getDrawable(it1,R.drawable.estrellita) })
                    }
                }
            }
        }

        binding.backdrop.load("https://image.tmdb.org/t/p/original${posterid}") {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

        viewModel.pelislenguaje.observe(viewLifecycleOwner){
            binding.tvLenguajeValor.text = viewModel.pelislenguaje.value

        }

        viewModel.pelisgenero.observe(viewLifecycleOwner){
            generos -> var textogeneros = ""
            for (genero in generos){
                textogeneros += "${genero.name} "
            }
            binding.tvGeneroValor.text = textogeneros

        }

        return binding.root

    }

    fun cargarDatos(){




    }

}










