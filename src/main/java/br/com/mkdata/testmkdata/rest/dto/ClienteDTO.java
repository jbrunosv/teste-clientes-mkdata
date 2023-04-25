package br.com.mkdata.testmkdata.rest.dto;

import br.com.mkdata.testmkdata.model.enums.TipoCliente;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ClienteDTO implements Serializable {

    private Long id;
    private String nome;
    private LocalDateTime dataCadastro;
    private Boolean ativo;
    private TipoCliente tipoCliente;
    private String cpf;
    private String cnpj;
    private String registroGeral;
    private String inscricaoEstadual;
    private List<TelefoneDto> telefones;
}
