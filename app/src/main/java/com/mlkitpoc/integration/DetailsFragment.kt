package com.mlkitpoc.integration

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.mlkitpoc.databinding.FragmentDetailsBinding
import com.mlkitpoc.integration.retrofit.ApiService
import com.mlkitpoc.integration.viewmodel.ContentViewModel
import com.mlkitpoc.list.model.Product
import com.mlkitpoc.list.model.Record
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsFragment : Fragment() {

    private val viewModel: ContentViewModel by activityViewModels()
    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.text.observe(viewLifecycleOwner) {
            binding?.contentLabel?.text = it

            fetchProducts(it)
        }
    }

    private fun fetchProducts(searchTerm: String) {
        val call = ApiService.getApiClient().getListOfProducts(searchTerm)
        call?.enqueue(object : Callback<Record?> {
            override fun onResponse(call: Call<Record?>, response: Response<Record?>) {
                if (response.isSuccessful) {
                    Log.d("TAG", "onResponse: ${response.body()?.totalRecords}")
                }
            }

            override fun onFailure(call: Call<Record?>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message}", t)
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}