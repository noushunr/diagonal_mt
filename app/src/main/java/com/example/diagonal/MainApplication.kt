package com.example.diagonal

import android.app.Application
import android.content.Context
import com.example.diagonal.data.repositories.BooksListRepositories
import com.example.diagonal.ui.ListViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


/**
 *Created by Noushad on 02-05-22
**/

class MainApplication : Application(), KodeinAware {

    companion object {
        lateinit var instance: MainApplication
            private set
        val appContext: Context
            get() {
                return instance.applicationContext
            }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))
        bind() from singleton { BooksListRepositories(instance()) }
        bind() from provider { ListViewModelFactory(instance()) }
    }

}