# Bootcamp: Desafio Quality
Abra a pasta na sua IDE favorita para ver o código da aplicação e executa-la.

## Dependências
- Java 11
- Gradle 7
- Lombok

## Testes
Os testes unitários e de integração estão na pasta src/test e podem ser rodados pela IDE ou pelo terminal com o gradle.

Disponibilizamos uma coleção do postman para facilitar testes manuais:
- https://www.getpostman.com/collections/82b337a08165d4cbe31f

Também é possível acessar a documentação do Swagger no seguinte link quando a aplicação estiver em execução:
- http://localhost:8080/swagger-ui/

Como usamos o banco de dados h2, existe um script que roda quando a aplicação é iniciada, para criar alguns dados para teste. \
O script fica no arquivo: src/main/java/com/bootcampqualitychallenge/PopulateDatabase.java \
Caso queira adicionar algum bairro, é só modificar o script acima, mas os que já existem são suficientes para diferentes testes.

## Resumo dos endpoints
- POST http://localhost:8080/properties
```json
{
    "name": "Minha Casa",
    "neighborhood": "Vila Olimpia",
    "rooms": [
        {
            "name": "Quarto",
            "width": 5,
            "length": 3
        },
        {
            "name": "Cozinha",
            "width": 2,
            "length": 4
        }
    ]
}
```
