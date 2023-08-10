package com.mlkitpoc.integration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.indices
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.databinding.FragmentListBinding
import com.mlkitpoc.integration.viewmodel.ContentViewModel
import com.mlkitpoc.list.ListAdapter

class ListFragment : Fragment() {

    private var binding: FragmentListBinding? = null
    private val viewModel: ContentViewModel by activityViewModels()
    private lateinit var itemAdapter: ListAdapter
    private var itemsList = mutableListOf<String>()


    var i:Int = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemAdapter = ListAdapter(itemsList, onItemClick = {
            viewModel.setText(it)
            itemAdapter.notifyDataSetChanged()
        })
        binding?.rvList?.apply {
            layoutManager = LinearLayoutManager(this.context)
            adapter = itemAdapter

        }
        viewModel.itemList.observe(viewLifecycleOwner) {
            itemsList.addAll(it)
            viewModel.setText(it[i])
            itemAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}