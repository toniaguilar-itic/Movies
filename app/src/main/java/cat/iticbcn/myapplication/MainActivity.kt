package cat.iticbcn.myapplication

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import cat.iticbcn.myapplication.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
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
        //setupMoviesList()
        binding.svSearch.setOnQueryTextListener(this)
        initRecyclerView()
        initUI()
    }

    private fun initUI() {
        //val llBotons = findViewById<LinearLayout>(R.id.llBotons)

        EndPoint.values().forEach { endpoint ->
            val button = MaterialButton(this).apply {
                text = endpoint.name.lowercase().replaceFirstChar { it.uppercase() }
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                isCheckable=true
                isChecked=false
                setOnClickListener {
                    val selector = (it as MaterialButton).text.toString().lowercase()
                    if (it.isChecked)
                        setupMoviesList("$selector/")
                    else
                        removeMoviesFromList("$selector/")
                }
            }
            button.setPaddingRelative(8, 4, 8, 4)
            button.insetTop = 0
            button.insetBottom = 0

            button.backgroundTintList = ContextCompat.getColorStateList(
                this,
                R.color.button_bg_selector
            )

            button.setTextColor(
                ContextCompat.getColorStateList(
                    this,
                    R.color.button_text_selector
                )
            )

            binding.gridBotons.addView(button)
        }

    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(selectedMovies)
        binding.rvMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        binding.rvMovies.adapter = adapter
    }

    private fun getRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.sampleapis.com/movies/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun setupMoviesList(selection : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: retrofit2.Response<List<Movie>> = getRetrofit().create(MovieApiService::class.java).getAllMovies(selection)

            runOnUiThread {
                if (call.isSuccessful) {
                    val retrievedMovies: List<Movie> = call.body() ?: emptyList()
                    if (!retrievedMovies.isEmpty()) {
                        //movies.clear()
                        movies.addAll(retrievedMovies)
                        movies.sortBy { it.title }
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

    private fun removeMoviesFromList(selection : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call: retrofit2.Response<List<Movie>> = getRetrofit().create(MovieApiService::class.java).getAllMovies(selection)

            runOnUiThread {
                if (call.isSuccessful) {
                    val retrievedMovies: List<Movie> = call.body() ?: emptyList()
                    if (!retrievedMovies.isEmpty()) {
                        //movies.clear()
                        movies.removeAll(retrievedMovies)
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