package com.example.peliculaspopulares.vistas


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.peliculaspopulares.PeliculasApplication
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.data.PeliculasDAO
import com.example.peliculaspopulares.data.PeliculasDAOID
import com.example.peliculaspopulares.databinding.FragmentPantallaPrincipalBinding
import com.example.peliculaspopulares.model.PeliculaDaoViewModel
import com.example.peliculaspopulares.model.PeliculasPopularesViewModel


/**

class PantallaPrincipal : Fragment() {

    private lateinit var binding: FragmentPantallaPrincipalBinding

    private lateinit var adapter: AdaptadorRecyclerView

    private val viewModel : PeliculasPopularesViewModel by activityViewModels {PeliculasPopularesViewModel.Factory}

    private val peliculaDaoViewModel: PeliculaDaoViewModel by activityViewModels {
        PeliculaDaoViewModel.PeliculaDaoViewModelFactory((activity?.application as PeliculasApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPantallaPrincipalBinding.inflate(layoutInflater)
        binding.recyclerPeliculas.layoutManager = LinearLayoutManager(context)
        adapter = context?.let { AdaptadorRecyclerView(it) }!!
        binding.recyclerPeliculas.adapter = adapter

        peliculaDaoViewModel.delete()

        /** Consulta a api */

        viewModel.getPeliculas()

        viewModel.getListaPeliId()


        /** Observer que actualiza lista de peliculas */

        viewModel.pelis.observe(viewLifecycleOwner) { newPeliculas ->

            /**Carga de peliculas en base de datos*/
            peliculaDaoViewModel.insert(newPeliculas.map {
                PeliculasDAO(
                    it.id,
                    it.titulo,
                    it.backdrop,
                    it.posterpath
                )
            }
            )
        }

        peliculaDaoViewModel.allPelis.observe(viewLifecycleOwner) { pelis ->

            adapter.setPeliculas( pelis.map {
                Peliculas(
                    id = it.id,
                    titulo = it.titulo,
                    posterpath = it.backdrop,
                    backdrop = it.posterpath
                )
            }
            )
        }

        viewModel.listapeliculasid.observe(viewLifecycleOwner) { p ->

            peliculaDaoViewModel.insertID( p.map {
                PeliculasDAOID(
                    id = it.id,
                    titulo = it.titulo,
                    descipcion = it.descipcion,
                    porcenjatevotos = it.porcenjatevotos,
                    lenguaje = it.lenguaje,
                    fechalanzamiento = it.fechalanzamiento,
                    poster = it.poster
                )
            }
            )
        }

        mensajeBienvenida()

        adapter.onClick = {

                     viewModel.getPeliculaId(it.id)
                     peliculaDaoViewModel.datoDetalle(it.id)
                     getFragmentManager()?.let { DetallesPeliculas().show(it, "DialogFragment") }
        }


        return binding.root

    }

    /** Mensaje de bienvenida */

    fun mensajeBienvenida() {

            Toast.makeText(context, getString(R.string.bienvenido), Toast.LENGTH_SHORT).show()

    }


}

*/
