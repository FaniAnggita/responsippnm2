package com.example.a203110026.fanianggita_pnm2.network

import com.example.a203110026.fanianggita_pnm2.database.DatabaseVideo
import com.example.a203110026.fanianggita_pnm2.domain.CarModel
import com.squareup.moshi.JsonClass

// TODO 2: Melakukan parsing data hasil respon dari API
@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val data: List<NetworkVideo>)

// mendefinsikan model
@JsonClass(generateAdapter = true)
data class NetworkVideo(
        val title: String,
        val description: String,
        val image_url_tumbnail: String,
        val date: String,
        val available_count: String,
        val is_purchased: String?)

// menkonversi data hasil respon menjadi format Object
fun NetworkVideoContainer.asDomainModel(): List<CarModel> {
    return data.map {
        CarModel(
                title = it.title,
                description = it.description,
                image_url_tumbnail = it.image_url_tumbnail,
                date = it.date,
                available_count = it.available_count)
    }
}


// menkonversi data hasil respon menjadi database Object
fun NetworkVideoContainer.asDatabaseModel(): List<DatabaseVideo> {
    return data.map {
        DatabaseVideo(
            title = it.title,
            description = it.description,
            image_url_tumbnail = it.image_url_tumbnail,
            date = it.date,
            available_count = it.available_count)
    }
}

