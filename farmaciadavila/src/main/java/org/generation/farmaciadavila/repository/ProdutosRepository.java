package org.generation.farmaciadavila.repository;

import java.util.List;

import org.generation.farmaciadavila.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
	
	public List <Produtos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
	
	@Query("SELECT e.nome FROM Produtos e")
    public List<String> findNomes();

}
