package com.yourssu.realprice.repository

import com.yourssu.realprice.model.Record
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RecordRepository : JpaRepository<Record, Long> {
    fun findByKindnameContainingAndDate(keyword: String, date: String): Optional<Record>
    fun findTop2ByProductIdOrderByDateDesc(productId: Long): List<Record>
}
