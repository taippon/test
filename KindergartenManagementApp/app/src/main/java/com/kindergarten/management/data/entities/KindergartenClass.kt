package com.kindergarten.management.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Entity(tableName = "classes")
@Parcelize
data class KindergartenClass(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val capacity: Int,
    val teacherId: Long?,
    val roomNumber: String,
    val schedule: String,
    val ageGroup: String,
    val description: String = "",
    val isActive: Boolean = true,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) : Parcelable