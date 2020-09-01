package com.yourssu.realprice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Product(
        @Id @GeneratedValue
        var id: Long? = null,
        val name: String,
        val p_itemcategorycode: Int, // 부류코드
        val p_itemcode: Int,         // 품목코드
        val p_kindcode: String,         // 품종코드
        val p_productrankcode: String,  // 등급코드
        val category: Int
)
