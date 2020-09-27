package com.example.rabbitmq.repository;

import com.example.rabbitmq.bean.Produto;
import org.springframework.data.repository.CrudRepository;

public interface ProdutoRepository extends CrudRepository<Produto, String> {
}
