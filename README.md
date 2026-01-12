# 🧮 API REST para Cálculo de IMC

API REST desenvolvida em **Spring Boot** para cadastro de usuários e cálculo do **Índice de Massa Corporal (IMC)**, retornando a classificação conforme padrões da OMS.

Projeto criado com foco em **boas práticas**, **arquitetura REST**, **documentação OpenAPI/Swagger** e **organização de código**.

---

## 🚀 Tecnologias utilizadas

- Java 25
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
- API RESTful
- Separação de responsabilidades
- DTOs para entrada e saída
- Versionamento de API
- Content Negotiation (JSON / XML)

---

## 📊 Regra de negócio – IMC

O cálculo do IMC é feito pela fórmula:

IMC = peso / (altura × altura)


Classificação:
- Abaixo de 18.5 → Magreza
- 18.5 a 24.9 → Normal
- 25 a 29.9 → Sobrepeso
- 30 ou mais → Obesidade

---

## 📡 Endpoints principais

| Método | Endpoint        | Descrição                         |
|------|-----------------|----------------------------------|
| POST | `/api/v1/users` | Cadastra um usuário e calcula IMC |
| GET  | `/api/v1/users` | Lista usuários cadastrados        |
| GET  | `/api/v1/users/{id}` | Busca usuário por ID         |
| PUT  | `/api/v1/users/{id}}` | Atualiza os dados do usuário e recalcula o IMC  |
| DELETE  | `/api/v1/users/{id}` | Remove um usuário pelo ID         |


---

## 📑 Documentação da API (Swagger)

Após subir a aplicação, acesse:
http://localhost:8080/swagger-ui.html


---

## ▶️ Como executar o projeto

### Pré-requisitos
- Java 25
- Maven
- MySQL
---
### Passos
git clone https://github.com/moura92/Calculo_IMC.git

cd Calculo_IMC
mvn spring-boot:run

## ⚙️ Configuração da aplicação

As configurações do projeto estão no arquivo: src/main/resources/application.properties

spring.application.name=calculimc


spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.datasource.url=jdbc:mysql://localhost:3306/calculoimc?useTimezone=true&serverTimezone=UTC

spring.datasource.username=root

spring.datasource.password=senha_exemplo


## JPA / Hibernate

spring.jpa.hibernate.ddl-auto=none

spring.jpa.show-sql=true

spring.jpa.open-in-view=false

spring.jpa.defer-datasource-initialization=false



## Flyway (Migrations)

spring.flyway.enabled=false

spring.flyway.locations=classpath:db/migration


Para habilitar o Flyway:

spring.flyway.enabled=true


## 🪵 Logs da aplicação

logging.level.root=warn

logging.level.com.moura=INFO

---
## ▶️ Executar a aplicação

mvn spring-boot:run

---
## 📑 Swagger / OpenAPI

A documentação da API é gerada automaticamente pelo **SpringDoc OpenAPI**.

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs


---
## 📌 Licença

Este projeto está sob a licença MIT License, permitindo uso, estudo e modificação.

---
## 👨‍💻 Autor

Alisson de Moura

GitHub: https://github.com/moura92

LinkedIn: https://www.linkedin.com/in/alisson-moura-071410238/
