# Cadastro de Produtos API

API REST do sistema de cadastro de produtos. Ela recebe as requisições do frontend, valida os dados, aplica as operações de negócio e persiste os produtos no PostgreSQL.

## Objetivo

Disponibilizar operações para cadastrar, listar, consultar, atualizar e excluir produtos, com validação de entrada e respostas HTTP consistentes.

## Stack

| Tecnologia | Uso |
|---|---|
| Java 21 | Linguagem da aplicação |
| Spring Boot 3.4.5 | Inicialização e configuração da API |
| Spring Web | Endpoints REST |
| Spring Data JPA / Hibernate | Persistência e mapeamento objeto-relacional |
| Bean Validation | Validação dos dados recebidos |
| PostgreSQL | Banco de dados relacional |
| Springdoc OpenAPI | Documentação interativa Swagger |
| Maven | Dependências, build e execução |
| JUnit / Mockito | Testes unitários |

## Requisitos

- Java 21
- Maven 3.9 ou superior instalado e disponível no `PATH`
- Docker Desktop com Docker Compose, ou PostgreSQL instalado localmente

## Iniciar o PostgreSQL com Docker

Se ainda não existir um container para o projeto, crie e inicie o banco de desenvolvimento com:

```powershell
docker run --name cadastro-produtos-postgres -e POSTGRES_DB=cadastro_produtos -e POSTGRES_USER=cadastro_produtos -e POSTGRES_PASSWORD=senha-local-dev -p 127.0.0.1:5432:5432 -v cadastro-produtos-postgres-data:/var/lib/postgresql/data -d postgres:17
```

Se o container já foi criado antes, inicie-o novamente com:

```powershell
docker start cadastro-produtos-postgres
```

O volume `cadastro-produtos-postgres-data` mantém os dados ao parar o container. `senha-local-dev` é apenas um exemplo para desenvolvimento local: se você já criou o container com outra senha, use a senha original. Não use essa senha de exemplo em produção.

## Configurar e executar a API

Na pasta `C:\Projetos\cadastro-produtos-api`, defina as variáveis e inicie a aplicação no mesmo terminal PowerShell:

```powershell
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/cadastro_produtos"
$env:SPRING_DATASOURCE_USERNAME = "cadastro_produtos"
$env:SPRING_DATASOURCE_PASSWORD = "senha-local-dev"
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`. Mantenha o terminal aberto enquanto estiver usando a aplicação.

### Executar pelo IntelliJ IDEA

1. Abra o projeto Maven da API no IntelliJ.
2. Inicie o container do PostgreSQL usando as instruções acima.
3. Abra **Run → Edit Configurations…** e selecione a configuração Spring Boot da classe `ProdutoApplication`.
4. Em **Modify options**, habilite **Environment variables**.
5. Configure estas variáveis, usando a mesma senha configurada no container:

   ```text
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/cadastro_produtos
   SPRING_DATASOURCE_USERNAME=cadastro_produtos
   SPRING_DATASOURCE_PASSWORD=senha-local-dev
   ```

6. Salve com **Apply** e **OK** e execute a configuração pelo botão **Run**.

As variáveis devem ser configuradas na configuração do IntelliJ se a API for iniciada pelo botão Run. Variáveis definidas com `$env:` em um terminal só valem naquela sessão do terminal.

### Banco e tabelas

O banco `cadastro_produtos` é criado na primeira inicialização do container por `POSTGRES_DB`. Ao conectar, o Hibernate cria/atualiza a tabela `produtos` a partir da entidade. O `POST /api/produtos` cadastra um registro; ele não cria o banco de dados. Para visualizar registros, conecte uma ferramenta como DBeaver a `localhost:5432`, no database `cadastro_produtos`, schema `public`.

## Swagger UI (OpenAPI)

Com a aplicação em execução, acesse [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) para explorar e testar os endpoints. A especificação OpenAPI em JSON fica disponível em [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs). O Swagger não abre automaticamente; é preciso abrir o endereço no navegador.

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

O CORS permite chamadas da origem local `http://localhost:4200`. Para outros ambientes, ajuste a origem permitida em `CorsConfig`. As credenciais do banco não devem ser versionadas; configure-as no ambiente ou na configuração local da IDE.
