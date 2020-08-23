package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotExistsException(product: String, msg: String = "$product doesn't exist in api") : RuntimeException(msg)
