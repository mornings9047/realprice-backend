package com.yourssu.realprice.dto.request

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel
class ProductCreateRequestDto(
        @ApiModelProperty("품목명")
        val name: String,
        @ApiModelProperty("부류코드(100:식량작물, 200:채소류, 300:특용작물, 400:과일류, 500:축산물, 600:수산물)")
        val p_itemcategorycode: Int,
        @ApiModelProperty("품목코드")
        val p_itemcode: Int,
        @ApiModelProperty("품종코드")
        val p_kindcode: String,
        @ApiModelProperty("등급코드")
        val p_productrankcode: String
)
