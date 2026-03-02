package cat.iticbcn.myapplication.ui.main.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cat.iticbcn.myapplication.R
import cat.iticbcn.myapplication.data.model.Movie

class MovieAdapter(private var movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    fun updateList(newList: List<Movie>) {
        movies = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(layoutInflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = movies[position]
        holder.bind(item)
    }
}