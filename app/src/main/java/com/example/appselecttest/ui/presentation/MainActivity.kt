package com.example.appselecttest.ui.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appselecttest.databinding.ActivityMainBinding
import com.example.appselecttest.domain.model.NetworkStatus
import com.example.appselecttest.ui.recycler.MovieAdapter
import com.example.appselecttest.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MovieViewModel by viewModels()
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapter()
    private var checkLoad = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        recyclerView = binding.movieRecyclerView
        recyclerView.adapter = adapter
        scrollList()

        viewModel.status.observe(this) {
            when (it) {
                NetworkStatus.ERROR -> errorEvent()
                NetworkStatus.LOADING -> checkLoad = true
                NetworkStatus.DONE -> successEvent()
                else -> throw Exception("Неизвестное состояние")
            }
        }
    }

    private fun scrollList() {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!checkLoad) {
                    val maxSizeItem = layoutManager.itemCount
                    val lastItem = layoutManager.findLastVisibleItemPosition()
                    if (lastItem + 3 >= maxSizeItem) {
                        checkLoad = true
                        viewModel.loadDataNetwork()
                    }
                }
            }
        })
    }

    private fun errorEvent() {
        Toast.makeText(
            this,
            "Error network",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun successEvent() {
        checkLoad = false
        binding.progressBar.visibility = View.GONE
        viewModel.movies.observe(this) {
            adapter.submitList(viewModel.movies.value)
            checkLoad = false
        }
    }
}