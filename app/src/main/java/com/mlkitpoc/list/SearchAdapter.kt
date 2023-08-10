package com.mlkitpoc.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.list.model.Product



class SearchAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<SearchAdapter.ProductViewHolder>() {


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.itemname)
        val productPrice: TextView = itemView.findViewById(R.id.mrp)
        val productSKU: TextView = itemView.findViewById(R.id.sku)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchcard_f, parent, false)
        return ProductViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        val defaultVariant=product.sKUs[0]


        holder.productName.text=product.name
        holder.productPrice.text=defaultVariant.variantTextValue
        holder.productSKU.text=defaultVariant.priceSALE.toString()

    }



    override fun getItemCount(): Int {

        return productList.size
    }
}