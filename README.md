
# API de Vendas

Esta é uma API RESTful para gerenciamento de vendas, clientes, filiais e itens. Desenvolvida em Java com Spring Boot, a API oferece funcionalidades completas para operações CRUD e gerenciamento de status para as entidades principais.

## Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e dependências:

*   **Java 21**
*   **Spring Boot 3.5.6**
    *   `spring-boot-starter-actuator`: Monitoramento e gerenciamento da aplicação.
    *   `spring-boot-starter-data-jpa`: Persistência de dados com JPA e Hibernate.
    *   `spring-boot-starter-validation`: Validação de dados com Jakarta Bean Validation.
    *   `spring-boot-starter-web`: Construção de aplicações web RESTful.
    *   `spring-boot-starter-security`: Segurança da aplicação (autenticação e autorização).
*   **PostgreSQL**: Driver para conexão com banco de dados PostgreSQL.
*   **Lombok**: Redução de código boilerplate.
*   **Micrometer Registry Prometheus**: Métricas para monitoramento com Prometheus.
*   **Logstash Logback Encoder**: Integração com Logstash para logging centralizado.
*   **Springdoc OpenAPI UI (Swagger UI)**: Geração automática de documentação da API e interface interativa.
*   **JUnit 5, Mockito**: Ferramentas para testes unitários.

## Estrutura do Projeto

A estrutura principal do projeto segue o padrão de aplicações Spring Boot:

```
api-vendas-main/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── br/com/vendas/api/
│   │   │       ├── VendasApiApplication.java
│   │   │       ├── config/             # Configurações da aplicação
│   │   │       ├── controller/         # Endpoints da API
│   │   │       ├── domain/             # Entidades de domínio e repositórios
│   │   │       ├── enums/              # Enumerações
│   │   │       ├── event/              # Eventos da aplicação
│   │   │       ├── exception/          # Classes de exceção personalizadas
│   │   │       ├── handler/            # Handlers de exceções
│   │   │       ├── mapper/             # Mapeamento entre DTOs e entidades
│   │   │       └── service/            # Lógica de negócio
│   │   └── resources/          # Arquivos de configuração e propriedades
│   └── test/                   # Testes unitários e de integração
├── pom.xml                     # Gerenciamento de dependências Maven
├── Dockerfile                  # Definição para construção da imagem Docker
└── docker-compose.yml          # Definição para orquestração de containers Docker
```

## Como Começar

### Pré-requisitos

Certifique-se de ter o seguinte instalado em sua máquina:

*   **Java Development Kit (JDK) 21** ou superior.
*   **Maven 3.x** ou superior.
*   **Docker** e **Docker Compose** (opcional, para execução em containers).

### Configuração do Ambiente

1.  **Clone o repositório:**

    ```bash
    git clone <URL_DO_REPOSITORIO>
    cd api-vendas-main
    ```

2.  **Construa o projeto com Maven:**

    ```bash
    mvn clean install
    ```

### Executando a Aplicação

#### Via Maven

Para executar a aplicação diretamente:

```bash
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

#### Via Docker Compose

Para construir e executar a aplicação usando Docker Compose:

```bash
docker-compose up --build
```

A API estará disponível em `http://localhost:8080`.

## Endpoints da API

A documentação interativa da API (Swagger UI) estará disponível em `http://localhost:8080/swagger-ui.html` após a inicialização da aplicação.

### Clientes (`/clientes`)

| Método | URL          | Descrição                                  | Status de Resposta |
| :----- | :----------- | :----------------------------------------- | :----------------- |
| `POST` | `/clientes`  | Cria um novo cliente.                      | `201 Created`      |
| `GET`  | `/clientes`  | Lista todos os clientes ativos.            | `200 OK`           |
| `GET`  | `/clientes/{id}` | Busca um cliente pelo ID.                  | `200 OK`           |
| `PUT`  | `/clientes/{id}` | Atualiza um cliente existente.             | `200 OK`           |
| `PATCH`| `/clientes/{id}/ativar` | Ativa um cliente.                          | `204 No Content`   |
| `PATCH`| `/clientes/{id}/desativar` | Desativa um cliente.                       | `204 No Content`   |

**Exemplo de `ClienteRequestDTO`:**

```json
{
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "documento": "123.456.789-00"
}
```

**Exemplo de `ClienteResponseDTO`:**

```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@example.com",
  "documento": "123.456.789-00",
  "ativo": true
}
```

### Filiais (`/filiais`)

