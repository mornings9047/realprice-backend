package com.yourssu.realprice.repository

import com.yourssu.realprice.model.Record
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record, Long>
