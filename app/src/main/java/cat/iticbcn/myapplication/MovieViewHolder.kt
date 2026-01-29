package cat.iticbcn.myapplication

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import cat.iticbcn.myapplication.databinding.ItemMovieBinding
import com.squareup.picasso.Picasso

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)
    fun bind(movie: Movie) {
        Picasso.get().load(movie.image).into(binding.ivMovie)
        binding.tvMovieTitle.text = movie.title
        binding.tvRefernce.text = movie.reference.toString()
    }
}