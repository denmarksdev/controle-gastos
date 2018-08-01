package com.marksdev.financask.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.AdapterView
import com.marksdev.financask.R
import com.marksdev.financask.dao.TransacaoDao
import com.marksdev.financask.model.Tipo
import com.marksdev.financask.model.Transacao
import com.marksdev.financask.ui.ResumoView
import com.marksdev.financask.ui.adapter.ListaTransacoesAdapter
import com.marksdev.financask.ui.dialog.AdicionaTransacaoDialog
import com.marksdev.financask.ui.dialog.AlteraTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity : AppCompatActivity() {

    private val dao = TransacaoDao()
    private val transacoes: List<Transacao> = dao.transacoes

    private val viewDaActivity by lazy {
        window.decorView
    }
    private val viewGroupDaActivity by lazy {
        viewDaActivity as ViewGroup
    }

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
                    chamaDialogDeInclusao(Tipo.RECEITA)
                }

        lista_transacoes_adiciona_despesa
                .setOnClickListener {
                    chamaDialogDeInclusao(Tipo.DEPESA)
                }
    }

    private fun chamaDialogDeInclusao(tipo: Tipo) {
        AdicionaTransacaoDialog(viewGroupDaActivity, this)
                .chama(tipo) { transacaoCriada ->
                    adiciona(transacaoCriada)
                    lista_transacoes_adiciona_menu.close(true)
                }

    }

    private fun adiciona(transacao: Transacao) {
        dao.adiciona(transacao)
        atualizaTransacoes()
    }

    private fun atualizaTransacoes() {
        configuraLista()
        configuraResumo()
    }

    private fun configuraResumo() {
        val resumoView = ResumoView(this, viewDaActivity, transacoes)
        resumoView.atualiza()
    }

    private fun configuraLista() {
        val listaTransacoesAdapter = ListaTransacoesAdapter(transacoes, this@ListaTransacoesActivity)
        with(lista_transacoes_listview) {
            adapter = listaTransacoesAdapter
            setOnItemClickListener { _, _, position, _ ->
                val transacao = transacoes[position]
                chamaDialogDeAlteracao(transacao, position)
            }

            setOnCreateContextMenuListener { menu, _, _ ->
                menu.add(Menu.NONE, 1, Menu.NONE, "Remover")
            }

        }
    }

    private fun chamaDialogDeAlteracao(transacao: Transacao, position: Int) {
        AlteraTransacaoDialog(viewGroupDaActivity, this)
                .chama(transacao) { trasacaoAlterda ->
                    altera(position, trasacaoAlterda)
                    lista_transacoes_adiciona_menu.close(true)
                }
    }

    private fun altera(position: Int, transacao: Transacao) {
        dao.altera(transacao, position)
        atualizaTransacoes()
    }


    override fun onContextItemSelected(item: MenuItem?): Boolean {
        val idDomenu = item?.itemId
        if (idDomenu == 1) {
            val adapterContextMenuInfo = item.menuInfo as AdapterView.AdapterContextMenuInfo
            val posicaoDaTransacao = adapterContextMenuInfo.position
            remove(posicaoDaTransacao)
        }

        return  super.onContextItemSelected(item)
    }

    private fun remove(posicao: Int) {
        dao.remove(posicao)
        atualizaTransacoes()
    }
}





