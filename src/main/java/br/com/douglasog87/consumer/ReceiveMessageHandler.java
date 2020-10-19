package br.com.douglasog87.consumer;

import br.com.douglasog87.model.Product;
import br.com.douglasog87.repository.ProductRepository;

import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageHandler {

    private ProductRepository repository;

    public ReceiveMessageHandler(ProductRepository repository) {
        this.repository = repository;
    }

    public void handleMessage(Product produto) {
        System.out.println("Produto recebido com sucesso:");
        System.out.println(produto.toString());
        repository.save(produto);
        System.out.println("Salvo no banco.");
    }

}
