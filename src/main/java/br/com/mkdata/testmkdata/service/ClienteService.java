package br.com.mkdata.testmkdata.service;

import br.com.mkdata.testmkdata.model.entity.Cliente;
import br.com.mkdata.testmkdata.rest.dto.ClienteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {

    Page<Cliente> buscarTodos(PageRequest pageRequest);

    ClienteDTO buscarPorId(Long id);

    ClienteDTO salvarNovo(ClienteDTO dto);

    void deletarPorId(Long idCliente);

    ClienteDTO update(Long id, ClienteDTO dto);

    Page<Cliente> filtrarCliente(String nome, String isAtivo, Pageable pageRequest);
}
