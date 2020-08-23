package com.yourssu.realprice.controller

import com.yourssu.realprice.dto.request.ProductCreateRequestDto
import com.yourssu.realprice.model.Product
import com.yourssu.realprice.model.URLBuilder
import com.yourssu.realprice.service.ProductService
import io.swagger.annotations.ApiOperation
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(val productService: ProductService) {

    @ApiOperation("품목 정보 저장하기")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNewProduct(@RequestBody productCreateRequestDto: ProductCreateRequestDto) {
        productService.registerProduct(productCreateRequestDto)
    }

}
