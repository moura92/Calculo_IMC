# 🧮 API REST para Cálculo de IMC

API REST desenvolvida em **Spring Boot** para cadastro de usuários e cálculo do **Índice de Massa Corporal (IMC)**, retornando a classificação conforme padrões da OMS.

Projeto criado com foco em **boas práticas**, **arquitetura REST**, **documentação OpenAPI/Swagger** e **organização de código**.

---

## 🚀 Tecnologias utilizadas

- Java 21
- Spring Boot 3.5.9
- Spring Web (MVC)
- Spring Data JPA
- Spring HATEOAS
- MySQL
- Flyway
- MapStruct
- SpringDoc OpenAPI (Swagger)
- Maven

---

## 📐 Arquitetura

- Padrão **MVC**
- API **RESTful**
- Separação de responsabilidades
- DTOs para entrada e saída de dados
- Versionamento de API
- Content Negotiation (**JSON / XML**)

---

## ✅ Boas práticas adotadas

- Separação clara entre Controller, Service e Repository
- Uso de DTOs para evitar exposição da entidade
- Tratamento centralizado de exceções
- Versionamento de API (`/v1`)
- Uso de HATEOAS para navegação entre recursos
- Migrations com Flyway
- Testes unitários com Mockito
---
## 🗃️ Versionamento do Banco de Dados (Flyway)

O Flyway é utilizado para versionamento e controle de migrations do banco de dados, garantindo:

- Criação automática do schema
- Histórico de alterações versionado
- Padronização entre ambientes (dev / test / prod)

As migrations estão localizadas em:
`src/main/resources/db/migration`
---
## 📊 Regra de negócio – IMC

O cálculo do IMC é feito pela fórmula:  
**IMC = peso / (altura × altura)**


### Classificação:
- Abaixo de **18.5** → Magreza
- **18.5 a 24.9** → Normal
- **25 a 29.9** → Sobrepeso
- **30 ou mais** → Obesidade

---

## 📡 Endpoints principais
### Usuario
| Método | Endpoint                | Descrição                         |
|------|-------------------------|----------------------------------|
| POST | `/api/usuario/v1`       | Cadastra um usuário e calcula IMC |
| GET  | `/api/usuario/v1`       | Lista usuários cadastrados        |
| GET  | `/api/usuario/v1/{id}`  | Busca usuário por ID         |
| PUT  | `/api/usuario/v1/{id}}` | Atualiza os dados do usuário e recalcula o IMC  |
| DELETE  | `/api/usuario/v1/{id}`  | Remove um usuário pelo ID         |

### Book
| Método | Endpoint             | Descrição     |
| ------ |----------------------| ------------- |
| POST   | `/api/books/v1`      | Criar livro   |
| GET    | `/api/books/v1/{id}` | Buscar por ID |
| PUT    | `/api/books/v1/{id}` | Atualizar     |
| DELETE | `/api/books/v1/{id}` | Deletar       |

---
## 📑 Documentação da API (Swagger)

Após subir a aplicação, acesse:  
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

---

## ▶️ Como executar o projeto

### Pré-requisitos
- Java 21
- Maven
- MySQL
---
### Passos
```bash

git clone https://github.com/moura92/Calculo_IMC.git  
cd Calculo_IMC  
mvn spring-boot:run
```
---

## 🧪 Testes Automatizados

O projeto conta com testes unitários focados na **camada de serviço**, utilizando:

- JUnit 5
- Mockito

Os testes validam:
- Busca por ID inválido
- Recurso não encontrado
- Criação de usuário
- Atualização de usuário
- Exclusão de usuário

Os repositórios são **mockados**, evitando dependência de banco de dados nos testes unitários.

### Executar os testes:
```bash
mvn test
```
# ⚙️ Configuração da aplicação

### As configurações do projeto estão no arquivo:  ***src/main/resources/application.properties***
spring.application.name=calculimc  
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver  
spring.datasource.url=jdbc:mysql://localhost:3306/calculoimc?useTimezone=true&serverTimezone=UTC  
spring.datasource.username=root  
spring.datasource.password=SENHA_EXEMPLO

## JPA / Hibernate
spring.jpa.hibernate.ddl-auto=validate  
spring.jpa.show-sql=true  
spring.jpa.open-in-view=false

## Flyway (Migrations)
spring.flyway.enabled=true  
spring.flyway.locations=classpath:db/migration  
spring.flyway.baseline-on-migrate=true

### Para desabilitar o Flyway:  
spring.flyway.enabled=false  

## 🪵 Logs da aplicação

logging.level.root=warn  
logging.level.com.moura=INFO

---
## 📌 Licença

Este projeto está sob a licença **MIT License**, permitindo uso, estudo e modificação.

---
## 👨‍💻 Autor

### Alisson de Moura
Estudante de backend Java ☕

- GitHub: https://github.com/moura92

- LinkedIn: https://www.linkedin.com/in/alisson-moura-071410238/
