package com.mlkitpoc.list.model

data class Variant(val variantText: String, val variantTextValue: String, val variantInfoTxtValue: String,
                   val variantType: String, val variantImgValue: String, val homeorpup: String,
                   val cod: String, val groceryType: String, val defaultRank: String, val imageKey: String,
                   val productImageKey: String, val binaryImgCode: String, val imgCode: String,
                   val availabilityType: String, val priceMRP: Double, val priceSALE: Double,
                   val savePrice: Double, val invStatus: Int, val maxQuantity: Int,
                   val skuUniqueID: String, val articleNumber: String, val buyable: Boolean,
                   val defaultVariant: String, val isPriceEditAllowed: String, val bulkQuantity: String,
                   val bulkThreshold: String, val exclusive: String, val minBulkQuantity: String,
                   val giftItem: String, val usp: String, val invType: String, val name: String,
                   val tags: List<String>)

//priceSALE,