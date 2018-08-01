package com.marksdev.financask.ui.dialog

import android.content.Context
import android.view.ViewGroup
import com.marksdev.financask.R
import com.marksdev.financask.model.Tipo

class AdicionaTransacaoDialog(
        viewGroup: ViewGroup,
        context: Context)  : FormularioTransacaoDialog(context,viewGroup) {

    override val tituloBotaoPositivo: String
        get() = "Incluir"

    override fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
            return R.string.adiciona_receita
        }
        return R.string.adiciona_despesa
    }

}