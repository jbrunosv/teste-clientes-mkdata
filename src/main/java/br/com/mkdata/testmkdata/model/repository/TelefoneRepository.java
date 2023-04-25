package br.com.mkdata.testmkdata.model.repository;

import br.com.mkdata.testmkdata.model.entity.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {

    void deleteAllByClienteId(Long id);
}
