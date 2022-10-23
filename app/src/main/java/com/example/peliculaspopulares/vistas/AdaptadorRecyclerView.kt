package com.example.peliculaspopulares.vistas


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.peliculaspopulares.BuildConfig
import com.example.peliculaspopulares.R
import com.example.peliculaspopulares.data.Peliculas
import com.example.peliculaspopulares.databinding.VistaPrincipalBinding

class AdaptadorRecyclerView(val context : Context): RecyclerView.Adapter<AdaptadorRecyclerView.ItemViewHolder>(){

    var data = mutableListOf<Peliculas>()
    var onClick: ((Peliculas) -> Unit)? = null

    fun setPeliculas(newPeliculas : List<Peliculas>){
        data.clear()
        data.addAll(newPeliculas)
        notifyDataSetChanged()

    }

    class ItemViewHolder(private val binding: VistaPrincipalBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(peliculas: Peliculas) {

            binding.tituloPelicula.text = peliculas.titulo
            binding.posterPelicula.load(BuildConfig.BASE_URL_IMAGEN + peliculas.posterpath) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdaptadorRecyclerView.ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VistaPrincipalBinding.inflate(layoutInflater, parent, false)
        return AdaptadorRecyclerView.ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdaptadorRecyclerView.ItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener{
            onClick?.invoke(data[position])
        }
    }

    override fun getItemCount(): Int {
        return  data.size
    }

}




