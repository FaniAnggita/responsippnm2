package com.example.a203110026.fanianggita_pnm2.network

import com.example.a203110026.fanianggita_pnm2.database.DatabaseVideo
import com.example.a203110026.fanianggita_pnm2.domain.CarModel
import com.squareup.moshi.JsonClass

/**
 * DataTransferObjects go in this file. These are responsible for parsing responses from the server
 * or formatting objects to send to the server. You should convert these to domain objects before
 * using them.
 *
 * @see domain package for
 */

/**
 * VideoHolder holds a list of Videos.
 *
 * This is to parse first level of our network result which looks like
 *
 * {
 *   "videos": []
 * }
 */
@JsonClass(generateAdapter = true)
data class NetworkVideoContainer(val data: List<NetworkVideo>)

/**
 * Videos represent a devbyte that can be played.
 */
@JsonClass(generateAdapter = true)
data class NetworkVideo(
        val title: String,
        val description: String,
        val image_url_tumbnail: String,
        val date: String,
        val available_count: String,
        val is_purchased: String?)

/**
 * Convert Network results to database objects
 */
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


/**
 * Convert Network results to database objects
 */
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

