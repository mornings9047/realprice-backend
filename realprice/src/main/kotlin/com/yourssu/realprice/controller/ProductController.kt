package com.yourssu.realprice.controller

import com.yourssu.realprice.dto.request.ProductCreateRequestDto
import com.yourssu.realprice.model.Product
import com.yourssu.realprice.service.ProductService
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/api/v2")
class ProductController @Autowired constructor(val productService: ProductService) {
    private final val p_cert_key = "7a5a147c-4735-41b4-b530-91137f6c5b51"
    private final val p_cert_id = "1153"
    private final val p_returntype = "json"

    var url = "http://www.kamis.or.kr/service/price/xml.do?action=periodProductList" +
            "&p_productclscode=02" +        // 구분 ( 01:소매, 02:도매, default:02 )
            "&p_startday=" + "2020-08-19" +
            "&p_endday=" + "2020-08-21" +
            "&p_itemcategorycode=" +        // 부류코드 ( 100:식량작물, 200:채소류, 300:특용작물, 400:과일류, 500:축산물, 600:수산물)
            "&p_itemcode=212" +             // 품목코드
            "&p_kindcode=00" +              // 품종코드
            "&p_productrankcode=0" +        // 등급코드
            "4&p_countrycode=1101" +
            "&p_convert_kg_yn=N" +
            "&p_cert_key=" + p_cert_key +
            "&p_cert_id=" + p_cert_id +
            "&p_returntype=" + p_returntype

    @GetMapping("/test")
    fun getData(/*@PathVariable keyword: String*/) {
        val jsonObject = JSONParser().parse(readURL()) as JSONObject
        val json = jsonObject["data"] as JSONObject
        val items = json["item"] as JSONArray
//        val prices = json["price"] as JSONArray

        for (i in 0 until items.size) {
            val entity = items[i] as JSONObject
            val kindname = entity["kindname"].toString()
//            val price = entity["price"].toString()
            println("kindname: $kindname")
        }
    }

    fun readURL(): String {
        val buffer = StringBuilder()
        val url = URL(url)
        BufferedReader(InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).use {
            return buffer.append(it.readLine()).toString()
        }
    }


    @PostMapping("/create")
    fun saveProduct(@RequestBody productCreateRequestDto: ProductCreateRequestDto): Product {
        val product = Product(
                name = productCreateRequestDto.name,
                p_itemcategorycode = productCreateRequestDto.p_itemcategorycode,
                p_itemcode = productCreateRequestDto.p_itemcode,
                p_kindcode = productCreateRequestDto.p_kindcode
        )
        return productService.saveProduct(product)
    }

}
