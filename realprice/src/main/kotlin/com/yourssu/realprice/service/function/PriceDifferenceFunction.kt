package com.yourssu.realprice.service.function

import com.yourssu.realprice.model.Record
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class PriceDifferenceFunction {
    fun getDiff(today: Record, yesterday: Record): Int {
        val todayPrice = today.price.replace(",", "").toInt()
        val yesterdayPrice = yesterday.price.replace(",", "").toInt()
        return abs(todayPrice - yesterdayPrice)
    }
}
