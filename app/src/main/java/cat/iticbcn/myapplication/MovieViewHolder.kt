package cat.iticbcn.myapplication

import androidx.recyclerview.widget.RecyclerView
import cat.iticbcn.myapplication.databinding.ItemMovieBinding

class MovieViewHolder(
    private val binding: ItemMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvMovieTitle.text = movie.title
        binding.tvImage.text = movie.poster
        binding.tvRating.text = movie.rating.toString()
    }
}