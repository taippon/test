package com.kindergarten.management.data.dao

import androidx.room.*
import com.kindergarten.management.data.entities.Student
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    
    @Query("SELECT * FROM students WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveStudents(): Flow<List<Student>>
    
    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Long): Student?
    
    @Query("SELECT * FROM students WHERE classId = :classId AND isActive = 1")
    fun getStudentsByClass(classId: Long): Flow<List<Student>>
    
    @Query("SELECT * FROM students WHERE name LIKE '%' || :query || '%' AND isActive = 1")
    fun searchStudents(query: String): Flow<List<Student>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student): Long
    
    @Update
    suspend fun updateStudent(student: Student)
    
    @Delete
    suspend fun deleteStudent(student: Student)
    
    @Query("UPDATE students SET isActive = 0 WHERE id = :id")
    suspend fun deactivateStudent(id: Long)
    
    @Query("SELECT COUNT(*) FROM students WHERE isActive = 1")
    suspend fun getActiveStudentCount(): Int
    
    @Query("SELECT COUNT(*) FROM students WHERE classId = :classId AND isActive = 1")
    suspend fun getStudentCountByClass(classId: Long): Int
}