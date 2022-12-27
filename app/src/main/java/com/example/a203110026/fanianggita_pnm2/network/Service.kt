package com.example.a203110026.fanianggita_pnm2.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// TODO 1: Bagian Retrofit untuk mengambil data dari API
interface CarService {
    // untuk mengirim permintaan data ke API
    @GET("home")
    suspend fun getPlaylist(): NetworkVideoContainer
}

// konfigurasi retrofit menentukan akses data ke alamat API
object CarNetwork {

    // proses retrofit untuk parsing data ke format JSON menggunakan Moshi
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://testapi.io/api/crosscodedj/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val devbytes = retrofit.create(CarService::class.java)

}



