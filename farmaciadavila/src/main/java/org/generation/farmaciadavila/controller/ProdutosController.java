package org.generation.farmaciadavila.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.farmaciadavila.model.Produtos;
import org.generation.farmaciadavila.repository.ProdutosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;



@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;

	@GetMapping
	private ResponseEntity<List<Produtos>> getAll() {
		return ResponseEntity.ok(produtosRepository.findAll());
	}

	@GetMapping("/{id}")
	private ResponseEntity<Produtos> getById(@PathVariable Long id) {
		return produtosRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/nome/{nome}")
	private ResponseEntity<List<Produtos>> getByNome(@PathVariable String nome) {
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(nome));
	}

	@GetMapping("/nomes")
	public List<String> getNome() {
		return produtosRepository.findNomes();
	}

	@PostMapping
	private ResponseEntity<Produtos> post(@Valid @RequestBody Produtos produtos) {
		return ResponseEntity.status(HttpStatus.CREATED).body(produtosRepository.save(produtos));
	}

	@PutMapping
	private ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos) {
		return produtosRepository.findById(produtos.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produtos)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	private void delete(@PathVariable Long id) {
		Optional<Produtos> produtos = produtosRepository.findById(id);
				
				if(produtos.isEmpty())
					throw new ResponseStatusException(HttpStatus.NOT_FOUND);
				produtosRepository.deleteById(id);
	}

}
