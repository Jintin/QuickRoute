package com.jintin.quickroute

import android.app.Application
import androidx.room.Room
import com.jintin.quickroute.db.QuickRouteDB
import com.jintin.quickroute.db.ActionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDB(application: Application): QuickRouteDB {
        return Room.databaseBuilder(
            application,
            QuickRouteDB::class.java,
            QuickRouteDB.NAME
        ).build()
    }

    @Provides
    fun provideActionDao(db: QuickRouteDB): ActionDao {
        return db.actionDao()
    }
}