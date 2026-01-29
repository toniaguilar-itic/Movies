package cat.iticbcn.myapplication

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface MovieApiService {
    @GET
    suspend fun getAllMovies(@Url url:String): Response<List<Movie>>
}