package ru.maxmv.notes.di

import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

import ru.maxmv.notes.data.NoteRepository
import ru.maxmv.notes.data.db.AppDatabase

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "notes"
        ).fallbackToDestructiveMigration().build()
    }

    single {
        get<AppDatabase>().getNoteDao()
    }

    single {
        NoteRepository(get())
    }
}