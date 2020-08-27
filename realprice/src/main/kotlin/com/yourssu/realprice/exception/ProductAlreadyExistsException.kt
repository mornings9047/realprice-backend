package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class ProductAlreadyExistsException(product: String, msg: String = "$product already exists") : RuntimeException(msg)
