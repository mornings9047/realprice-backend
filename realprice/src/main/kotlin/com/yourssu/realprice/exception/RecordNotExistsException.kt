package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class RecordNotExistsException(date: String, msg: String = "Product doesn't exist on $date") : RuntimeException(msg)
