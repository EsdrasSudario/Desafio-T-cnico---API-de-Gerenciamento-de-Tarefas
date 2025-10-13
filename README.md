# Desafio Técnico - API de Gerenciamento de Tarefas

## Contexto

Bem-vindo ao desafio técnico para a vaga de **Desenvolvedor Java Júnior com foco em QA**!

Este projeto é uma API REST desenvolvida em **Spring Boot 3.x** com **Java 21** para gerenciamento de tarefas. A API possui autenticação JWT e operações CRUD completas. No entanto, o código contém **bugs intencionais** e **não possui testes automatizados**.

Seu objetivo é atuar como um profissional de QA, identificando problemas, planejando testes, implementando automação e corrigindo os bugs encontrados.

---

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.5**
- **Spring Security 6.x** (com JWT)
- **Spring Data JPA**
- **H2 Database** (banco em memória)
- **Maven**
- **JUnit 5** (para testes)
- **Mockito** (para mocks)

---

## Funcionalidades da API

### Endpoints de Autenticação (públicos)

- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Login e obtenção de token JWT

### Endpoints de Tarefas (requerem autenticação)

- `POST /api/tasks` - Criar nova tarefa
- `GET /api/tasks` - Listar todas as tarefas do usuário autenticado
- `GET /api/tasks/{id}` - Buscar tarefa específica por ID
- `PUT /api/tasks/{id}` - Atualizar tarefa
- `DELETE /api/tasks/{id}` - Deletar tarefa

---

## Como Executar o Projeto

### Pré-requisitos

- Java 21 instalado
- Maven 3.6+ instalado

### Passos

1. Clone o repositório:
```bash
git clone <url-do-repositorio>
cd task-api-challenge
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

4. A API estará disponível em: `http://localhost:8080`

5. Console H2 disponível em: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:taskdb`
   - Username: `sa`
   - Password: (deixe em branco)

---

## Dados de Teste

O banco de dados é populado automaticamente com um usuário de teste:

- **Username:** `testuser`
- **Password:** `password123`

---

## Suas Tarefas

### 1. Análise e Planejamento de Testes (Obrigatório)

Crie um arquivo `TEST_PLAN.md` na raiz do projeto contendo:

- **Cenários de Teste:** Liste os cenários que você testaria para cada endpoint (caminho feliz, casos de borda, cenários de erro)
- **Estratégia de QA:** Descreva como você abordaria o teste desta API (tipos de teste, ferramentas, priorização)
- **Bugs Identificados:** Documente os bugs que você encontrou durante a análise exploratória do código

### 2. Implementação de Testes Automatizados (Obrigatório)

Implemente os seguintes testes:

#### Testes de Integração
- Testes para todos os endpoints de autenticação
- Testes para todos os endpoints de tarefas
- Testes de segurança (acesso sem token, token inválido, token expirado)

#### Testes Unitários
- Testes para `AuthService`
- Testes para `TaskService`
- Testes para `JwtTokenProvider`

**Dica:** Use `@SpringBootTest`, `TestRestTemplate` ou `MockMvc` para testes de integração, e `Mockito` para testes unitários.

### 3. Correção de Bugs (Obrigatório)

Após identificar os bugs através dos testes:

- Corrija todos os bugs encontrados
- Garanta que todos os testes passem após as correções
- Mantenha commits organizados e descritivos

### 4. Tarefas Bônus (Opcional)

- Configure um workflow de CI/CD com GitHub Actions ou em containers próprios (preferencialmente)
- Adicione documentação Swagger/OpenAPI
- Implemente validações adicionais
- Adicione logs estruturados

---

## Critérios de Avaliação

Seu desafio será avaliado com base em:

1. **Planejamento de Testes** - Qualidade e completude do plano de testes
2. **Implementação de Testes** - Cobertura, qualidade e eficácia dos testes automatizados
3. **Correção de Bugs** - Identificação e correção adequada dos problemas
4. **Qualidade do Código** - Código limpo, organizado e seguindo boas práticas
5. **Uso de Git** - Commits e branches organizados, mensagens descritivas
6. **Documentação** - README claro e documentação adequada

---

## Instruções de Entrega
1. Repositório: Compartilhe o repositório no Github com o usuário @eniocc
2. Email: Envie para cticurriculo@email.com
3. Assunto: DESAFIO TÉCNICO - [SEU NOME] - JAVA JÚNIOR QA informando que compartilhou repositório e enviando o link do repositório no corpo do texto.
4. Prazo: até às 23h59min do dia 19/10/2025.

--- 

## Template do Email:
* Nome: [Seu nome completo]
* Repositório: [Link do GitHub/GitLab]
* Instruções especiais: [Se houver]
* Tempo investido: [Estimativa de horas]
* Principais desafios: [Breve relato]

---

## ❓ Dúvidas e Suporte
* Email: cticurriculo@email.com
Assunto: DÚVIDA DESAFIO JAVA JÚNIOR QA - [SEU NOME]
* Prazo para dúvidas: Até 48h antes do deadline.