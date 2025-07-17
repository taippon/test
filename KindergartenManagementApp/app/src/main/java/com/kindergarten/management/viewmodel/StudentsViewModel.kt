package com.kindergarten.management.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.kindergarten.management.data.dao.StudentDao
import com.kindergarten.management.data.dao.ClassDao
import com.kindergarten.management.data.entities.Student
import kotlinx.coroutines.launch

class StudentsViewModel(
    private val studentDao: StudentDao,
    private val classDao: ClassDao
) : ViewModel() {

    private val _searchQuery = MutableLiveData("")
    
    val students: LiveData<List<Student>> = _searchQuery.switchMap { query ->
        if (query.isBlank()) {
            studentDao.getAllActiveStudents().asLiveData()
        } else {
            studentDao.searchStudents(query).asLiveData()
        }
    }

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _searchQuery.value = ""
    }

    fun searchStudents(query: String) {
        _searchQuery.value = query
    }

    fun addStudent(student: Student) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                studentDao.insertStudent(student)
            } catch (e: Exception) {
                _error.value = "Failed to add student: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateStudent(student: Student) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                studentDao.updateStudent(student)
            } catch (e: Exception) {
                _error.value = "Failed to update student: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteStudent(student: Student) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                studentDao.deactivateStudent(student.id)
            } catch (e: Exception) {
                _error.value = "Failed to delete student: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    class StudentsViewModelFactory(
        private val studentDao: StudentDao,
        private val classDao: ClassDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(StudentsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return StudentsViewModel(studentDao, classDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}