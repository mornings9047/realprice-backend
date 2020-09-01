package com.yourssu.realprice.controller

import com.yourssu.realprice.dto.request.ProductCreateRequestDto
import com.yourssu.realprice.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController @Autowired constructor(val productService: ProductService) {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNewProduct(@RequestBody productCreateRequestDto: ProductCreateRequestDto) {
        productService.registerProduct(productCreateRequestDto)
    }
}
