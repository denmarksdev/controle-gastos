package com.marksdev.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ArrayAdapter
import com.marksdev.financask.R
import com.marksdev.financask.model.Tipo
import com.marksdev.financask.model.Transacao
import com.marksdev.financask.ui.adapter.ListaTransacoesAdapter
import kotlinx.android.synthetic.main.activity_lista_transacoes.*
import java.math.BigDecimal


class ListaTransacoesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes);

        val transacoes : List<Transacao> = transacoesDeExemplo()
        configuraLista(transacoes)

    }

    private fun configuraLista(transacoes: List<Transacao>) {

        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this);
    }

    private fun transacoesDeExemplo(): List<Transacao> {
        val transacoes = listOf(
                Transacao(BigDecimal(20.5), "Despesa", tipo = Tipo.DEPESA),
                Transacao(BigDecimal(100), "Economia", Tipo.RECEITA),
                Transacao(BigDecimal(100), "Almoço de final de semana", Tipo.DEPESA),
                Transacao(BigDecimal(200), "Prêmio", Tipo.RECEITA)
        );
        return transacoes
    }

}