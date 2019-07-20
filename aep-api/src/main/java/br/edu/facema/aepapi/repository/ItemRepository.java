package br.edu.facema.aepapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.edu.facema.aepapi.model.Item;

public interface ItemRepository extends CrudRepository<Item, Long>{

	@Query("select i from Item i where lower(i.nome) like  %?1%")
	public List<Item> findByNome(String nome);
	
	@Query("select i from Item i where lower(i.nome) like  %?1% and lower(i.vistoPorUltimo) like %?2%")
	public List<Item> findByNomeEVisto(String nome, String visto);
	
	@Query("select i from Item i where i.codigo = ?1")
	public Optional<Item> findByCodigo(long codigo);
	
	@Query("select i from Item i where i.nome like %?1% and i.status = ?2")
	public List<Item> findByStatus(String nome,boolean status);
	
}
