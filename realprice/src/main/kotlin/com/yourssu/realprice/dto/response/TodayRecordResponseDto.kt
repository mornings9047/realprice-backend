package com.yourssu.realprice.dto.response

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class TodayRecordResponseDto(
        @ApiModelProperty("품목명(단위)")
        val kindname: String,
        @ApiModelProperty("오늘의 가격")
        val price: String,
        @ApiModelProperty("등락여부(가격이 비싸면 1, 같으면 0, 싸면 -1)")
        val riseOrFall: Boolean,
        @ApiModelProperty("가격변동")
        val diff: Int
)
