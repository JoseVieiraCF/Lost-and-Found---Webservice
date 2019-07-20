package br.edu.facema.aepapi.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.facema.aepapi.model.Item;
import br.edu.facema.aepapi.repository.ItemRepository;

@RestController
@RequestMapping("/itens")
public class ItemController {

	@Autowired
	private ItemRepository ir;
	
	
	@GetMapping()
	public @ResponseBody Iterable<Item> listaItens() {
		Iterable<Item> lista = ir.findAll();
		return lista;
	}
	
	@GetMapping("status/{nome}/{status}")
	public List<Item> devolvidos(@PathVariable String nome,@PathVariable boolean status){
		List<Item> itens = ir.findByStatus(nome,status);
		return itens;
	}
	
	
	@GetMapping("/{nome}")
	public List<Item> buscarPorNome(@PathVariable String nome) {
		List<Item> listaItens = ir.findByNome(nome);
		return listaItens;
	}
	
	@GetMapping("/{nome}/{visto}")
	public List<Item> buscarPorNomeEVisto(@PathVariable String nome, @PathVariable String visto) {
		List<Item> listaItens = ir.findByNomeEVisto(nome, visto);
		return listaItens;
	}
	
	@GetMapping("cod/{codigo}")
	public Optional<Item> buscarPorcod(@PathVariable Long codigo){
		Optional<Item> item = ir.findByCodigo(codigo);
		return item;
	}
	

	
	@PostMapping
	public Item addItem(@RequestBody @Valid Item item) {
		return ir.save(item);
	}
	
	@DeleteMapping
	public Item deleteItem(@RequestBody Item item) {
		ir.delete(item);
		return item;
	}
	
	@DeleteMapping("/{id}")
	public Optional<Item> deleteItem(@PathVariable Long id){
		Optional<Item> item = ir.findById(id);
		ir.deleteById(id);
		
		return item;
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Item> update(@PathVariable Long id,
            @RequestBody Item item) {
		return ir.findById(id)
				.map(record -> {
					record.setImageUrl(item.getImageUrl());
					record.setNome(item.getNome());
					record.setDescricao(item.getDescricao());
					record.setStatus(item.isStatus());
					record.setVistoPorUltimo(item.getVistoPorUltimo());
					Item updated = ir.save(record);
					return ResponseEntity.ok().body(updated);
				}).orElse(ResponseEntity.notFound().build());
	}}
