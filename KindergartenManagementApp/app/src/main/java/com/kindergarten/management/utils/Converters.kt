package com.kindergarten.management.utils

import androidx.room.TypeConverter
import com.kindergarten.management.data.entities.AttendanceStatus

class Converters {
    
    @TypeConverter
    fun fromAttendanceStatus(status: AttendanceStatus): String {
        return status.name
    }
    
    @TypeConverter
    fun toAttendanceStatus(status: String): AttendanceStatus {
        return AttendanceStatus.valueOf(status)
    }
}