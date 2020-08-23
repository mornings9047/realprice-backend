package com.yourssu.realprice.service

import com.yourssu.realprice.dto.request.ProductCreateRequestDto
import com.yourssu.realprice.exception.ProductAlreadyExistsException
import com.yourssu.realprice.exception.ProductNotExistsException
import com.yourssu.realprice.model.Product
import com.yourssu.realprice.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {

    fun registerProduct(productCreateRequestDto: ProductCreateRequestDto) {
        val product = Product(
                name = productCreateRequestDto.name,
                p_itemcategorycode = productCreateRequestDto.p_itemcategorycode,
                p_itemcode = productCreateRequestDto.p_itemcode,
                p_kindcode = productCreateRequestDto.p_kindcode
        )
        if (productRepository.existsByName(product.name))
            throw ProductAlreadyExistsException(product.name)
        productRepository.save(product)
    }

    fun findProduct(keyword: String): Product {
        return productRepository.findByName(keyword).orElseThrow { ProductNotExistsException(keyword) }
    }

}
