package com.yourssu.realprice.exception

class ProductAlreadyExistsException(product: String, msg: String = "$product already exists") : RuntimeException(msg)
