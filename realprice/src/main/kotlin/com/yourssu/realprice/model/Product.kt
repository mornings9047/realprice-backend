package com.yourssu.realprice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Product(
        @Id @GeneratedValue
        var id: Long? = null,
        val name: String,
        val p_itemcategorycode: String,
        val p_itemcode: String,
        val p_kindcode: String
)
