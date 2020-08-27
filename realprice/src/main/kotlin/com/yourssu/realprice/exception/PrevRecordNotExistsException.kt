package com.yourssu.realprice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.NOT_FOUND)
class PrevRecordNotExistsException(keyword: String, date: String, msg: String = "$keyword's prevRecord doesn't exist on $date in api") : RuntimeException(msg)
