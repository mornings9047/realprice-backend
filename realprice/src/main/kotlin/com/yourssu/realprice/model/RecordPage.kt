package com.yourssu.realprice.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class RecordPage(page: Int) : PageRequest(page, 2, Sort.by("date").ascending())
