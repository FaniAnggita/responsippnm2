/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.a203110026.fanianggita_pnm2.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.a203110026.fanianggita_pnm2.database.VideosDatabase
import com.example.a203110026.fanianggita_pnm2.database.asDomainModel
import com.example.a203110026.fanianggita_pnm2.domain.CarModel
import com.example.a203110026.fanianggita_pnm2.network.CarNetwork
import com.example.a203110026.fanianggita_pnm2.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

// TODO 6: Kelas Repositori untuk proses menyimpan data dari API ke ROOM DB
class VideosRepository(private val database: VideosDatabase) {

    // Menginisialisasi list CarModel menjadi hasil mapping dari database
    val videos: LiveData<List<CarModel>> = Transformations.map(database.carDao.getVideos()) {
        it.asDomainModel()
    }

    // Untuk proses refresh data yang disimpan di cache offline
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh berhasil!")
            val playlist = CarNetwork.devbytes.getPlaylist()
            // Insert data ke database
            database.carDao.insertAll(playlist.asDatabaseModel())
        }
    }

}
