package br.com.mkdata.testmkdata.service.impl;

import br.com.mkdata.testmkdata.model.entity.Cliente;
import br.com.mkdata.testmkdata.model.entity.Telefone;
import br.com.mkdata.testmkdata.model.repository.ClienteRepository;
import br.com.mkdata.testmkdata.model.repository.TelefoneRepository;
import br.com.mkdata.testmkdata.rest.dto.ClienteDTO;
import br.com.mkdata.testmkdata.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository repository;
    private TelefoneRepository telefoneRepository;
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public ClienteServiceImpl(ClienteRepository repository, TelefoneRepository telefoneRepository) {
        this.repository = repository;
        this.telefoneRepository = telefoneRepository;
    }

    @Override
    public Page<Cliente> buscarTodos(PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Override
    public ClienteDTO buscarPorId(Long id){
        Cliente cliente = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Não foi encontrado nenum CLiente com o ID informado."));
        return modelMapper.map(cliente, ClienteDTO.class);
    }

    @Override
    @Transactional
    public ClienteDTO salvarNovo(ClienteDTO dto) {
        if(verificaSeJaExistePorNome(dto.getNome())){
            throw new IllegalArgumentException("Já existe um CLIENTE para o nome informado.");
        }

        if(dto.getCnpj().length() < 2){
            dto.setCnpj(null);
            dto.setInscricaoEstadual(null);
        }
        if(dto.getCpf().length() < 2){
            dto.setCpf(null);
            dto.setRegistroGeral(null);
        }

        Cliente clienteConvertido = modelMapper.map(dto, Cliente.class);
        List<Telefone> telefones = clienteConvertido.getTelefones();
        clienteConvertido.setTelefones(null);

        Cliente clienteSalvo = repository.save(clienteConvertido);

        telefones.forEach( t -> t.setCliente(clienteSalvo));

        List<Telefone> telefonesSalvos = telefoneRepository.saveAll(telefones);
        clienteSalvo.setTelefones(telefonesSalvos);

        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }

    @Transactional
    public void deletarPorId(Long id){
        telefoneRepository.deleteAllByClienteId(id);
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        if(verificarSeNomeEstaEmUso(dto.getNome(), id)){
            throw new IllegalArgumentException("Já existe um Cliente com o nome informado.");
        }

        if(dto.getCnpj().length() < 2){
            dto.setCnpj(null);
            dto.setInscricaoEstadual(null);
        }
        if(dto.getCpf().length() < 2){
            dto.setCpf(null);
            dto.setRegistroGeral(null);
        }
        Cliente cliente = modelMapper.map(dto, Cliente.class);

        cliente.setId(id);
        Cliente clienteSalvo = repository.save(cliente);
        return  modelMapper.map(clienteSalvo, ClienteDTO.class);
    }

    @Override
    public Page<Cliente> filtrarCliente(String nome, String isAtivo, Pageable pageable) {

        Cliente cliente = setNullValues();

        if (nome.length() == 0) {
            cliente.setNome(null);
        }else {
            cliente.setNome(nome);
        }
        if (isAtivo.equals("undefined")) {
            cliente.setAtivo(null);
        }
        if(isAtivo.equals("true")){
            cliente.setAtivo(true);
        }
        if(isAtivo.equals("false")){
            cliente.setAtivo(false);
        }

        var matcher = ExampleMatcher.matching()
                .withMatcher("nome", ExampleMatcher.GenericPropertyMatcher::contains)
                .withIgnoreCase();

        Example<Cliente> example = Example.of(cliente, matcher);
        return repository.findAll(example, pageable);
    }

    private boolean verificaSeJaExistePorNome(String nome){
        List<Cliente> clientes = repository.findAllByNome(nome);
        if(!clientes.isEmpty()){
            return true;
        }
        return false;
    }

    private boolean verificarSeNomeEstaEmUso(String nome, Long id){
        List<Cliente> clientes = repository.findAllByNome(nome);
        if(clientes.isEmpty()){
            return false;
        }
        if(clientes.size() == 1 ){
            if(clientes.get(0).getId().equals(id)){
                return false;
            }
            return true;
        }
        return true;
    }

    private Cliente setNullValues(){
        Cliente cliente = new Cliente();
        cliente.setTipoCliente(null);
        cliente.setTelefones(null);
        return cliente;
    }
}
