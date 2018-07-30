package com.marksdev.financask.extension

private val i = 0

fun String.limitaEmAte(carecteres: Int): String {

    if (this.length > carecteres) {
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, carecteres)}..."
    }
    return this
}