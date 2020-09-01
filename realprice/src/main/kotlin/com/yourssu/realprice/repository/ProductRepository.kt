package com.yourssu.realprice.repository

import com.yourssu.realprice.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
    fun findByName(keyword: String): Optional<Product>
    fun findByCategory(category: Int): List<Product>
    fun findAllByName(keyword: String): Optional<Product>

    @Query("select p.name from Product p where p.p_itemcategorycode = ?1")
    fun findAllByPItemCategoryCode(p_itemcategorycode: Int): List<String>
}
