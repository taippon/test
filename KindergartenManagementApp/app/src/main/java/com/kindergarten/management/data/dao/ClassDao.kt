package com.kindergarten.management.data.dao

import androidx.room.*
import com.kindergarten.management.data.entities.KindergartenClass
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    
    @Query("SELECT * FROM classes WHERE isActive = 1 ORDER BY name ASC")
    fun getAllActiveClasses(): Flow<List<KindergartenClass>>
    
    @Query("SELECT * FROM classes WHERE id = :id")
    suspend fun getClassById(id: Long): KindergartenClass?
    
    @Query("SELECT * FROM classes WHERE teacherId = :teacherId AND isActive = 1")
    fun getClassesByTeacher(teacherId: Long): Flow<List<KindergartenClass>>
    
    @Query("SELECT * FROM classes WHERE name LIKE '%' || :query || '%' AND isActive = 1")
    fun searchClasses(query: String): Flow<List<KindergartenClass>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(kindergartenClass: KindergartenClass): Long
    
    @Update
    suspend fun updateClass(kindergartenClass: KindergartenClass)
    
    @Delete
    suspend fun deleteClass(kindergartenClass: KindergartenClass)
    
    @Query("UPDATE classes SET isActive = 0 WHERE id = :id")
    suspend fun deactivateClass(id: Long)
    
    @Query("SELECT COUNT(*) FROM classes WHERE isActive = 1")
    suspend fun getActiveClassCount(): Int
}