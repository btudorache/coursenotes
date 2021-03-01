package com.example.coursenotes.coursedetail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coursenotes.R
import com.example.coursenotes.createcourse.CreateCourseViewModel
import com.example.coursenotes.createcourse.CreateCourseViewModelFactory
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentCourseDetailBinding

class CourseDetailFragment : Fragment() {
    private lateinit var viewModel: CourseDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        val binding: FragmentCourseDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_course_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = CourseDetailFragmentArgs.fromBundle(requireArguments())

        val database = AppDatabase.getInstance(application)

        val viewModelFactory = CourseDetailViewModelFactory(arguments.courseId, database.courseDao, database.weekDao)

        viewModel = ViewModelProvider(this, viewModelFactory).get(CourseDetailViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        val adapter = WeekAdapter(WeekListener { weekId ->
            viewModel.navigateToWeekDetail(weekId)
        })
        binding.weekList.adapter = adapter

        viewModel.courseAndWeeks.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it.weeks)
            }
        })

        viewModel.navigateToWeekDetail.observe(viewLifecycleOwner, Observer { weekId ->
            weekId?.let {
                findNavController().navigate(CourseDetailFragmentDirections.actionCourseDetailFragmentToWeekDetailFragment(weekId))
                viewModel.doneNavigatingToWeekDetail()
            }
        })

        viewModel.navigateToCourseList.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                findNavController().navigate(CourseDetailFragmentDirections.actionCourseDetailFragmentToCourseListFragment())
                viewModel.doneNavigatingToCourseList()
            }
        })

        val manager = LinearLayoutManager(activity)
        binding.weekList.layoutManager = manager

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_course_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_course -> {
                viewModel.deleteCourse()
                true
            }
            R.id.create_course -> {
                viewModel.createCourse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}