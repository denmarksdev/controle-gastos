package com.marksdev.financask.ui

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.View
import com.marksdev.financask.R
import com.marksdev.financask.extension.formataParaBrasileiro
import com.marksdev.financask.model.Resumo
import com.marksdev.financask.model.Transacao
import kotlinx.android.synthetic.main.resumo_card.view.*
import java.math.BigDecimal

class ResumoView(private val context:Context,
                 private val view: View,
                 transacoes: List<Transacao>)  {

    private val resumo:Resumo = Resumo(transacoes)
    private val corDespesa = ContextCompat.getColor(context, R.color.despesa)
    private val corReceita = ContextCompat.getColor(context, R.color.receita)

    fun adicionaReceita() {
        val totalReceita =  resumo.receita()
        with (view.resumo_card_receita){
            setTextColor(corReceita)
            text = totalReceita.formataParaBrasileiro()
        }
    }

    fun adicionaDespesa() {
        val totalDespesa = resumo.despesa()
        with(view.resumo_card_despesa){
            resumo_card_despesa.setTextColor(corDespesa)
            resumo_card_despesa.text = totalDespesa.formataParaBrasileiro()
        }
    }

    fun adicionaTotal(){
        val total = resumo.total()
        val corTotal = corPor(total)

        with(view.resumo_card_total) {
            setTextColor(corTotal)
            text = total.formataParaBrasileiro()
        }
    }

    private fun corPor(valor: BigDecimal) =
            if (valor.compareTo(BigDecimal.ZERO) >= 0)
                corReceita else corDespesa

}