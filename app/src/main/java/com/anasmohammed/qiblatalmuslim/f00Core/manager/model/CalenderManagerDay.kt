package com.anasmohammed.qiblatalmuslim.f00Core.manager.model

data class CalenderManagerDay(
    var id: Int = 0,
    var dayNumber: String = "",
    var dayName: String = "",
    var monthNumber: String = "",
    var monthName: String = "",
    var yearNumber: String = "",
    var dayDate: String = "",
    var isSelected: Boolean = false
)

