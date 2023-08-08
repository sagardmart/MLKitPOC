package com.mlkitpoc.list


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mlkitpoc.R
import com.mlkitpoc.list.model.Product


class SearchAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<SearchAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val productNameTextView: TextView = itemView.findViewById(R.id.itemname)
        val productPriceTextView: TextView = itemView.findViewById(R.id.mrp)
        val productVariantTextView: TextView = itemView.findViewById(R.id.sku)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.searchcard_f, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        val defaultVariant = product.sKUs.firstOrNull()

        defaultVariant?.let {
            holder.productNameTextView.text = product.name
            holder.productPriceTextView.text = it.priceSALE.toString()
            holder.productVariantTextView.text = it.variantTextValue
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }
}