package com.example.a203110026.fanianggita_pnm2.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

// Since we only have one service, this can all go in one file.
// If you add more services, split this to multiple files and make sure to share the retrofit
// object between services.

/**
 * A retrofit service to fetch a devbyte playlist.
 */
interface CarService {
    @GET("home")
    suspend fun getPlaylist(): NetworkVideoContainer
}

/**
 * Main entry point for network access. Call like `CarNetwork.devbytes.getPlaylist()`
 */
object CarNetwork {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://testapi.io/api/crosscodedj/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val devbytes = retrofit.create(CarService::class.java)

}

//interface CarService {
//    @GET("devbytes")
//    suspend fun getPlaylist(): NetworkVideoContainer
//}
//
///**
// * Main entry point for network access. Call like `CarNetwork.devbytes.getPlaylist()`
// */
//object CarNetwork {
//
//    // Configure retrofit to parse JSON and use coroutines
//    private val retrofit = Retrofit.Builder()
//            .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//
//    val devbytes = retrofit.create(CarService::class.java)
//
//}


