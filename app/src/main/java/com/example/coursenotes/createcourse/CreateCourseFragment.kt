package com.example.coursenotes.createcourse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.coursenotes.R
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentCreateCourseBinding
import com.google.android.material.snackbar.Snackbar

class CreateCourseFragment : Fragment() {

    private lateinit var binding: FragmentCreateCourseBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_course, container, false)

        val application = requireNotNull(this.activity).application

        val database = AppDatabase.getInstance(application)
        val courseDataSource = database.courseDao
        val weekDataSource = database.weekDao

        val viewModelFactory = CreateCourseViewModelFactory(courseDataSource, weekDataSource)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(CreateCourseViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.createCourseButton.setOnClickListener {
            viewModel.onCreateCourse(binding.editCourseName.text.toString(),
                                     binding.editNumWeeks.text.toString())
        }

        viewModel.showCreationWarning.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.error_message),
                    Snackbar.LENGTH_SHORT // How long to display the message.
                ).show()
                viewModel.doneShowingWarning()
            }
        })

        viewModel.navigateToCourseList.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(CreateCourseFragmentDirections.actionCreateCourseFragmentToCourseListFragment())
                viewModel.doneNavigatingCourseList()
            }
        })

        return binding.root
    }
}
