package cat.iticbcn.myapplication

import retrofit2.Response
import retrofit2.http.GET

interface MovieApiService {
    @GET("movies/popular")
    suspend fun getPopularMovies(): Response<MovieResponse>
}