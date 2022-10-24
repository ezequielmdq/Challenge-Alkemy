package com.example.peliculaspopulares.vistas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.databinding.ActivityDetallesPeliculasBinding
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModelFactory
import kotlin.math.roundToInt

class DetallesPeliculas : DialogFragment() {



    private lateinit var binding: ActivityDetallesPeliculasBinding
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
        binding = ActivityDetallesPeliculasBinding.inflate(inflater, container, false)

        /** Se carga el valor de la descripcion observando el livedata*/
        viewModel.pelisid.observe(viewLifecycleOwner) { pelisid ->
            binding.tvDescripcionValor.text = pelisid
        }
        /** Se carga el valor de la fecha de lanzamiento observando el livedata*/
        viewModel.pelisfechalanzamiento.observe(viewLifecycleOwner) {
            binding.tvFechaEstrenoValor.text = viewModel.pelisfechalanzamiento.value
        }
        /** Se carga el valor de la calificacion observando el livedata*/
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
        /** Se carga el valor del poster back observando el livedata*/
        viewModel.poster.observe(viewLifecycleOwner){ poster ->
        binding.backdrop.load("https://image.tmdb.org/t/p/original${poster}") {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }}
        /** Se carga el valor de la descripcion observando el livedata*/
        viewModel.pelislenguaje.observe(viewLifecycleOwner){
            binding.tvLenguajeValor.text = viewModel.pelislenguaje.value

        }
        /** Se carga el valor del genero observando el livedata*/
        viewModel.pelisgenero.observe(viewLifecycleOwner){
            generos -> var textogeneros = ""
            for (genero in generos){
                textogeneros += "${genero.name} "
            }
            binding.tvGeneroValor.text = textogeneros

        }

        return binding.root

    }

}










