package com.yourssu.realprice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
class Record(
        @Id @GeneratedValue
        var id: Long? = null,
        val kindname: String,
        val price: String,
        val date: String,
        @ManyToOne val product: Product
)
