package ru.maxmv.notes

import android.app.Application

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

import ru.maxmv.notes.di.dbModule
import ru.maxmv.notes.di.uiModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(uiModule, dbModule)
        }
    }
}