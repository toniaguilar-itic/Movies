package cat.iticbcn.myapplication.data.network

import cat.iticbcn.myapplication.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApiService {
    @GET
    suspend fun getAllMovies(@Url url:String): Response<List<Movie>>
}