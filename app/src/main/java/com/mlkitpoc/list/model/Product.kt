package com.mlkitpoc.list.model


data class Product(val name: String, val buyable: Boolean, val manufacturer: String,
                   val numberOfskus: Int, val seo_token_ntk: String, val targetUrl: String,
                   val productId: String, val availabilityType: String, val categoryId: String,
                   val categoryName: String, val isItemShareable: Boolean,
                   val PRODUCT_SKUS_SPLIT:Boolean, val announcementTags: List<String>,
                   val productAnnouncement: List<String>, val sKUs: List<Variant>,
                   val templatetype: String)