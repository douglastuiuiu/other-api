package com.example.rabbitmq;

import com.example.rabbitmq.bean.Produto;
import com.example.rabbitmq.repository.ProdutoRepository;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
class RabbitmqApplicationTests {

	@Autowired
	private ProdutoRepository repository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createShouldPersistData() {
		Produto produto = getProduto();
		Produto saved = repository.save(produto);

		Optional<Produto> findOne = repository.findById(produto.getCodigo());

		Assertions.assertThat(saved.getCodigo()).isEqualTo(produto.getCodigo());
		Assertions.assertThat(saved.getNome()).isEqualTo(produto.getNome());
		Assertions.assertThat(saved.getCategoria()).isEqualTo(produto.getCategoria());
		Assertions.assertThat(saved.getFabricante()).isEqualTo(produto.getFabricante());

	}

	private Produto getProduto() {
		Produto produto = new Produto();
		produto.setCodigo("999999");
		produto.setNome("TESTE");
		produto.setCategoria("TESTE");
		produto.setFabricante("TESTE");
		return produto;
	}

}
