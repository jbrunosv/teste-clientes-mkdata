package br.com.mkdata.testmkdata.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TelefoneDto implements Serializable {

    private Long id;
    private String ddd;
    private String numeroTelefone;
    private Long clienteId;
}
