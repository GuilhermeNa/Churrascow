package br.com.apps.churrascow

import android.app.Application
import br.com.apps.churrascow.di.modules.appModules
import br.com.apps.churrascow.preferences.dataStore
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }


}