package com.kindergarten.management.viewmodel

import androidx.lifecycle.*
import com.kindergarten.management.data.dao.*
import com.kindergarten.management.data.entities.AttendanceStatus
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DashboardViewModel(
    private val studentDao: StudentDao,
    private val teacherDao: TeacherDao,
    private val classDao: ClassDao,
    private val attendanceDao: AttendanceDao
) : ViewModel() {

    private val _totalStudents = MutableLiveData<Int>()
    val totalStudents: LiveData<Int> = _totalStudents

    private val _totalTeachers = MutableLiveData<Int>()
    val totalTeachers: LiveData<Int> = _totalTeachers

    private val _totalClasses = MutableLiveData<Int>()
    val totalClasses: LiveData<Int> = _totalClasses

    private val _presentToday = MutableLiveData<Int>()
    val presentToday: LiveData<Int> = _presentToday

    private val _absentToday = MutableLiveData<Int>()
    val absentToday: LiveData<Int> = _absentToday

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // Load counts
                _totalStudents.value = studentDao.getActiveStudentCount()
                _totalTeachers.value = teacherDao.getActiveTeacherCount()
                _totalClasses.value = classDao.getActiveClassCount()

                // Load today's attendance
                val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                _presentToday.value = attendanceDao.getAttendanceCountByDateAndStatus(today, AttendanceStatus.PRESENT)
                _absentToday.value = attendanceDao.getAttendanceCountByDateAndStatus(today, AttendanceStatus.ABSENT)
            } catch (e: Exception) {
                // Handle error
                _totalStudents.value = 0
                _totalTeachers.value = 0
                _totalClasses.value = 0
                _presentToday.value = 0
                _absentToday.value = 0
            }
        }
    }

    class DashboardViewModelFactory(
        private val studentDao: StudentDao,
        private val teacherDao: TeacherDao,
        private val classDao: ClassDao,
        private val attendanceDao: AttendanceDao
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DashboardViewModel(studentDao, teacherDao, classDao, attendanceDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}