package cat.iticbcn.myapplication.domain.usecases

import cat.iticbcn.myapplication.data.MovieRepository
import cat.iticbcn.myapplication.data.model.Movie

class GetOneRandomMovieUseCase (){
    private val repository = MovieRepository()

    suspend operator fun invoke (url : String) : Movie {
        val position = (1..repository.getAllMovies(url).size-1).random()
        return repository.getAllMovies(url)[position]
    }
}