package br.com.douglasog87;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import br.com.douglasog87.model.Product;
import br.com.douglasog87.repository.ProductRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
class ApplicationTests {

	@Autowired
    private ProductRepository repository;
    
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void createShouldPersistData() {
		Product product = getProduct();
		Product saved = repository.save(product);

		Optional<Product> findOne = repository.findById(product.getId());

		Assertions.assertThat(saved.getId()).isEqualTo(product.getId());
		Assertions.assertThat(saved.getName()).isEqualTo(product.getName());
		Assertions.assertThat(saved.getKind()).isEqualTo(product.getKind());
		Assertions.assertThat(saved.getManufacturer()).isEqualTo(product.getManufacturer());
	}

	private Product getProduct() {
		Product product = new Product();
		product.setId("999999");
		product.setName("TESTE");
		product.setKind("TESTE");
		product.setManufacturer("TESTE");
		return product;
	}

}
