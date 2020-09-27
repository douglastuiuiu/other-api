package com.example.rabbitmq.consumer;

import com.example.rabbitmq.bean.Produto;
import org.springframework.stereotype.Service;

@Service
public class ReceiveMessageHandler {

    public void handleMessage(Produto produto) {
        System.out.println("Produto recebido com sucesso:");
        System.out.println(produto.toString());
    }

}
