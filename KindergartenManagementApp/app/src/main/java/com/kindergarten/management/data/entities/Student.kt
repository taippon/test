package com.kindergarten.management.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "students")
@Parcelize
data class Student(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val age: Int,
    val birthDate: String,
    val parentName: String,
    val parentPhone: String,
    val address: String,
    val emergencyContact: String,
    val medicalInfo: String = "",
    val classId: Long? = null,
    val photoPath: String = "",
    val enrollmentDate: String,
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable