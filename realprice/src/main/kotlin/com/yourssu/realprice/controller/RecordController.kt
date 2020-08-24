package com.yourssu.realprice.controller

import com.yourssu.realprice.dto.response.TodayRecordResponseDto
import com.yourssu.realprice.service.ProductService
import com.yourssu.realprice.service.RecordService
import com.yourssu.realprice.service.function.RegisterRecordFunction
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/record")
class RecordController @Autowired constructor(val productService: ProductService,
                                              val recordService: RecordService) {
    @ApiOperation("기간 사이에 올라온 품목 가격 추가하기")
    @PostMapping("/{keyword}/{startday}/{endday}")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerNewRecordFromStartDayToEndDay(@PathVariable keyword: String, @PathVariable("startday") startDay: String, @PathVariable("endday") endDay: String) {
        recordService.registerRecord(keyword, startDay, endDay)
    }

    @ApiOperation("기간 사이에 올라온 모든 품목 가격 추가하기")
    @PostMapping("/all/{startday}/{endday}")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerAllNewRecordFromStartDayToEndDay(@PathVariable("startday") startDay: String, @PathVariable("endday") endDay: String) {
        recordService.registerAllRecords(startDay, endDay)
    }

    @ApiOperation("선택한 품목의 오늘 정보 가져오기")
    @GetMapping("/today/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun getTodayRecordComparedWithYesterday(@PathVariable keyword: String): TodayRecordResponseDto {
        return recordService.getTodayRecordCompareWithYesterday(keyword)
    }

    @ApiOperation("지난 일주일 가격 가져오기")
    @GetMapping("/week/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun getWeekRecords(@PathVariable keyword: String) {
        recordService.getWeekRecords(keyword)
    }

    @ApiOperation("지난 한 달 가격 가져오기")
    @GetMapping("/month/{keyword}")
    @ResponseStatus(HttpStatus.OK)
    fun getMonthRecords(@PathVariable keyword: String) {
        recordService.getMonthRecords(keyword)
    }

}
