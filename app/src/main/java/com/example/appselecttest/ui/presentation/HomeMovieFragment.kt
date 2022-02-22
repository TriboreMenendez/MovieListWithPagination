package com.example.appselecttest.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appselecttest.databinding.FragmentHomeMovieBinding
import com.example.appselecttest.domain.model.NetworkStatus
import com.example.appselecttest.ui.recycler.MovieAdapter
import com.example.appselecttest.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeMovieFragment : Fragment() {

    private val viewModel: MovieViewModel by sharedViewModel()
    lateinit var binding: FragmentHomeMovieBinding
    lateinit var recyclerView: RecyclerView
    private val adapter = MovieAdapter()
    private var checkLoad = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.movieRecyclerView
        recyclerView.adapter = adapter
        scrollList()
        setItemMovies()

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                NetworkStatus.ERROR -> Toast.makeText(
                    context,
                    "Error network",
                    Toast.LENGTH_SHORT
                ).show()
                NetworkStatus.LOADING -> checkLoad = true
                else -> checkLoad = false
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
                    if (lastItem + 2 >= maxSizeItem) {
                        checkLoad = true
                        viewModel.loadDataNetwork()
                    }
                }
            }
        })
    }

    private fun setItemMovies() {
        viewModel.movies.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.movies.value)
            checkLoad = false
        }
    }
}


