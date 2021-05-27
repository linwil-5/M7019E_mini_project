package com.example.test

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.test.adapter.TempClickListener
import com.example.test.adapter.TempListAdapter
import com.example.test.database.ServerDatabase
import com.example.test.database.ServerDatabaseDao
import com.example.test.databinding.FragmentHomeScreenBinding
import com.example.test.viewmodel.HomeScreenViewModel
import com.example.test.viewmodel.HomeScreenViewModelFactory


class HomeScreenFragment : Fragment() {

    private val TAG = "HomeScreenFragment"

    private lateinit var viewModelHomeScreen: HomeScreenViewModel
    private lateinit var viewModelHomeScreenFactory: HomeScreenViewModelFactory
    private lateinit var serverDatabaseDao: ServerDatabaseDao


    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Binding this fragment to its corresponding xml layout file
        _binding = FragmentHomeScreenBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application
        serverDatabaseDao = ServerDatabase.getInstance(application).serverDatabaseDao

        // Setting up the viewmodel homescreen to this fragment
        viewModelHomeScreenFactory = HomeScreenViewModelFactory(serverDatabaseDao, application)
        viewModelHomeScreen =
            ViewModelProvider(this, viewModelHomeScreenFactory).get(HomeScreenViewModel::class.java)

        val tempListAdapter = TempListAdapter(TempClickListener { tempDetail ->
            viewModelHomeScreen.onTempListItemClicked(tempDetail)
        })

        binding.homescreenTempRv.adapter = tempListAdapter

        viewModelHomeScreen.navigateToTempDetail.observe(viewLifecycleOwner, { temp ->
            temp?.let {
                this.findNavController().navigate(
                    HomeScreenFragmentDirections.actionHomescreenFragmentToTempHistoryFragment(temp)
                )
                viewModelHomeScreen.onTempDetailNavigated()

            }
        })

        viewModelHomeScreen.tempList.observe(viewLifecycleOwner, { tempList ->
            tempList?.let {
                tempListAdapter.submitList(tempList)
            }
        })

        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     *  Creates/inflates the menu bar
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeToServerlistscreenB.setOnClickListener {
            this.findNavController()
                .navigate(HomeScreenFragmentDirections.actionHomescreenFragmentToServerListFragment())
        }

        binding.homescreenB.setOnClickListener {
            viewModelHomeScreen.getTemperaturesServers()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}