package com.example.perpustakaan

import android.app.Application
import com.example.perpustakaan.data.di.AppContainer
import com.example.perpustakaan.data.di.PerpustakaanContainer

class PerpustakaanAplications : Application() {
    lateinit var container: AppContainer

    override fun  onCreate() {
        super.onCreate()
        container = PerpustakaanContainer()
    }
}