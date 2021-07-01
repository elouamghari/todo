package com.elouamghari.todo.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elouamghari.todo.application.MainApplication
import com.elouamghari.todo.database.doas.ClassificationDao
import com.elouamghari.todo.database.doas.TodoDao
import com.elouamghari.todo.database.entities.Classification
import com.elouamghari.todo.database.entities.Todo

@Database(
    entities = [
        Classification::class,
        Todo::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    companion object{

        private const val DATABASE_NAME = "todo.db"
        @Volatile
        private var db : AppDatabase? = null

        fun getAppDatabase(): AppDatabase{
            return db ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    MainApplication.instance.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME).build()
                db = instance
                instance
            }
        }
    }

    abstract fun getClassificationDao(): ClassificationDao
    abstract fun getTodoDao(): TodoDao

}