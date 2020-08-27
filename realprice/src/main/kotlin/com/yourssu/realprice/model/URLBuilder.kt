package com.yourssu.realprice.model

import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

class URLBuilder(
        product: Product,
        date: String
) {
    val p_productclscode = "02"
    var p_startday = date
    var p_endday = date
    var p_itemcategorycode = product.p_itemcategorycode
    var p_itemcode = product.p_itemcode
    var p_kindcode = product.p_kindcode
    val p_productrankcode = "05"
    val p_countrycode = "1101"
    val p_convert_kg_yn = "N"
    private val p_cert_key = "7a5a147c-4735-41b4-b530-91137f6c5b51"
    private val p_cert_id = "1153"
    private val p_returntype = "json"
    private val log: Logger = LogManager.getLogger()

    fun build(): String {
        val url = StringBuffer("http://www.kamis.or.kr/service/price/xml.do?action=periodProductList")
        url.append("&p_productclscode=$p_productclscode")
        url.append("&p_startday=$p_startday")
        url.append("&p_endday=$p_endday")
        url.append("&p_itemcategorycode=$p_itemcategorycode")
        url.append("&p_itemcode=$p_itemcode")
        url.append("&p_kindcode=$p_kindcode")
        url.append("&p_productrankcode=$p_productrankcode")
        url.append("&p_countrycode=$p_countrycode")
        url.append("&p_convert_kg_yn=$p_convert_kg_yn")
        url.append("&p_cert_key=$p_cert_key")
        url.append("&p_cert_id=$p_cert_id")
        url.append("&p_returntype=$p_returntype")
        log.info(url.toString())
        return url.toString()
    }
}
