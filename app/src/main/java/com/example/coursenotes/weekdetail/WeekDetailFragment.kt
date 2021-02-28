package com.example.coursenotes.weekdetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.coursenotes.R
import com.example.coursenotes.coursedetail.CourseDetailFragmentArgs
import com.example.coursenotes.database.AppDatabase
import com.example.coursenotes.databinding.FragmentWeekDetailBinding

class WeekDetailFragment : Fragment() {

    private lateinit var viewModel: WeekDetailViewModel
    private lateinit var binding: FragmentWeekDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_week_detail, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = WeekDetailFragmentArgs.fromBundle(requireArguments())

        val database = AppDatabase.getInstance(application).weekDao

        val viewModelFactory = WeekDetailViewModelFactory(arguments.weekId, database)

        viewModel = ViewModelProvider(this, viewModelFactory).get(WeekDetailViewModel::class.java)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStop() {
        viewModel.updateWeekData(binding.editWeekText.text.toString(), binding.editWeekTitle.text.toString())
        super.onStop()
    }

}