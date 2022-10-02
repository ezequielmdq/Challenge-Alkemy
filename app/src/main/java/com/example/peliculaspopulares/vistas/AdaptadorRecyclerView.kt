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

class AdaptadorRecyclerView(private val context: Context): RecyclerView.Adapter<AdaptadorRecyclerView.ItemViewHolder>(),
    PeliculaClickListener {

    private var data = mutableListOf<Peliculas>()
    private lateinit var listener : PeliculaClickListener

    fun setPeliculas(newPeliculas : List<Peliculas>){
        data.clear()
        data.addAll(newPeliculas)
        notifyDataSetChanged()
    }

    fun setOnclicklistener(listener : PeliculaClickListener){
        this.listener = listener
    }

    class ItemViewHolder(private val binding: VistaPrincipalBinding, val listener : PeliculaClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(peliculas: Peliculas) {
            binding.tituloPelicula.text = peliculas.titulo
            binding.posterPelicula.load(BuildConfig.BASE_URL_IMAGEN + peliculas.posterpath){
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.posterPelicula.setOnClickListener {
                listener.itemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VistaPrincipalBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return  data.size
    }

    override fun itemClick(position: Int) {

    }


}




