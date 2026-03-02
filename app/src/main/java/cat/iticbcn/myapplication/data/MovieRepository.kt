package cat.iticbcn.myapplication.data

import cat.iticbcn.myapplication.data.model.Movie
import cat.iticbcn.myapplication.data.model.MovieProvider
import cat.iticbcn.myapplication.data.network.MovieService

class MovieRepository {
    private val api = MovieService()

    suspend fun getAllMovies(url: String):List<Movie> {
        val response: List<Movie> = api.getAllMovies(url)
        MovieProvider.movies = response
        return response
    }
}