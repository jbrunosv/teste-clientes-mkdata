package br.com.mkdata.testmkdata.model.repository;

import br.com.mkdata.testmkdata.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("select c from Cliente c")
    Page<Cliente> findAll(PageRequest pageRequest);

    List<Cliente> findAllByNome(@Param("nome") String nome);
}
