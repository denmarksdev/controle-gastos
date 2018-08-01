package com.marksdev.financask.ui.dialog

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.marksdev.financask.R
import com.marksdev.financask.extension.converteParaCalendar
import com.marksdev.financask.extension.formataParaBrasileiro
import com.marksdev.financask.model.Tipo
import com.marksdev.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

abstract class FormularioTransacaoDialog(
        private val context: Context,
        private val viewGroup: ViewGroup) {

    val viewCriada = criaLayout()

    protected val campoValor = viewCriada.form_transacao_valor
    protected val campoData = viewCriada.form_transacao_data
    protected val campoCategoria = viewCriada.form_transacao_categoria
    protected abstract val tituloBotaoPositivo : String

    fun chama(tipo: Tipo, delegate: ( transacao:Transacao ) -> Unit ) {
        configuraCampoData()
        configuraCampoCatagoria(tipo)
        configuraFormulario(tipo, delegate)
    }

    private fun configuraFormulario(tipo: Tipo, delegate: (transacao: Transacao) -> Unit) {
        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
                .setTitle(titulo)
                .setView(viewCriada)
                .setPositiveButton(tituloBotaoPositivo)
                { _, _ ->

                    val valorEmTexto = campoValor
                            .text.toString()
                    val dataEmTexto = campoData
                            .text.toString()
                    val categoriaEmTexto = campoCategoria
                            .selectedItem.toString()

                    var valor = converteCampoValor(valorEmTexto)
                    val data = dataEmTexto.converteParaCalendar()

                    val transacaoCriada = Transacao(
                            tipo = tipo,
                            valor = valor,
                            data = data,
                            categoria = categoriaEmTexto)

                    delegate(transacaoCriada)

                }
                .setNegativeButton("Cancelar", null)
                .show()
    }

       protected abstract fun tituloPor(tipo: Tipo): Int



    private fun converteCampoValor(valorEmTexto: String): BigDecimal {

        return try {
            valorEmTexto.toBigDecimal()
        } catch (exception: NumberFormatException) {
            Toast.makeText(
                    context,
                    "Falha na conversao do valor",
                    Toast.LENGTH_LONG).show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCatagoria(tipo: Tipo) {
        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
                .createFromResource(context,
                        categorias,
                        android.R.layout.simple_spinner_dropdown_item)

        campoCategoria.adapter = adapter
    }

    protected fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA) {
           return  R.array.categorias_de_receita
        }
        return  R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {

        val hoje = Calendar.getInstance()

        val ano = hoje.get(Calendar.YEAR)
        val mes = hoje.get(Calendar.MONTH)
        val dia = hoje.get(Calendar.DAY_OF_MONTH)

        campoData.setText(hoje.formataParaBrasileiro())
        campoData
                .setOnClickListener {
                    DatePickerDialog(context,
                            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                                val dataSelecionada = Calendar.getInstance()
                                dataSelecionada.set(year, month, dayOfMonth)
                                campoData.setText(
                                        dataSelecionada.formataParaBrasileiro())

                            }, ano, mes, dia)
                            .show()
                }
    }

    private fun criaLayout(): View {
        return LayoutInflater
                .from(context)
                .inflate(R.layout.form_transacao, viewGroup, false)
    }
}