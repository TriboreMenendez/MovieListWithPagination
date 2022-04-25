package com.example.appselecttest.ui.viewmodel

import androidx.lifecycle.*
import com.example.appselecttest.domain.model.MovieDomainModel
import com.example.appselecttest.domain.model.NetworkStatus
import com.example.appselecttest.domain.model.SuccessRequest
import com.example.appselecttest.domain.usecase.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor (private val useCase: GetMovieUseCase) : ViewModel() {

    private val _status = MutableLiveData<NetworkStatus>()
    val status: LiveData<NetworkStatus> = _status

    private val _movies = MutableLiveData<List<MovieDomainModel>>()
    val movies: LiveData<List<MovieDomainModel>> = _movies

    private lateinit var requestResult: SuccessRequest

    init {
        loadDataNetwork()
    }

    fun loadDataNetwork() = viewModelScope.launch {
        _status.value = NetworkStatus.LOADING

        requestResult = useCase.getMovie()

        if (requestResult.error == null && requestResult.listResult.isNotEmpty()) {
            _movies.value =
                _movies.value?.plus(requestResult.listResult) ?: requestResult.listResult
            _status.value = NetworkStatus.DONE
        } else {
            _status.value = NetworkStatus.ERROR
        }
    }
}