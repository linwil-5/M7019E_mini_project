package com.example.test

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.test.adapter.DowndetectorClickListener
import com.example.test.adapter.DowndetectorListAdapter
import com.example.test.databinding.FragmentServerListBinding
import com.example.test.network.DataFetchStatus
import com.example.test.viewmodel.ServerListViewModel
import com.example.test.viewmodel.ServerListViewModelFactory

class ServerListFragment : Fragment() {

    private val TAG = "ServerListFragment"

    private lateinit var viewModelServerList: ServerListViewModel
    private lateinit var viewModelServerListFactory: ServerListViewModelFactory

    private var _binding: FragmentServerListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentServerListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val application = requireNotNull(this.activity).application

        viewModelServerListFactory = ServerListViewModelFactory(application)
        viewModelServerList =
            ViewModelProvider(this, viewModelServerListFactory).get(ServerListViewModel::class.java)

        val serverListAdapter = DowndetectorListAdapter(DowndetectorClickListener { server ->
            viewModelServerList.onServerListItemClicked(server)
        })

        binding.serverListRv.adapter = serverListAdapter

        viewModelServerList.serverList.observe(viewLifecycleOwner, { serverList ->
            serverList?.let {
                serverListAdapter.submitList(serverList)
            }
        })


        viewModelServerList.myResponse.observe(viewLifecycleOwner, { response ->
            response?.let {
                if (response.isSuccessful) {
                    Log.d(TAG, "onCreateView:" + response.body().toString())
                    Log.d(TAG, "onCreateView:" + response.code().toString())
                    Log.d(TAG, "onCreateView:" + response.message().toString())
                } else {
                    Toast.makeText(application, response.code(), Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModelServerList.dataFetchStatus.observe(viewLifecycleOwner, { status ->
            status?.let {
                when (status) {
                    DataFetchStatus.ERROR -> {
                        binding.statusIv.visibility = View.VISIBLE
                        binding.statusIv.setImageResource(R.drawable.ic_connection_error)
                    }
                    DataFetchStatus.DONE -> {
                        binding.statusIv.visibility = View.GONE
                    }
                    else -> binding.statusIv.visibility = View.VISIBLE
                }
            }
        })


        viewModelServerList.getServersDownDetectionList()

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelServerList.getServersDownDetectionList()
    }
}