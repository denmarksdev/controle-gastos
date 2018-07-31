package com.marksdev.financask.delegate

import com.marksdev.financask.model.Transacao

interface TransacaoDelegate {

    fun  delegate(transacao: Transacao)

}