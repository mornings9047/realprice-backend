package com.yourssu.realprice.repository

import com.yourssu.realprice.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(keyword: String): Optional<Product>
    fun existsByName(name: String): Boolean
    fun findByCategory(category: Int): List<Product>
}
