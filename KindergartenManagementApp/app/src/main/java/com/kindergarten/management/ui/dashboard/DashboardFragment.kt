package com.kindergarten.management.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kindergarten.management.R
import com.kindergarten.management.databinding.FragmentDashboardBinding
import com.kindergarten.management.viewmodel.DashboardViewModel
import com.kindergarten.management.KindergartenApplication

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val application = requireActivity().application as KindergartenApplication
        val database = application.database
        
        viewModel = ViewModelProvider(
            this,
            DashboardViewModel.DashboardViewModelFactory(database.studentDao(), database.teacherDao(), database.classDao(), database.attendanceDao())
        )[DashboardViewModel::class.java]
        
        setupObservers()
        loadDashboardData()
    }

    private fun setupObservers() {
        viewModel.totalStudents.observe(viewLifecycleOwner) { count ->
            binding.textTotalStudents.text = count.toString()
        }
        
        viewModel.totalTeachers.observe(viewLifecycleOwner) { count ->
            binding.textTotalTeachers.text = count.toString()
        }
        
        viewModel.totalClasses.observe(viewLifecycleOwner) { count ->
            binding.textTotalClasses.text = count.toString()
        }
        
        viewModel.presentToday.observe(viewLifecycleOwner) { count ->
            binding.textPresentToday.text = count.toString()
        }
        
        viewModel.absentToday.observe(viewLifecycleOwner) { count ->
            binding.textAbsentToday.text = count.toString()
        }
    }
    
    private fun loadDashboardData() {
        viewModel.loadDashboardData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}