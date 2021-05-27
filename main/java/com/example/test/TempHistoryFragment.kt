package com.example.test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.test.databinding.FragmentTempHistoryBinding
import com.example.test.model.Temp
import com.example.test.network.DataFetchStatus
import com.example.test.viewmodel.TempHistoryViewModel
import com.example.test.viewmodel.TempHistoryViewModelFactory
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import java.text.Format
import java.text.SimpleDateFormat
import com.jjoe64.graphview.DefaultLabelFormatter as DefaultLabelFormatter

class TempHistoryFragment : Fragment() {

    private lateinit var viewModelTempHistory: TempHistoryViewModel
    private lateinit var viewModelTempHistoryFactory: TempHistoryViewModelFactory

    private lateinit var temp: Temp

    private var _binding: FragmentTempHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTempHistoryBinding.inflate(inflater)
        val application = requireNotNull(this.activity).application

        temp = TempHistoryFragmentArgs.fromBundle(requireArguments()).temp


        viewModelTempHistoryFactory = TempHistoryViewModelFactory(application)
        viewModelTempHistory =
            ViewModelProvider(
                this,
                viewModelTempHistoryFactory
            ).get(TempHistoryViewModel::class.java)

        viewModelTempHistory.tempHistoryList.observe(viewLifecycleOwner, { tempListHistory ->
            tempListHistory?.let {
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val series1 = LineGraphSeries<DataPoint>()
                for (temp in tempListHistory.reversed()) {
                    series1.appendData(
                        DataPoint(format.parse(temp.time), temp.temp.toDouble()),
                        true,
                        144
                    )
                }
                val graph = binding.graph
                graph.addSeries(series1)
                graph.viewport.isXAxisBoundsManual = true
                graph.viewport.isScalable = true
                graph.viewport.isScrollable = true
                graph.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
                    override fun formatLabel(value: Double, isValueX: Boolean): String? {
                        if (isValueX) {
                            val formatter: Format = SimpleDateFormat("HH:mm")
                            return formatter.format(value)
                        }
                        return super.formatLabel(value, isValueX)
                    }
                }
                graph.gridLabelRenderer.numHorizontalLabels = 6
            }
        })

        viewModelTempHistory.getTempHistory(temp.name)

        viewModelTempHistory.dataFetchStatus.observe(viewLifecycleOwner, { status ->
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


        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}