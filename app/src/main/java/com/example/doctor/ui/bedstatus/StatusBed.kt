package com.example.doctor.ui.bedstatus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.doctor.R
import com.example.doctor.data.model.BedModel
import com.example.doctor.data.model.BedResponse
import com.example.doctor.ui.adapter.BedAdapter
import kotlinx.android.synthetic.main.fragment_status_bed.*
import java.util.ArrayList


class StatusBed : Fragment() {


    lateinit var bedAdapter: BedAdapter
    lateinit var gridLayoutManager: RecyclerView.LayoutManager
    lateinit var bedsData: ArrayList<BedModel>

    lateinit var statusBedViewModel: StatusBedViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status_bed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()

             bed_progress.visibility=View.VISIBLE


        swipe.setOnRefreshListener {

            bedAdapter.clear()
            statusBedViewModel.getBeds()
        }


        statusBedViewModel =
            ViewModelProviders.of(requireActivity()).get(StatusBedViewModel::class.java)
        statusBedViewModel.getBeds()

        statusBedViewModel.beds.observe(viewLifecycleOwner, Observer {
            addBedsData(it)
        })

        statusBedViewModel.message.observe(viewLifecycleOwner, Observer {
            print("dola error message " + it)
        })


    }

    fun setupRecycler() {

        bedsData = ArrayList()
        gridLayoutManager = GridLayoutManager(context, 3)
        bedAdapter = BedAdapter(requireContext(), bedsData)
        bed_list.adapter = bedAdapter
        bed_list.layoutManager = gridLayoutManager
    }

    fun addBedsData(beds: BedResponse) {


        println("dola many times")
        bedsData.clear()

        bedsData.add(BedModel(beds.channel?.field1, beds.feeds?.get(0)?.field1?.toInt()))
        bedsData.add(BedModel(beds.channel?.field2, beds.feeds?.get(0)?.field2?.toInt()))
        bedsData.add(BedModel(beds.channel?.field3, beds.feeds?.get(0)?.field3?.toInt()))
        bedsData.add(BedModel(beds.channel?.field4, beds.feeds?.get(0)?.field4?.toInt()))
        bedsData.add(BedModel(beds.channel?.field5, beds.feeds?.get(0)?.field5?.toInt()))
        bedsData.add(BedModel(beds.channel?.field6, beds.feeds?.get(0)?.field6?.toInt()))

        bedAdapter.notifyDataSetChanged()

        swipe.isRefreshing=false
        bed_progress.visibility=View.GONE
    }

    override fun onResume() {
        super.onResume()
        bedAdapter.clear()
        bed_progress.visibility=View.VISIBLE
        statusBedViewModel.getBeds()
        bedAdapter.notifyDataSetChanged()
    }


}