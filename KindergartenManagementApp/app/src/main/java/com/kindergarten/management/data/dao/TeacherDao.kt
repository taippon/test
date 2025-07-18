package com.kindergarten.management.data.dao

import androidx.room.*
import com.kindergarten.management.data.entities.Teacher
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherDao {
    
    @Query("SELECT * FROM teachers WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveTeachers(): Flow<List<Teacher>>
    
    @Query("SELECT * FROM teachers WHERE id = :id")
    suspend fun getTeacherById(id: Long): Teacher?
    
    @Query("SELECT * FROM teachers WHERE name LIKE '%' || :query || '%' AND isActive = 1")
    fun searchTeachers(query: String): Flow<List<Teacher>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacher(teacher: Teacher): Long
    
    @Update
    suspend fun updateTeacher(teacher: Teacher)
    
    @Delete
    suspend fun deleteTeacher(teacher: Teacher)
    
    @Query("UPDATE teachers SET isActive = 0 WHERE id = :id")
    suspend fun deactivateTeacher(id: Long)
    
    @Query("SELECT COUNT(*) FROM teachers WHERE isActive = 1")
    suspend fun getActiveTeacherCount(): Int
}