package com.kindergarten.management

import android.app.Application
import com.kindergarten.management.data.database.KindergartenDatabase

class KindergartenApplication : Application() {
    
    val database by lazy { KindergartenDatabase.getDatabase(this) }
    
    override fun onCreate() {
        super.onCreate()
    }
}