package com.example.appselecttest.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appselecttest.R
import com.example.appselecttest.databinding.FragmentSplashScreenBinding
import com.example.appselecttest.domain.model.NetworkStatus
import com.example.appselecttest.ui.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding
    private val viewModel: MovieViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.status.observe(viewLifecycleOwner) {
            when (viewModel.status.value) {
                NetworkStatus.DONE -> {
                    loadHomePage()
                }

                NetworkStatus.ERROR -> {
                    binding.apply {
                        errorImage.visibility = View.VISIBLE
                        errorMessage.visibility = View.VISIBLE
                        progressBarSplashLoad.visibility = View.INVISIBLE
                    }
                }
            }
        }
    }

    private fun loadHomePage() {
        parentFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .replace(R.id.fragmentContainerView, HomeMovieFragment())
            .commit()
    }
}