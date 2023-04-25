package br.com.mkdata.testmkdata.rest.controller;

import br.com.mkdata.testmkdata.model.entity.Cliente;
import br.com.mkdata.testmkdata.rest.dto.ClienteDTO;
import br.com.mkdata.testmkdata.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("testemkdata/api-v1/cliente")
@CrossOrigin(origins = "${endereco.cors}")
@Slf4j
public class ClienteController {

    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDTO salvarNovo(@RequestBody ClienteDTO dto) {
        return clienteService.salvarNovo(dto);
    }

    @GetMapping("filter")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> filtroDeClientes(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina,
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "isAtivo", defaultValue = "") String isAtivo){
        Pageable pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return clienteService.filtrarCliente(nome, isAtivo, pageRequest);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO atualizarCliente(@PathVariable("id") Long id, @RequestBody ClienteDTO dto) {
        return clienteService.update(id, dto);
    }

    @GetMapping("all")
    @ResponseStatus(HttpStatus.OK)
    public Page<Cliente> buscarTodos(
            @RequestParam(value = "page", defaultValue = "0") Integer pagina,
            @RequestParam(value = "size", defaultValue = "10") Integer tamanhoPagina) {

        PageRequest pageRequest = PageRequest.of(pagina, tamanhoPagina);
        return clienteService.buscarTodos(pageRequest);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClienteDTO buscarPorId(@PathVariable("id") Long id) {
        return clienteService.buscarPorId(id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable("id") Long idCliente) {
        clienteService.deletarPorId(idCliente);
    }
}
