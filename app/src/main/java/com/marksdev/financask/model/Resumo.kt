package com.marksdev.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    val receita get() = somaPor(Tipo.RECEITA)
    val despesa get() = somaPor(Tipo.DEPESA)
    val total get() = receita.subtract(despesa)

    private fun somaPor(tipoReceita:Tipo) :BigDecimal {
        val somaDespesa = transacoes
                .filter { it.tipo == tipoReceita }
                .sumByDouble { it.valor.toDouble() }

        return  BigDecimal(somaDespesa)
    }
}