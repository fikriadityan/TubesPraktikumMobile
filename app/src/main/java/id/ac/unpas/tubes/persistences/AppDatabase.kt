package id.ac.unpas.tubes.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.tubes.model.Mobil

@Database(entities = [Mobil::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mobilDao(): MobilDao
}
