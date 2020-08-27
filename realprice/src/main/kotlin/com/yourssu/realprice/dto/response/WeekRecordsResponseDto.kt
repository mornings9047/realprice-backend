package com.yourssu.realprice.dto.response

import com.yourssu.realprice.model.Record
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class WeekRecordsResponseDto(record: Record) {
    @ApiModelProperty("해당 날짜에 대한 품목의 가격")
    val price = record.price
    @ApiModelProperty("품목 정보를 가져올 날짜")
    val date = record.date
}
