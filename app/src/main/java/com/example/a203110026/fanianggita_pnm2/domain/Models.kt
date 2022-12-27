package com.example.a203110026.fanianggita_pnm2.domain

import com.example.a203110026.fanianggita_pnm2.util.smartTruncate

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */

/**
 * Videos represent a devbyte that can be played.
 */
data class CarModel(val title: String,
                    val description: String,
                    val image_url_tumbnail: String,
                    val date: String,
                    val available_count: String) {

    /**
     * Short description is used for displaying truncated descriptions in the UI
     */
    val shortDescription: String
        get() = description.smartTruncate(200)
}