package br.com.douglasog87.otherapi.conf;

import br.com.douglasog87.products.event.consumer.ConsumerConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.Map;

@Import({ConsumerConfig.class})
@Configuration
public class RabbitConfig {

    @Getter
    @Value("#{productsApiConsumerConfig.getEventTopicName()}")
    private String productsEventTopic;

    @Getter
    @Value("${product.event.config.queue-name:product.event.otherapi}")
    private String productEventQueue;

    @Getter
    @Value("${product.event.config.routing-key:product.update}")
    private String productEventRoutingUpdateKey;

    @Getter
    @Value("${product.event.config.routing-key:product.delete}")
    private String productEventRoutingDeleteKey;

    @Getter
    @Value("${product.event.config.routing-key:product.otherapi.error}")
    private String productEventRoutingErrorKey;

    @Getter
    @Value("${product.event.config.queue-name.error:product.event.otherapi.error}")
    private String productEventErrorQueue;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(MessageConverter contentTypeConverter, SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(contentTypeConverter);
        factory.setPrefetchCount(50);
        return factory;
    }

    @Bean
    Declarables productEvents() {

        Exchange productTopic = ExchangeBuilder.topicExchange(productsEventTopic).durable(true).build();

        Queue productQueue = QueueBuilder
                .durable(productEventQueue)
                .withArguments(
                        Map.of(
                                "x-dead-letter-exchange", productsEventTopic,
                                "x-dead-letter-routing-key" ,productEventRoutingErrorKey
                        )
                ).build();

        Queue productErrorQueue = QueueBuilder
                .durable(productEventErrorQueue)
                .withArguments( Map.of("x-queue-mode", "lazy") )
                .build();

        Binding productUpdateBinding = BindingBuilder
                .bind( productQueue )
                .to( productTopic )
                .with( productEventRoutingUpdateKey )
                .noargs();

        Binding productDeleteBinding = BindingBuilder
                .bind( productQueue )
                .to( productTopic )
                .with( productEventRoutingDeleteKey )
                .noargs();

        Binding productErrorBinding = BindingBuilder
                .bind( productErrorQueue )
                .to( productTopic )
                .with( productEventRoutingErrorKey )
                .noargs();

        return new Declarables(
                productTopic,
                productQueue, productErrorQueue,
                productUpdateBinding, productDeleteBinding, productErrorBinding
        );
    }
}
