package com.kindergarten.management.ui.teachers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kindergarten.management.databinding.FragmentTeachersBinding
import com.kindergarten.management.viewmodel.TeachersViewModel
import com.kindergarten.management.KindergartenApplication
import com.kindergarten.management.ui.teachers.adapter.TeachersAdapter

class TeachersFragment : Fragment() {

    private var _binding: FragmentTeachersBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: TeachersViewModel
    private lateinit var teachersAdapter: TeachersAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeachersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val application = requireActivity().application as KindergartenApplication
        val database = application.database
        
        viewModel = ViewModelProvider(
            this,
            TeachersViewModel.TeachersViewModelFactory(database.teacherDao())
        )[TeachersViewModel::class.java]
        
        setupRecyclerView()
        setupObservers()
        setupSearchListener()
        setupFab()
    }

    private fun setupRecyclerView() {
        teachersAdapter = TeachersAdapter { teacher ->
            // Handle teacher item click - navigate to teacher details
            // TODO: Implement navigation to teacher details
        }
        
        binding.recyclerTeachers.apply {
            adapter = teachersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.teachers.observe(viewLifecycleOwner) { teachers ->
            teachersAdapter.submitList(teachers)
            binding.emptyState.visibility = if (teachers.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupSearchListener() {
        binding.editSearch.addTextChangedListener { text ->
            viewModel.searchTeachers(text?.toString() ?: "")
        }
    }
    
    private fun setupFab() {
        binding.fabAddTeacher.setOnClickListener {
            // TODO: Navigate to add teacher screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}