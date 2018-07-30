package com.marksdev.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.marksdev.financask.R
import com.marksdev.financask.extension.formataParaBrasileiro
import com.marksdev.financask.extension.limitaEmAte
import com.marksdev.financask.model.Tipo
import com.marksdev.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transacao>,
                             private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(posicao: Int, view: View?, parent: ViewGroup?): View {

        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[posicao]

        lidaComValorEadcionaIcone(transacao, viewCriada)

        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()

        return viewCriada
    }

    private fun lidaComValorEadcionaIcone(transacao: Transacao, viewCriada: View) {

        if (transacao.tipo == Tipo.RECEITA) {
            viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.receita))
            viewCriada.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_receita)
        } else {
            viewCriada.transacao_icone.setBackgroundResource(R.drawable.icone_transacao_item_despesa)
            viewCriada.transacao_valor.setTextColor(ContextCompat.getColor(context, R.color.despesa));
        }

        viewCriada.transacao_valor.text = transacao.valor.formataParaBrasileiro()
    }


    override fun getItem(posicao: Int): Transacao {
        return transacoes[posicao]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}


