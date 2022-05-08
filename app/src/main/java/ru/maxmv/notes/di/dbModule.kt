package ru.maxmv.notes.di

import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

import ru.maxmv.notes.data.NoteRepository
import ru.maxmv.notes.data.db.AppDatabase

val dbModule = module {
    single {
        AppDatabase.getInstance(androidApplication())
    }

    single {
        get<AppDatabase>().getNoteDao()
    }

    single {
        NoteRepository(get())
    }
}