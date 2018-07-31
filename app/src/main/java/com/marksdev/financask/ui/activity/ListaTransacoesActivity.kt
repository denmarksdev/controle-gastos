package com.marksdev.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.marksdev.financask.R
import com.marksdev.financask.delegate.TransacaoDelegate
import com.marksdev.financask.model.Tipo
import com.marksdev.financask.model.Transacao
import com.marksdev.financask.ui.ResumoView
import com.marksdev.financask.ui.adapter.ListaTransacoesAdapter
import com.marksdev.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity(), TransacaoDelegate {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()
        configuraLista()
        configuraFab()
    }

    private fun configuraFab() {
        lista_transacoes_adiciona_receita
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.RECEITA)
                }

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeAdicao(Tipo.DEPESA)
                }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        AdicionaTransacaoDialog(window.decorView as ViewGroup, this)
                .configuraDialog(tipo, this)
    }

    private fun atualizaTransacoes(transacaoCriada: Transacao) {
        transacoes.add(transacaoCriada)
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val view = window.decorView
        val resumoView = ResumoView(this, view, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }

    override fun delegate(transacao: Transacao) {
        atualizaTransacoes(transacao)
        lista_transacoes_adiciona_menu.close(true)
    }

}
