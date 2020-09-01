package com.yourssu.realprice.service.product

import com.yourssu.realprice.dto.response.CategoryResponseDto
import com.yourssu.realprice.exception.ProductNotExistsException
import com.yourssu.realprice.repository.ProductRepository
import com.yourssu.realprice.service.record.RecordService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository,
                                            val recordService: RecordService) {
    fun findProductsByCategory(category: Int): List<CategoryResponseDto> {
        return productRepository.findByCategory(category).map {
            val records = recordService.findRecentRecords(it.id!!)
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

    fun findProductsByKeyword(keyword: String): List<String> {
        val product = productRepository.findAllByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
        return productRepository.findAllByPItemCategoryCode(product.p_itemcategorycode)
    }
}
