# Cadastro de Produtos API

API REST para criar, consultar, atualizar e excluir produtos. O projeto usa Java 21, Spring Boot 3, Spring Data JPA e PostgreSQL.

## Requisitos

- Java 21
- Maven 3.9 ou superior
- PostgreSQL acessível pela aplicação

## Configuração do banco

Configure estas variáveis no ambiente antes de iniciar a aplicação. Nenhuma credencial é armazenada no repositório.

| Variável | Exemplo |
|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/cadastro_produtos` |
| `SPRING_DATASOURCE_USERNAME` | usuário do PostgreSQL |
| `SPRING_DATASOURCE_PASSWORD` | senha do PostgreSQL |

No PowerShell, por exemplo:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/cadastro_produtos"
$env:SPRING_DATASOURCE_USERNAME = "seu-usuario"
$env:SPRING_DATASOURCE_PASSWORD = "sua-senha"
```

## Executar

```powershell
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. O Hibernate atualiza o esquema do banco automaticamente durante o desenvolvimento.

## Swagger UI (OpenAPI)

Com a aplicação em execução, acesse `http://localhost:8080/swagger-ui/index.html` para explorar e testar os endpoints pelo navegador. A especificação OpenAPI em JSON fica disponível em `http://localhost:8080/v3/api-docs`.

## Testes

```powershell
mvn test
```

## Endpoints

Prefixo: `/api/produtos`

| Método | Rota | Resposta |
|---|---|---|
| `GET` | `/api/produtos` | `200 OK` e lista de produtos |
| `GET` | `/api/produtos/{id}` | `200 OK` ou `404 Not Found` |
| `POST` | `/api/produtos` | `201 Created` e produto criado |
| `PUT` | `/api/produtos/{id}` | `200 OK` ou `404 Not Found` |
| `DELETE` | `/api/produtos/{id}` | `204 No Content` ou `404 Not Found` |

Exemplo de corpo para criação e atualização:

```json
{
  "nome": "Teclado",
  "descricao": "Teclado USB",
  "preco": 149.90,
  "quantidadeEstoque": 10
}
```

O nome é obrigatório e limitado a 100 caracteres; a descrição aceita até 500 caracteres; o preço precisa ser maior que zero; e a quantidade deve ser zero ou maior. Erros de validação retornam `400 Bad Request` com mensagens por campo.

O CORS permite chamadas da origem local `http://localhost:4200`. Para outros ambientes, ajuste a origem permitida em `CorsConfig`.
