package com.kindergarten.management.ui.students

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kindergarten.management.R
import com.kindergarten.management.databinding.FragmentStudentsBinding
import com.kindergarten.management.viewmodel.StudentsViewModel
import com.kindergarten.management.KindergartenApplication
import com.kindergarten.management.ui.students.adapter.StudentsAdapter

class StudentsFragment : Fragment() {

    private var _binding: FragmentStudentsBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: StudentsViewModel
    private lateinit var studentsAdapter: StudentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val application = requireActivity().application as KindergartenApplication
        val database = application.database
        
        viewModel = ViewModelProvider(
            this,
            StudentsViewModel.StudentsViewModelFactory(database.studentDao(), database.classDao())
        )[StudentsViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupSearchListener()
        setupFab()
    }

    private fun setupRecyclerView() {
        studentsAdapter = StudentsAdapter { student ->
            // Handle student item click - navigate to student details
            // TODO: Implement navigation to student details
        }
        
        binding.recyclerStudents.apply {
            adapter = studentsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.students.observe(viewLifecycleOwner) { students ->
            studentsAdapter.submitList(students)
            binding.emptyState.visibility = if (students.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchListener() {
        binding.editSearch.addTextChangedListener { text ->
            viewModel.searchStudents(text?.toString() ?: "")
        }
    }
    
    private fun setupFab() {
        binding.fabAddStudent.setOnClickListener {
            // TODO: Navigate to add student screen
            // For now, show a placeholder message
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}