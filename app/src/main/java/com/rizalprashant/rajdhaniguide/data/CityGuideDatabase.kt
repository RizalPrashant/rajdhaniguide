package com.rizalprashant.rajdhaniguide.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizalprashant.rajdhaniguide.model.Categories
import com.rizalprashant.rajdhaniguide.model.Place
import com.rizalprashant.rajdhaniguide.model.User


@Database(entities = [User::class, Categories::class, Place::class], version = 2, exportSchema = false)
abstract class CityGuideDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun categoriesDao(): CategoriesDao

    abstract fun placeDao(): PlaceDao

    companion object {

        @Volatile
        private var INSTANCE: CityGuideDatabase? = null

        fun getDatabase(context: Context) : CityGuideDatabase {
            val tempInstance = INSTANCE

            if(tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityGuideDatabase::class.java,
                    "city_guide_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}