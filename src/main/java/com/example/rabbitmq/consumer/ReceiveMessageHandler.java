package com.example.rabbitmq.consumer;

import com.example.rabbitmq.bean.Produto;
import com.example.rabbitmq.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageHandler {

    private final ProdutoRepository repository;

    public ReceiveMessageHandler(ProdutoRepository repository) {
        this.repository = repository;
    }

    public void handleMessage(Produto produto) {
        System.out.println("Produto recebido com sucesso:");
        System.out.println(produto.toString());
        repository.save(produto);
        System.out.println("Salvo no banco.");
    }

}
