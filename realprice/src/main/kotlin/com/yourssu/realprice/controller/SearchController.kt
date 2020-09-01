package com.yourssu.realprice.controller

import com.yourssu.realprice.dto.response.CategoryResponseDto
import com.yourssu.realprice.service.product.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/search")
class SearchController @Autowired constructor(val productService: ProductService) {
    @GetMapping("{category}")
    fun getProductsByCategory(@PathVariable category: Int): List<CategoryResponseDto> {
        return productService.findProductsByCategory(category)
    }
}