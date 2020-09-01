package com.yourssu.realprice.service.product

import com.yourssu.realprice.dto.request.ProductCreateRequestDto
import com.yourssu.realprice.dto.response.CategoryResponseDto
import com.yourssu.realprice.exception.ProductAlreadyExistsException
import com.yourssu.realprice.exception.ProductNotExistsException
import com.yourssu.realprice.model.Product
import com.yourssu.realprice.repository.ProductRepository
import com.yourssu.realprice.service.record.RecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository,
                                            val recordService: RecordService) {
    fun registerProduct(productCreateRequestDto: ProductCreateRequestDto) {
        val product = Product(
                name = productCreateRequestDto.name,
                p_itemcategorycode = productCreateRequestDto.p_itemcategorycode,
                p_itemcode = productCreateRequestDto.p_itemcode,
                p_kindcode = productCreateRequestDto.p_kindcode,
                p_productrankcode = productCreateRequestDto.p_productrankcode,
                category = when {
                    productCreateRequestDto.p_itemcode <= 300 -> 1
                    productCreateRequestDto.p_itemcode == 400 -> 2
                    else -> 3
                }
        )
        if (productRepository.existsByName(product.name))
            throw ProductAlreadyExistsException(product.name)
        productRepository.save(product)
    }

    fun findProduct(keyword: String): Product {
        return productRepository.findByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
    }

    fun findProductsByCategory(category: Int): List<CategoryResponseDto> {
        return productRepository.findByCategory(category).map {
            val records = recordService.findRecentRecords(it.name)
            val todayPrice = records[0].price.replace(",", "").toInt()
            val yesterdayPrice = records[1].price.replace(",", "").toInt()
            CategoryResponseDto(
                    name = it.name,
                    riseOrFall = when {
                        todayPrice > yesterdayPrice -> 1
                        todayPrice == yesterdayPrice -> 0
                        else -> -1
                    }
            )
        }
    }
}
