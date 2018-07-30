package com.marksdev.financask.extension

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataParaBrasileiro(): String{
    val formatoBrasileiro = "dd/mm/yyyy"
    val format = SimpleDateFormat(formatoBrasileiro)
    return format.format(this.time)
}