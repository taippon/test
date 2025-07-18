package com.kindergarten.management.ui.students.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kindergarten.management.data.entities.Student
import com.kindergarten.management.databinding.ItemStudentBinding
import java.text.SimpleDateFormat
import java.util.*

class StudentsAdapter(
    private val onStudentClick: (Student) -> Unit
) : ListAdapter<Student, StudentsAdapter.StudentViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = getItem(position)
        holder.bind(student)
    }

    inner class StudentViewHolder(
        private val binding: ItemStudentBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onStudentClick(getItem(position))
                }
            }
        }

        fun bind(student: Student) {
            binding.apply {
                textStudentName.text = student.name
                textAge.text = "${student.age} years"
                textParentName.text = "Parent: ${student.parentName}"
                
                // Format enrollment date
                val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val enrollmentDate = try {
                    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val date = inputFormat.parse(student.enrollmentDate)
                    date?.let { dateFormat.format(it) } ?: student.enrollmentDate
                } catch (e: Exception) {
                    student.enrollmentDate
                }
                textEnrollmentDate.text = "Enrolled: $enrollmentDate"
                
                // Show class info if assigned
                if (student.classId != null) {
                    textClassInfo.text = "Class: Assigned"
                    // TODO: Load actual class name from database
                } else {
                    textClassInfo.text = "Class: Not assigned"
                }
                
                // Load student photo if available
                // TODO: Implement image loading with Glide
                // if (student.photoPath.isNotEmpty()) {
                //     Glide.with(imageStudent.context)
                //         .load(student.photoPath)
                //         .placeholder(R.drawable.ic_student_placeholder)
                //         .into(imageStudent)
                // }
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean {
                return oldItem == newItem
            }
        }
    }
}