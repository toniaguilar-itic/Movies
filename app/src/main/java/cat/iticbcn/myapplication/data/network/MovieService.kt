package cat.iticbcn.myapplication.data.network

import cat.iticbcn.myapplication.core.RetrofitHelper
import cat.iticbcn.myapplication.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getAllMovies(url:String): List<Movie> {
        return withContext(Dispatchers.IO) {
            val response = retrofit.create(MovieApiService::class.java).getAllMovies(url)
            response.body() ?: emptyList()
        }
    }
}