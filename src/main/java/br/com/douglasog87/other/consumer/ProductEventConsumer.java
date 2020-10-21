package br.com.douglasog87.other.consumer;

import br.com.douglasog87.consumer.EventConsumer;
import br.com.douglasog87.event.DomainEvent;
import br.com.douglasog87.products.event.domain.Product;
import br.com.douglasog87.products.event.strategy.ProductEvent;
import br.com.douglasog87.other.facade.OtherApiFacade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class ProductEventConsumer implements EventConsumer<Product, ProductEvent> {

    private final OtherApiFacade otherApiFacade;

    @RabbitListener(queues = "#{rabbitConfig.getProductEventQueue()}", concurrency = "5")
    @Override
    public void consume(DomainEvent<Product, ProductEvent> event) {
        otherApiFacade.handle(event);
    }
}
