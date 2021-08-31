package br.com.zupacademy.marcosOT6.pix.detalhar.dto

class Conta (contaGrpc: br.com.zupacademy.marcosOT6.pix.Conta){
    val titular: String = contaGrpc.titular
    val cpf: String = contaGrpc.cpf
    val nomeDaInstituicao: String = contaGrpc.nomeDaInstituicao
    val agencia: String = contaGrpc.agencia
    val numero: String = contaGrpc.numero
    val tipoDeConta: String = contaGrpc.tipoDeConta.name
}