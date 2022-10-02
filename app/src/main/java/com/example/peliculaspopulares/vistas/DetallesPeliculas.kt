package com.example.peliculaspopulares.vistas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import coil.load
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.databinding.ActivityDetallesPeliculasBinding
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModelFactory
import kotlin.math.roundToInt

class


DetallesPeliculas : AppCompatActivity() {



    private lateinit var binding: ActivityDetallesPeliculasBinding
    private val viewModel : PeliculasPopularesViewModel by viewModels(
        factoryProducer = { PeliculasPopularesViewModelFactory() }
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetallesPeliculasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peliculaid = intent.getStringExtra("pelicula_id")
        val posterid = intent.getStringExtra("poster_id")





        if (peliculaid != null) {
            viewModel.cargarID(peliculaid)
        }
        viewModel.pelisid.observe(this) {
            binding.tvDescripcionValor.text = viewModel.pelisid.value.toString()

        }

        viewModel.pelisfechalanzamiento.observe(this) {
            binding.tvFechaEstrenoValor.text = viewModel.pelisfechalanzamiento.value
        }
        viewModel.peliscalificacion.observe(this) {
            val porcentaje = viewModel.peliscalificacion.value
            if (porcentaje != null) {
                binding.tvCalificacionValor.text = porcentaje.roundToInt().toString()
            }
            val redondeo = porcentaje?.roundToInt()
            if (redondeo != null) {
                if (redondeo == 1) {
                    binding.estrella1.setImageDrawable(getDrawable(R.drawable.estrellita))
                } else if (redondeo <= 3) {
                    binding.apply {
                        estrella1.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella2.setImageDrawable(getDrawable(R.drawable.estrellita))
                    }
                } else if (redondeo <= 6) {
                    binding.apply {
                        estrella1.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella2.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella3.setImageDrawable(getDrawable(R.drawable.estrellita))
                    }
                } else if (redondeo <= 9) {
                    binding.apply {
                        estrella1.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella2.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella3.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella4.setImageDrawable(getDrawable(R.drawable.estrellita))
                    }
                } else {
                    binding.apply {
                        estrella1.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella2.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella3.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella4.setImageDrawable(getDrawable(R.drawable.estrellita))
                        estrella5.setImageDrawable(getDrawable(R.drawable.estrellita))
                    }
                }
            }
        }

        binding.backdrop.load("https://image.tmdb.org/t/p/original${posterid}") {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

        viewModel.pelislenguaje.observe(this){
            binding.tvLenguajeValor.text = viewModel.pelislenguaje.value

        }

        viewModel.pelisgenero.observe(this){
            generos -> var textogeneros = ""
            for (genero in generos){
                textogeneros += "${genero.name} "
            }
            binding.tvGeneroValor.text = textogeneros

        }



    }

}










