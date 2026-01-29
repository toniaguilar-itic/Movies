package cat.iticbcn.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cat.iticbcn.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter
    private val movies = mutableListOf<Movie>()
    private val selectedMovies = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupMoviesList()
        binding.svSearch.setOnQueryTextListener(this)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(selectedMovies)
        binding.rvMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvMovies.adapter = adapter
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.sampleapis.com/movies/drama/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setupMoviesList() {
        CoroutineScope(Dispatchers.IO).launch {
            val call: retrofit2.Response<List<Movie>> = getRetrofit().create(MovieApiService::class.java).getAllMovies()

            runOnUiThread {
                if (call.isSuccessful) {
                    val retrievedMovies: List<Movie> = call.body() ?: emptyList()
                    if (!retrievedMovies.isEmpty()) {
                        movies.clear()
                        movies.addAll(retrievedMovies)
                        selectMovies()
                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this@MainActivity, "S'ha recuperat un conjunt buit de dades", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Error codi ${call.code()} al recuperar les dades", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun selectMovies() {
        val query = binding.svSearch.query.toString()
        selectedMovies.clear()
        selectedMovies.addAll(movies.filter {
            it.title.contains(query, ignoreCase = true)
        })
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.v("onQueryTextChange", "Text changed: $newText")
        selectMovies()
        adapter.notifyDataSetChanged()
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.v("onQueryTextSubmit", "Text changed: $query")
        selectMovies()
        adapter.notifyDataSetChanged()
        return true
    }

}