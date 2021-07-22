package br.com.zup.edu.vini.keymanager

import br.com.zup.b1gvini.RegistraPixRequest
import br.com.zup.b1gvini.TipoChave
import br.com.zup.b1gvini.TipoConta
import br.com.zup.edu.vini.keymanager.compartilhado.validations.ValidPixKey
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
@ValidPixKey
class NovaChavePixRequest(@field:NotNull val tipoConta: TipoContaRequest,
                          @field:Size(max = 77) val chave: String,
                          @field:NotNull val tipoChave: TipoChaveRequest) {


    fun paraGrpc(clientId: String): RegistraPixRequest{
        return RegistraPixRequest.newBuilder()
            .setClientId(clientId)
            .setTipoChave(tipoChave.atributoGrpc ?: TipoChave.CHAVE_DESCONHECIDA)
            .setValorChave(chave ?: "")
            .setTipoConta(tipoConta.atributoGrpc ?: TipoConta.CONTA_DESCONHECIDA)
            .build()
    }

}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {

    CPF(TipoChave.CPF) {

        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }

            if (!chave.matches("[0-9]+".toRegex())) {
                return false
            }

            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }

    },

    CELULAR(TipoChave.TELEFONE) {
        override fun valida(chave: String?): Boolean {

            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    EMAIL(TipoChave.EMAIL) {

        override fun valida(chave: String?): Boolean {

            if (chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }

        }
    },

    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?) = chave.isNullOrBlank() // não deve se preenchida
    };

    abstract fun valida(chave: String?): Boolean
}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {

    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),

    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
