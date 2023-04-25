package br.com.mkdata.testmkdata.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Telefone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ddd;
    private String numeroTelefone;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Cliente cliente;
}
