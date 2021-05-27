package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.test.adapter.HelpListAdapter
import com.example.test.databinding.FragmentHelpBinding
import com.example.test.viewmodel.HelpViewModel
import com.example.test.viewmodel.HelpViewModelFactory


class HelpFragment : Fragment() {

    private val TAG = "HelpFragment"

    private lateinit var viewModelHelp: HelpViewModel
    private lateinit var viewModelHelpFactory: HelpViewModelFactory
    private var _binding: FragmentHelpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHelpBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        viewModelHelpFactory = HelpViewModelFactory(application)
        viewModelHelp = ViewModelProvider(this, viewModelHelpFactory).get(HelpViewModel::class.java)

        val helpListAdapter = HelpListAdapter()

        binding.helpListRv.adapter = helpListAdapter

        PagerSnapHelper().attachToRecyclerView(binding.helpListRv)

        viewModelHelp.results.observe(viewLifecycleOwner, { helpList ->
            helpList?.let {
                Log.d(TAG, "onCreateView: here is the help list" + helpList)
                helpListAdapter.submitList(helpList)
            }
        })

        viewModelHelp.getHelpList()

        return binding.root
    }
}