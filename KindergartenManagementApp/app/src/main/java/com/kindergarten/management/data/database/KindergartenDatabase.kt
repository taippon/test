package com.kindergarten.management.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.kindergarten.management.data.dao.*
import com.kindergarten.management.data.entities.*
import com.kindergarten.management.utils.Converters

@Database(
    entities = [
        Student::class,
        Teacher::class,
        KindergartenClass::class,
        Attendance::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class KindergartenDatabase : RoomDatabase() {
    
    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
    abstract fun classDao(): ClassDao
    abstract fun attendanceDao(): AttendanceDao
    
    companion object {
        @Volatile
        private var INSTANCE: KindergartenDatabase? = null
        
        fun getDatabase(context: Context): KindergartenDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KindergartenDatabase::class.java,
                    "kindergarten_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}