package com.kindergarten.management.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "attendance")
@Parcelize
data class Attendance(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val studentId: Long,
    val classId: Long,
    val date: String,
    val status: AttendanceStatus,
    val notes: String = "",
    val markedBy: String = "",
    val createdAt: Long = System.currentTimeMillis()
) : Parcelable

enum class AttendanceStatus {
    PRESENT,
    ABSENT,
    LATE
}