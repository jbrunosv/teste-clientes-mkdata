package br.com.mkdata.testmkdata.model.entity;

import br.com.mkdata.testmkdata.model.enums.TipoCliente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nonnull
    private String nome;
    @Temporal(value = TemporalType.TIMESTAMP)
    private LocalDateTime dataCadastro;
    private Boolean ativo;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoCliente tipoCliente;
    @CPF
    private String cpf;
    @CNPJ
    private String cnpj;
    private String registroGeral;
    private String inscricaoEstadual;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "cliente")
    private List<Telefone> telefones;

    @PrePersist
    private void prePersist(){
        boolean isCnpj = false;
        boolean isCpf = false;

        if (!(this.cnpj == null || this.cnpj.isBlank()))
            isCnpj = true;
        if (!(this.cpf == null || this.cpf.isBlank()))
            isCpf = true;

        if(isCnpj && isCpf)
            throw new IllegalArgumentException("Por favor, informe o CPF ou CNPJ e não os dois.");

        if(isCnpj) {
            this.tipoCliente = TipoCliente.PESSOA_JURIDICA;
            this.registroGeral = null;
        }
        if (isCpf) {
            this.tipoCliente = TipoCliente.PESSOA_FISICA;
            this.inscricaoEstadual = null;
        }

        this.dataCadastro = LocalDateTime.now();

    }
}
