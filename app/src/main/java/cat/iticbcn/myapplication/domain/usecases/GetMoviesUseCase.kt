package cat.iticbcn.myapplication.domain.usecases

import cat.iticbcn.myapplication.data.MovieRepository
import cat.iticbcn.myapplication.data.model.Movie

class GetMoviesUseCase () {
    private val repository = MovieRepository()

    suspend operator fun invoke (url:String) : List<Movie> = repository.getAllMovies(url)
}