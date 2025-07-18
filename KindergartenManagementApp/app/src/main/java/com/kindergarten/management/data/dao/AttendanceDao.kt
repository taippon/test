package com.kindergarten.management.data.dao

import androidx.room.*
import com.kindergarten.management.data.entities.Attendance
import com.kindergarten.management.data.entities.AttendanceStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    
    @Query("SELECT * FROM attendance WHERE date = :date ORDER BY studentId ASC")
    fun getAttendanceByDate(date: String): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceByStudent(studentId: Long): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE classId = :classId AND date = :date")
    fun getAttendanceByClassAndDate(classId: Long, date: String): Flow<List<Attendance>>
    
    @Query("SELECT * FROM attendance WHERE studentId = :studentId AND date = :date LIMIT 1")
    suspend fun getAttendanceByStudentAndDate(studentId: Long, date: String): Attendance?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: Attendance): Long
    
    @Update
    suspend fun updateAttendance(attendance: Attendance)
    
    @Delete
    suspend fun deleteAttendance(attendance: Attendance)
    
    @Query("SELECT COUNT(*) FROM attendance WHERE date = :date AND status = :status")
    suspend fun getAttendanceCountByDateAndStatus(date: String, status: AttendanceStatus): Int
    
    @Query("SELECT COUNT(*) FROM attendance WHERE studentId = :studentId AND status = :status")
    suspend fun getAttendanceCountByStudentAndStatus(studentId: Long, status: AttendanceStatus): Int
    
    @Query("SELECT COUNT(*) FROM attendance WHERE classId = :classId AND date = :date AND status = :status")
    suspend fun getAttendanceCountByClassDateAndStatus(classId: Long, date: String, status: AttendanceStatus): Int
}