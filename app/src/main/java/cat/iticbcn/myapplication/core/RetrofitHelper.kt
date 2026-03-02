package cat.iticbcn.myapplication.core

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    /**
     * Get retrofit
     * Obté una instància del Retrofit apuntant a la url i esperant resposta en
     * json per convertir-la a objecte de negoci
     * @return un objecte Retrofit
     */
     public fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.sampleapis.com/movies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}