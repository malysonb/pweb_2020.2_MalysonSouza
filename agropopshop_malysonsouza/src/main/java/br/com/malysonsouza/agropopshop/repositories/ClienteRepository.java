package br.com.malysonsouza.agropopshop.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.malysonsouza.agropopshop.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	
    @Query("Select c from Cliente c where MONTH(data_cadastro) = MONTH(:data)")
    List<Cliente> findClientesByMonth(LocalDate data);

}
