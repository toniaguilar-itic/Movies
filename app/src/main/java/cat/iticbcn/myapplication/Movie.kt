package cat.iticbcn.myapplication

import com.google.gson.annotations.SerializedName

/*
El json generat per l'API (https://api.sampleapis.com/movies/drama/) a rebre té el seget format:
[
  {
    "id": 1,
    "title": "Black Panther",
    "posterURL": "https://m.media-amazon.com/images/M/MV5BMTg1MTY2MjYzNV5BMl5BanBnXkFtZTgwMTc4NTMwNDI@._V1_SX300.jpg",
    "imdbId": "tt1825683"
  },
  {
    "id": 2,
    "title": "Lady Bird",
    "posterURL": "https://m.media-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg",
    "imdbId": "tt4925292"
  }, [...}
]
*/


data class Movie(
    val title: String,
    @SerializedName("posterURL")
    val image: String,
    @SerializedName("imdbId")
    val reference: String
)
