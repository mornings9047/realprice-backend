package com.yourssu.realprice.service.function

import com.yourssu.realprice.model.Product
import com.yourssu.realprice.model.Record
import com.yourssu.realprice.model.URLBuilder
import com.yourssu.realprice.repository.RecordRepository
import com.yourssu.realprice.service.ProductService
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets

@Component
class RegisterRecordFunction @Autowired constructor(val recordRepository: RecordRepository,
                                                    val productService: ProductService) {
    fun getRecordFromApi(keyword: String, date: String): Record? {
        val product = productService.findProduct(keyword)
        val jsonObject = JSONParser().parse(readURL(product, date)) as JSONObject
        if (jsonObject.toJSONString().contains("001")) {
            val prevRecord = recordRepository.findTop1ByProductIdOrderByDateDesc(product.id!!)
            return if (prevRecord.isPresent) {
                val record = prevRecord.get()
                Record(
                        kindname = record.kindname,
                        price = record.price,
                        date = date,
                        product = product
                )
            } else
                null
        }

        val jsonData = jsonObject["data"] as JSONObject
        val jsonItem = jsonData["item"] as JSONArray
        val record = jsonItem[jsonItem.size - 1] as JSONObject
        val kindname = product.name + "(" + record["kindname"].toString().substringAfter("(")
        val price = record["price"].toString()
        return Record(
                kindname = kindname,
                price = price,
                date = date,
                product = product
        )
    }

    fun readURL(product: Product, date: String): String {
        val buffer = StringBuffer()
        val url = URL(URLBuilder(product, date).build())
        BufferedReader(InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).use {
            return buffer.append(it.readLine()).toString()
        }
    }
}
