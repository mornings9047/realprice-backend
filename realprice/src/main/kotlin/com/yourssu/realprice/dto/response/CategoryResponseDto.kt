package com.yourssu.realprice.dto.response

import io.swagger.annotations.ApiModelProperty

class CategoryResponseDto(
        @ApiModelProperty("품목명")
        val name: String,
        @ApiModelProperty("등락여부(가격이 비싸면 1, 같으면 0, 싸면 -1)")
        val riseOrFall: Int
)
