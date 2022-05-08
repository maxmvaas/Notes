package ru.maxmv.notes.data.db

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import ru.maxmv.notes.data.db.dao.NoteDao

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "notes"
            ).fallbackToDestructiveMigration().build()
        }
    }
}