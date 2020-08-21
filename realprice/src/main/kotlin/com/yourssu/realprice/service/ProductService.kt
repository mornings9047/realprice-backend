package com.yourssu.realprice.service

import com.yourssu.realprice.model.Product
import com.yourssu.realprice.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {
    fun saveProduct(product: Product): Product {
        return productRepository.save(product)
    }

}
