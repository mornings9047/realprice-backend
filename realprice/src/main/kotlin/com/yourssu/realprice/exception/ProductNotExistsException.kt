package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class ProductNotExistsException(msg: String = "Product doesn't exist in api") : RuntimeException(msg)
