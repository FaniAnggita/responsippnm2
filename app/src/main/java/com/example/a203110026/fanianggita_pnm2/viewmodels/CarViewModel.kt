package com.example.a203110026.fanianggita_pnm2.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.a203110026.fanianggita_pnm2.database.getDatabase
import com.example.a203110026.fanianggita_pnm2.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

// TODO 7: Kelas View Model untuk mengolah data ke UI
class CarViewModel(application: Application) : AndroidViewModel(application) {

    // Mengambil list data dari repositori
    private val videosRepository = VideosRepository(getDatabase(application))

    //untuk menampilkan list data ke UI
    val playlist = videosRepository.videos

    //    Variabel untuk mengembalikan nilai boolean (true/ false) apabila terjadi kesalahan jaringan.
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    //    Mengembalikan data kesalahan jaringan
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    // Mengembalikan pesan error jaringan
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    // Init{} langsung dipanggil saat ViewModel ini dibuat.
    init {
        refreshDataFromRepository()
    }

    // refresh data dari repositori menggunakan coroutine yang berjalan di background aplikasi
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                videosRepository.refreshVideos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Jika network error maka akan menampilkan kesalahan jariangan
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    // reset flag kesalahan jariangan
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /*
    Factory ViewModel yang sama dapat digunakan untuk beberapa ViewModel saat berbagi dependensi.
    Jika class ViewModel menerima dependensi dalam konstruktornya, sediakan factory yang mengimplementasikan antarmuka ViewModelProvider.Factory.
    fungsi create(Class<T>, CreationExtras) untuk memberikan instance ViewModel baru.
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CarViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