| Método | URL          | Descrição                                  | Status de Resposta |
| :----- | :----------- | :----------------------------------------- | :----------------- |
| `POST` | `/filiais`   | Cria uma nova filial.                      | `201 Created`      |
| `GET`  | `/filiais`   | Lista todas as filiais ativas.             | `200 OK`           |
| `GET`  | `/filiais/{id}` | Busca uma filial pelo ID.                  | `200 OK`           |
| `PUT`  | `/filiais/{id}` | Atualiza uma filial existente.             | `200 OK`           |
| `PATCH`| `/filiais/{id}/ativar` | Ativa uma filial.                          | `204 No Content`   |
| `PATCH`| `/filiais/{id}/desativar` | Desativa uma filial.                       | `204 No Content`   |

**Exemplo de `FilialRequestDTO`:**

```json
{
  "nome": "Filial Centro",
  "endereco": "Rua Principal, 123"
}
```

**Exemplo de `FilialResponseDTO`:**

```json
{
  "id": 1,
  "nome": "Filial Centro",
  "endereco": "Rua Principal, 123",
  "ativo": true
}
```

### Itens (`/itens`)

| Método | URL          | Descrição                                  | Status de Resposta |
| :----- | :----------- | :----------------------------------------- | :----------------- |
| `POST` | `/itens`     | Cria um novo item.                         | `201 Created`      |
| `GET`  | `/itens`     | Lista todos os itens ativos.               | `200 OK`           |
| `GET`  | `/itens/{id}` | Busca um item pelo ID.                     | `200 OK`           |
| `PUT`  | `/itens/{id}` | Atualiza um item existente.                | `200 OK`           |
| `PATCH`| `/itens/{id}/ativar` | Ativa um item.                             | `204 No Content`   |
| `PATCH`| `/itens/{id}/desativar` | Desativa um item.                          | `204 No Content`   |

**Exemplo de `ItemRequestDTO`:**

```json
{
  "nome": "Produto X",
  "descricao": "Descrição detalhada do Produto X",
  "preco": 99.99
}
```

**Exemplo de `ItemResponseDTO`:**

```json
{
  "id": 1,
  "nome": "Produto X",
  "descricao": "Descrição detalhada do Produto X",
  "preco": 99.99,
  "ativo": true
}
```

### Vendas (`/vendas`)

| Método | URL          | Descrição                                  | Status de Resposta |
| :----- | :----------- | :----------------------------------------- | :----------------- |
| `POST` | `/vendas`    | Cria uma nova venda.                       | `201 Created`      |
| `GET`  | `/vendas`    | Lista todas as vendas.                     | `200 OK`           |
| `GET`  | `/vendas/{id}` | Busca uma venda pelo ID.                   | `200 OK`           |
| `PUT`  | `/vendas/{id}` | Atualiza uma venda existente.              | `200 OK`           |
| `PATCH`| `/vendas/{id}/cancelar` | Cancela uma venda.                         | `200 OK`           |
| `DELETE`| `/vendas/{id}` | Deleta uma venda.                          | `204 No Content`   |

**Exemplo de `VendaRequestDTO`:**

```json
{
  "clienteId": 1,
  "filialId": 1,
  "itens": [
    {
      "itemId": 1,
      "quantidade": 2
    }
  ]
}
```

**Exemplo de `VendaResponseDTO`:**

```json
{
  "id": 1,
  "numeroVenda": "VENDA-20230925-0001",
  "dataVenda": "2023-09-25T10:00:00Z",
  "status": "EFETUADA",
  "cliente": {
    "id": 1,
    "nome": "João Silva"
  },
  "filial": {
    "id": 1,
    "nome": "Filial Centro"
  },
  "itens": [
    {
      "id": 1,
      "item": {
        "id": 1,
        "nome": "Produto X",
        "preco": 99.99
      },
      "quantidade": 2,
      "precoUnitario": 99.99
    }
  ],
  "valorTotal": 199.98
}
```

## Contribuição

Para contribuir com o projeto, siga os passos:

1.  Faça um fork do repositório.
2.  Crie uma nova branch (`git checkout -b feature/sua-feature`).
3.  Faça suas alterações e commit (`git commit -am 'Adiciona nova feature'`).
4.  Envie para a branch (`git push origin feature/sua-feature`).
5.  Abra um Pull Request.

## Licença

Este projeto está licenciado sob a licença MIT. Consulte o arquivo `LICENSE` para mais detalhes.


