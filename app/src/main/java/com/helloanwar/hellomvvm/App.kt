package com.helloanwar.hellomvvm

import android.app.Application
import androidx.room.Room
import com.github.ajalt.timberkt.Timber
import com.helloanwar.hellomvvm.data.source.local.HelloMVVMDatabase

class App : Application() {

    val database by lazy {
        Room.databaseBuilder(
            applicationContext,
            HelloMVVMDatabase::class.java, "hello-mvvm-db"
        ).build()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}