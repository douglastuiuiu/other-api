package br.com.douglasog87.facade;

import br.com.douglasog87.event.DomainEvent;
import br.com.douglasog87.event.domain.Product;
import br.com.douglasog87.event.strategy.ProductEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Consumer;

@AllArgsConstructor
@Slf4j
@Service
public class OtherApiFacade {

    private final Map<ProductEvent, Consumer<Product>> operation =
            Map.of(
                    ProductEvent.UPDATE, payload -> createOrUpdate(payload),
                    ProductEvent.DELETE, payload -> delete(payload)
            );

    public void handle(DomainEvent<Product, ProductEvent> event) {
        operation.getOrDefault(
                event.getType(), payload -> log.warn("{} not supported", event.getType())
        ).accept(event.getPayload());
    }

    private void createOrUpdate(Product product) {
        System.out.println("Recebendo evento de inserção ou atualização");
        System.out.println("Produto: " + product.toString());
    }

    private void delete(Product product) {
        System.out.println("Recebendo evento de deleção");
        System.out.println("Produto: " + product.toString());
    }
}
