package com.marksdev.financask.extension

private val i = 0

fun String.limitaEmAte(caracteres: Int): String {

    if (this.length > caracteres) {
        val primeiroCaracter = 0
        return "${this.substring(primeiroCaracter, caracteres)}..."
    }
    return this
}