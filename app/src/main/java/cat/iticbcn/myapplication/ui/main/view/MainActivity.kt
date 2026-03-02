package cat.iticbcn.myapplication.ui.main.view

import android.os.Bundle
import android.widget.GridLayout
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import cat.iticbcn.myapplication.domain.entities.EndPoint
import cat.iticbcn.myapplication.R
import cat.iticbcn.myapplication.databinding.ActivityMainBinding
import cat.iticbcn.myapplication.ui.main.viewmodel.MainViewModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MovieAdapter

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel.onCreate()

        viewModel.isLoading.observe(this, Observer  {
            binding.pbProgress.isVisible = it
        })

        initRecyclerView()
        initUI()

        binding.svSearch.setOnQueryTextListener(this)

        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedMovies.collect { movies ->
                    adapter.updateList(movies)
                }
            }
        }
    }

    private fun initRecyclerView() {
        adapter = MovieAdapter(emptyList())
        binding.rvMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovies.adapter = adapter
    }

    private fun initUI() {
        EndPoint.entries.forEach { endpoint ->
            /*
            val button = MaterialButton(this).apply {
                text = endpoint.name.lowercase().replaceFirstChar { it.uppercase() }
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }
                isCheckable = true
                isChecked = false

                setPaddingRelative(8, 4, 8, 4)
                insetTop = 0
                insetBottom = 0


                setOnClickListener {
                    val selector = text.toString().lowercase()
                    if (isChecked)
                        viewModel.loadMovies("$selector/")
                    else
                        viewModel.removeMovies("$selector/")
                }


            }

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
            */
            val selector = endpoint.name.lowercase()

            val button = MaterialButton(this).apply {
                text = selector.replaceFirstChar { it.uppercase() }
                isCheckable = true

                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                }

                setPaddingRelative(8, 4, 8, 4)
                insetTop = 0
                insetBottom = 0

                setOnClickListener {
                    viewModel.toggleEndpoint(selector)
                }
            }

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

            observeButtonState(button, selector)
        }
    }

    private fun observeButtonState(button: MaterialButton, selector: String) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedEndpoints.collect { selected ->
                    button.isChecked = selected.contains(selector)
                }
            }
        }
    }
    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.updateQuery(newText.orEmpty())
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.updateQuery(query.orEmpty())
        return true
    }
}