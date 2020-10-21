#OTHER API

A idéia era simular a integração de uma API com outra porém sem a necessidade de informar para a API que detém a informação original, quais as outras APIs que querem receber as notificações e dados originários da API de origem.

Como implementação técnica, utilizei spring-boot para APIs e RabbitMQ para implementar o padrão de event-sourcing.

Requisito 1, o RabbitMQ deve estar instalado e em execução com o usuário e senha guest.
Requisito 2, https://github.com/douglastuiuiu/event-model baixar e rodar mvn clean install
Requisito 3, https://github.com/douglastuiuiu/desafio-produtos-api baixar e rodar mvn clean install

Para inicializar a api, executar na raiz do projeto o comando: mvn clean spring-boot:run

Após a inicialização do serviço, a aplicação poderá receber a chamada do  healthcheck:
- GET: http://localhost:8080/api/v1/health

De igual forma, com a aplicação rodando, esta poderá consumir mensagens da fila, originadas da API de produtos
