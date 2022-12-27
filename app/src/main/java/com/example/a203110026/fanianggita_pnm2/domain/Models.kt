package com.example.a203110026.fanianggita_pnm2.domain

import com.example.a203110026.fanianggita_pnm2.util.smartTruncate

// TODO 5: Kelas model untuk merepresentasikan data yang akan diproses.
// Menentukan properti atau atribut kelas model ini
data class CarModel(val title: String,
                    val description: String,
                    val image_url_tumbnail: String,
                    val date: String,
                    val available_count: String) {

   // trucate kata pada properti description
    val shortDescription: String
        get() = description.smartTruncate(150)
}