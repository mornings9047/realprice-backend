package com.yourssu.realprice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class RequestURL(
        @Id @GeneratedValue
        var id: Long? = null,
        val p_productclscode: String = "02",
        var p_startday: String,
        var p_endday: String,
        var p_itemcategorycode: String,
        var p_itemcode: String,
        var p_kindcode: String,
        val p_productrankcode: String = "05",
        val p_countrycode: String = "1101",
        val p_convert_kg_yn: String = "N"
) {
    fun update(product: Product, date: String) {
        this.p_startday = date
        this.p_endday = date
        this.p_itemcategorycode = product.p_itemcategorycode
        this.p_itemcode = product.p_itemcode
        this.p_kindcode = product.p_kindcode
    }

    fun generateURL(): String {
        return "&p_productclscode='$p_productclscode'" +
                "&p_startday='$p_startday'" +
                "&p_endday='$p_endday'" +
                "&p_itemcategorycode='$p_itemcategorycode'" +
                "&p_itemcode='$p_itemcode'" +
                "&p_kindcode='$p_kindcode'" +
                "&p_productrankcode='$p_productrankcode'" +
                "&p_countrycode='$p_countrycode'" +
                "&p_convert_kg_yn='$p_convert_kg_yn'"
    }

}
