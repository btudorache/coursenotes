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
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentCourseDetailBinding
import com.google.android.material.snackbar.Snackbar

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

        viewModel.showWeekCreatedSnackbar.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(requireActivity().findViewById(android.R.id.content),
                              getString(R.string.new_week_message),
                              Snackbar.LENGTH_SHORT).show()
                viewModel.doneShowingWeekCreatedSnackbar()
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
                DeleteCourseDialogFragment(viewModel).show(parentFragmentManager, "delete_dialog")
                true
            }
            R.id.create_week -> {
                viewModel.createWeek()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}