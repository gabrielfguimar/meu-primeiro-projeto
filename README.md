# 🗓️ API de Gerenciamento de Eventos

Uma API REST robusta e segura para gerenciamento de eventos corporativos, desenvolvida com o ecossistema Spring Boot e persistência em banco de dados relacional PostgreSQL. A aplicação conta com uma camada robusta de autenticação e autorização utilizando Spring Security e Tokens JWT.

## 🚀 Tecnologias Utilizadas

* **Java 21**
* **Spring Boot 3.x**
    * Spring Web (Construção de endpoints REST)
    * Spring Data JPA (Abstração de persistência)
    * Spring Security (Controle de acesso e filtros)
    * Spring Validation (Validação de dados de entrada)
* **PostgreSQL** (Banco de dados relacional)
* **Java-JWT (Auth0)** (Geração e validação de tokens seguros)
* **Docker & Docker Compose** (Containerização do banco de dados)
* **Maven** (Gerenciador de dependências)

---

## 🏗️ Arquitetura do Sistema

O projeto foi estruturado seguindo as melhores práticas do padrão de arquitetura em camadas, garantindo baixo acoplamento e separação de responsabilidades:

* `controller`: Camada de entrada que expõe os endpoints REST e recebe as requisições.
* `dto`: Objetos de Transferência de Dados (Records) usados para validar e transportar dados de forma segura entre a requisição e a API.
* `model`: Entidades de negócio mapeadas diretamente como tabelas do banco de dados (ORM).
* `repository`: Camada de acesso aos dados, utilizando interfaces que estendem o `JpaRepository`.
* `service`: Camada que isola as regras de negócio da aplicação e lida com criptografia de senhas e lógica de tokens.
* `security`: Configurações centrais do Spring Security e filtros customizados de interceptação de requisições (`OncePerRequestFilter`).

---

## 🛡️ Infraestrutura de Segurança

A API adota o modelo **Stateless** baseado em autenticação por tokens **JWT (JSON Web Tokens)**:
* As senhas dos usuários são criptografadas antes do armazenamento utilizando o algoritmo **BCrypt**.
* A listagem de eventos é aberta ao público, mas ações de escrita (criar, editar e deletar) exigem que o usuário possua a permissão (`Role`) de `ADMIN`.

---

## 🛠️ Como Executar o Projeto

### Pré-requisitos
* Docker e Docker Compose instalados.
* Java 21 (ou superior) e Maven configurados localmente (opcional, caso queira rodar fora do container).

### Passo a Passo

1.  **Subir o Banco de Dados (Docker):**
    Na raiz do projeto, execute o comando abaixo para iniciar o container do PostgreSQL isolado:
    ```bash
    docker compose up -d
    ```

2.  **Executar a Aplicação:**
    Use o Maven Wrapper do projeto para compilar e iniciar o servidor Spring Boot:
    ```bash
    ./mvnw spring-boot:run
    ```
    A API estará disponível e pronta para receber conexões em: `http://localhost:8080`

---

## 🏁 Fluxo de Teste dos Endpoints

Você pode testar o ecossistema completo utilizando clientes HTTP como o **Thunder Client** ou **Postman** seguindo a ordem abaixo:

### 1. Criar uma Conta Administrativa
* **Método:** `POST`
* **URL:** `http://localhost:8080/api/auth/registrar`
* **Body (JSON):**
    ```json
    {
      "login": "admin_user",
      "senha": "password123",
      "role": "ADMIN"
    }
    ```

### 2. Efetuar Login para Obter o Token JWT
* **Método:** `POST`
* **URL:** `http://localhost:8080/api/auth/login`
* **Body (JSON):**
    ```json
    {
      "login": "admin_user",
      "senha": "password123"
    }
    ```
* **Resposta Esperada (200 OK):** Copie o valor gerado dentro do campo `"token"`.

### 3. Cadastrar um Novo Evento (Protegido)
* **Método:** `POST`
* **URL:** `http://localhost:8080/api/eventos`
* **Autenticação:** Escolha o tipo **Bearer Token** e cole o token copiado no passo anterior.
* **Body (JSON):**
    ```json
    {
      "nome": "DevFest Belo Horizonte",
      "local": "Expominas",
      "data": "2026-11-20"
    }
    ```

### 4. Listar Eventos (Público)
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/eventos`
* *(Nota: Esta rota está liberada e não exige o envio de tokens no cabeçalho).*
