package com.example.coursenotes.courselist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coursenotes.R
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentCourseListBinding

class CourseListFragment : Fragment() {

    private lateinit var courseListViewModel: CourseListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Get reference to the binding object and inflate fragment
        val binding: FragmentCourseListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_list, container, false)

        val application = requireNotNull(this.activity).application

        // Create ViewModel Factory
        val dataSource = AppDatabase.getInstance(application)

        val viewModelFactory = CourseListViewModelFactory(dataSource.courseDao)

        // Get a reference to the ViewModel associated with this fragment
        courseListViewModel = ViewModelProvider(this, viewModelFactory).get(CourseListViewModel::class.java)

        binding.viewmodel = courseListViewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is necessary so that the binding can observe LiveData updates.
        binding.lifecycleOwner = this

        val adapter = CourseAdapter(
            CourseListener { id ->
                courseListViewModel.navigateToCourseDetail(id)
            }
        )
        binding.courseList.adapter = adapter

        // submit the list using the ListAdapter
        courseListViewModel.courses.observe(
            viewLifecycleOwner,
            {
                it?.let {
                    adapter.submitList(it)
                }
            }
        )

        courseListViewModel.navigateToCreateCourseView.observe(
            viewLifecycleOwner,
            {
                if (it == true) {
                    findNavController().navigate(CourseListFragmentDirections.actionCourseListFragmentToCreateCourseFragment())
                    courseListViewModel.doneNavigatingToCreateCourse()
                }
            }
        )

        courseListViewModel.navigateToCourseDetail.observe(
            viewLifecycleOwner,
            { id ->
                id?.let {
                    findNavController().navigate(CourseListFragmentDirections.actionCourseListFragmentToCourseDetailFragment(id))
                    courseListViewModel.doneNavigatingToCourseDetail()
                }
            }
        )

        val manager = GridLayoutManager(activity, 2)
        binding.courseList.layoutManager = manager

        return binding.root
    }
}
