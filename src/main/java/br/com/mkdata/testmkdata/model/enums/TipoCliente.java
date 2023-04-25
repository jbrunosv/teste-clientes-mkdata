package br.com.mkdata.testmkdata.model.enums;

public enum TipoCliente {

    PESSOA_FISICA ("Pessoa fisica"),
    PESSOA_JURIDICA ("Pessoa juridica");

    private final String tipo;
    TipoCliente(String tipoCliente) {
        this.tipo = tipoCliente;
    }

    public String getTipo(){
        return this.tipo;
    }
}
