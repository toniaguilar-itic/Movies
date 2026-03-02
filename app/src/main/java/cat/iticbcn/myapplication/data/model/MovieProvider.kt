package cat.iticbcn.myapplication.data.model

class MovieProvider {
    companion object {
        var movies : List<Movie> = emptyList()

        /*
        private val movies = listOf(
            Movie( "Black Panther", "https://m.media-amazon.com/images/M/MV5BMTg1MTY2MjYzNV5BMl5BanBnXkFtZTgwMTc4NTMwNDI@._V1_SX300.jpg", "tt1825683"),
            Movie("Lady Bird", "https://m.media-amazon.com/images/M/MV5BODhkZGE0NDQtZDc0Zi00YmQ4LWJiNmUtYTY1OGM1ODRmNGVkXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_SX300.jpg","tt4925292")
        )

        fun getAllMoview() : List<Movie> {
            return movies
        }

        fun getOneRandomMovie() : Movie {
            val position = (1..movies.size-1).random()
            return movies[position]
        }
         */
    }
}