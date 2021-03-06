package com.universodoandroid.starwarsjetpack

import android.app.Application
import com.universodoandroid.starwarsjetpack.data.di.getRepositoryModules
import com.universodoandroid.starwarsjetpack.domain.di.getUseCaseModules
import com.universodoandroid.starwarsjetpack.local.di.getDatabaseModules
import com.universodoandroid.starwarsjetpack.presentation.di.getViewModelsModules
import com.universodoandroid.starwarsjetpack.remote.di.getRemoteModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

@Suppress("Unused")
class StarWarsJetpackApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@StarWarsJetpackApplication)
            modules(
                listOfModules(
                    getRemoteModules(),
                    getDatabaseModules(),
                    getRepositoryModules(),
                    getUseCaseModules(),
                    getViewModelsModules()
                )
            )
        }
    }

    private fun listOfModules(vararg modules: List<Module>): List<Module> {
        val mappedModules = ArrayList<Module>()
        modules.forEach { mappedModules.addAll(it) }
        return mappedModules
    }
}
