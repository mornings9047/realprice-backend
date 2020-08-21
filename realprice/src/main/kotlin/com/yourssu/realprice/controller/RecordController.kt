package com.yourssu.realprice.controller

import com.yourssu.realprice.service.ProductService
import com.yourssu.realprice.service.RecordService
import com.yourssu.realprice.service.function.RegisterRecordFunction
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/record")
class RecordController @Autowired constructor(val productService: ProductService,
                                              val registerRecordFunction: RegisterRecordFunction,
                                              val recordService: RecordService) {

    @PostMapping("/{keyword}/{date}")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNewRecord(@PathVariable keyword: String, @PathVariable date: String) {
        recordService.registerRecord(keyword, date)
    }

    @GetMapping("/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun getData(@PathVariable keyword: String) {

    }

}
