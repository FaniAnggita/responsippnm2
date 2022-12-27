package com.example.a203110026.fanianggita_pnm2.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

// TODO 3: kelas Room Database.
/*
Kelas @Dao untuk abstraksi yang memiliki konstruktor untuk mengambil Database.
 */
@Dao
interface CarDao {
    // melakukan query atau mengambil data dari database
    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    // insert data ke database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( videos: List<DatabaseVideo>)
}

// Kelas untuk entitas Database
@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
    //    mengembalikan abstraksi kelas DAO
    abstract val carDao: CarDao
}

// Mengembalikan instan database
private lateinit var INSTANCE: VideosDatabase

/*
Membuat Room Database untuk database persisten.
*/
fun getDatabase(context: Context): VideosDatabase {
    synchronized(VideosDatabase::class.java) {
        // Mengembalikan nilai true jika properti lateinit ini telah diberi nilai, dan false jika sebaliknya.
        if (!::INSTANCE.isInitialized) {
            // Membuat database dan menginisialisasinya.
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    VideosDatabase::class.java,
                    "data").build()
        }
    }
    // Mengembalikan instan database
    return INSTANCE
}
