package com.example.a203110026.fanianggita_pnm2.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.a203110026.fanianggita_pnm2.domain.CarModel


/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */


/**
 * DatabaseVideo represents a video entity in the database.
 */
@Entity
data class DatabaseVideo constructor(
        @PrimaryKey
        val title: String,
        val description: String,
        val image_url_tumbnail: String,
        val date: String,
        val available_count: String)


/**
 * Map DatabaseVideos to domain entities
 */
fun List<DatabaseVideo>.asDomainModel(): List<CarModel> {
        return map {
                CarModel(
                        title = it.title,
                        description = it.description,
                        image_url_tumbnail = it.image_url_tumbnail,
                        date = it.date,
                        available_count = it.available_count)
        }
}
