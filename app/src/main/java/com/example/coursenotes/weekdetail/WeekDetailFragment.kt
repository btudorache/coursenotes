package com.example.coursenotes.weekdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.coursenotes.R
import com.example.coursenotes.coursedetail.CourseDetailFragmentArgs
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentWeekDetailBinding

class WeekDetailFragment : Fragment() {

    private lateinit var viewModel: WeekDetailViewModel
    private lateinit var binding: FragmentWeekDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = WeekDetailFragmentArgs.fromBundle(requireArguments())

        val database = AppDatabase.getInstance(application)

        val viewModelFactory = WeekDetailViewModelFactory(arguments.weekId, database.courseDao, database.weekDao)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WeekDetailViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToCourseDetail.observe(viewLifecycleOwner, { weekId ->
            weekId?.let {
                findNavController().navigate(WeekDetailFragmentDirections.actionWeekDetailFragmentToCourseDetailFragment(weekId))
                viewModel.doneNavigatingToCourseDetail()
            }
        })

        return binding.root
    }

    override fun onStop() {
        viewModel.updateWeekData(binding.editWeekText.text.toString(), binding.editWeekTitle.text.toString())
        super.onStop()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_week_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_week ->  {
                viewModel.deleteWeek()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}