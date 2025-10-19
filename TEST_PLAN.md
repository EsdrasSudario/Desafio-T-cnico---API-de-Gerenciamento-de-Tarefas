# Plano de Testes - API de Gerenciamento de Tarefas

**Candidato:** Esdras Sudário  
**Data:** 19/10/2025

---

## 1. Estratégia de QA

### 1.1 Tipos de Teste
Descreva quais tipos de teste você aplicaria neste projeto:
- [ X ] Testes Unitários
- [ X ] Testes de Integração
- [ X ] Testes de Segurança
- [ X ] Testes de Performance
- [ X ] Outros: Manuais / Exploratórios

### 1.2 Ferramentas Utilizadas
Liste as ferramentas que você usaria:
- Automação de Testes: Spring Boot Test(JUnit e Mockito)
- Testes Manuais/Exploratórios: Interface de usuário, do SWAGGER, no navegador.
- Gestão de Testes: Git
- CI/CD: GitHub Actions
- Testes de Performance: Grafana K6

### 1.3 Priorização
- No início, verifico se o sistema tem o módulo de documentação SWAGGER, caso não tenha, implementar.
- Então, faço testes manuais / exploratórios em cada endpoint, ocorrendo erros faz-se a verificação dos códigos.
- Em seguida, verefico se existe testes de automação( unitários / integração ), não tendo, implementar.
- Então, concluo com testes de segurança. 

---

## 2. Cenários de Teste

### 2.1 Documentação da API

**Cenários de Teste:**
- [ X ] Verificar se a página do SWAGGER é mostrada no navegador
- [ X ] Verificar se o nome e a descrição do sistema é mostrado na página do SWAGGER

---

### 2.2 Autenticação

#### POST /api/auth/register

**Cenários de Sucesso:**
- [ X ] Deve registrar usuário com sucesso.

**Cenários de Erro:**
- [ X ] Deve retornar erro ao registrar usuário com username duplicado.
- [ X ] Deve retornar erro ao registrar usuário com email duplicado.

#### POST /api/auth/login

**Cenários de Sucesso:**
- [ X ] Deve fazer login com sucesso após registro  

**Cenários de Erro:**
- [ X ] Deve falhar login com credenciais inválidas  

### 2.3 Gerenciamento de Tarefas

#### POST /api/tasks

**Cenários de Sucesso:**
- [ X ] Criar tarefa com todos os campos válidos

**Cenários de Erro:**
- [ X ] Criar tarefa sem autorização
- [ X ] Criar tarefa sem descrição (opcional)

#### GET /api/tasks

**Cenários de Sucesso:**
- [ X ] Listar tarefas do usuário autenticado
- [ X ] Verificar ordenação das tarefas por prioridade

**Cenários de Erro:**
- [ ] ?

#### GET /api/tasks/{id}

**Cenários de Sucesso:**
- [ ] Buscar tarefa existente do usuário

**Cenários de Erro:**
- [ ] ?

#### PUT /api/tasks/{id}

**Cenários de Sucesso:**
- [ ] Atualizar título da tarefa
- [ ] Atualizar descrição da tarefa
- [ ] Atualizar prioridade da tarefa
- [ ] Marcar tarefa como concluída

**Cenários de Erro:**
- [ ] ?

#### DELETE /api/tasks/{id}

**Cenários de Sucesso:**
- [ ] Deletar tarefa existente do usuário

**Cenários de Erro:**
- [ ] ?

### 2.4 Segurança JWT

**Cenários de Sucesso:**
- [ ] Deletar tarefa existente do usuário

**Cenários de Erro:**
- [ ] ?

**Cenários de Teste:**
- [ X ] Verifique acessos dos endpoints com/sem token válido.
- [ X ] Verificar comportamento do token com relação ao tempo de expiração.
- [ X ] Verificar se usuário só acessa suas próprias tarefas
- [ ] Outros

---

## 3. Bugs Identificados

### Bug #1
**Título:** Erro de segurança  
**Severidade:** Alta  
**Localização:** Classe : SecurityConfig  
**Descrição:** O Security não está para o uso de token.  
**Passos para Reproduzir:**
1. Execultar o sistema. 
2. Na tela do consele aparece uma senha gerada pelo Security.

**Resultado Esperado:**
	No consele não mais aparecer a senha gerada o sistema configurado para token.  
**Resultado Atual:**
Using generated security password: bddb4522-aa46-450b-97fe-debd5f729598  
**Sugestão de Correção:**
Configurar o Security e tudo que depende dessa configuração.

### Bug #2
**Título:** Implementação do Segurity incompleta  
**Severidade:** Alta  
**Localização:** Várias classes  
**Descrição:** Implementar o Security  
**Passos para Reproduzir:**
1. Iniciar a IDE
2. Inspecionar os arquivos para uso do Security

**Resultado Esperado:** O Segurity proteger o acesso dos end-points  
**Resultado Atual:** Os end-points estão restritos a uma ou mais permissão(ões)  
**Sugestão de Correção:** Corrigir / incrementar código

### Bug #3
**Título:** Spring Boot com versão errada  
**Severidade:** Baixa  
**Localização:** pom.xml  
**Descrição:** A versão estava 3.1.12  
**Passos para Reproduzir:**
1. Iniciar a IDE
2. Inspecionar o arquivo pom.xml

**Resultado Esperado:** A IDE reconhecer a versão correta  
**Resultado Atual:** A versão está atualizada  
**Sugestão de Correção:** Alterar a versão

### Bug #4
**Título:** Registro de usuário sem funcionar  
**Severidade:** Alta  
**Localização:** Vários arquivos  
**Descrição:** Ao tentar fazer registro de usuário o sistema não faz o registro  
**Passos para Reproduzir:**
1. No SWAGGER usar o end-point para registro de usuário

**Resultado Esperado:** O registro do usuário  
**Resultado Atual:** Erro ao registrar  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #5
**Título:** Status do registro errado  
**Severidade:** Alta  
**Localização:** AuthController register  
**Descrição:** Ao tentar registrar usuário o sistema com nome já existente  
**Passos para Reproduzir:**
1. No end-point para registro de usuário tentar enviar um nome que já tem registro

**Resultado Esperado:** Status 400  
**Resultado Atual:** Status 200  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #6
**Título:** Login não funciona  
**Severidade:** Alta  
**Localização:** AuthController login  
**Descrição:** Ao tentar logar, sistema mostra erro  
**Passos para Reproduzir:**
1. No end-point para login, informar nome e senha de usuário já cadastrado  

**Resultado Esperado:** Status 200  
**Resultado Atual:** Status 500  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #7
**Título:** Criando tarefa informando o id  
**Severidade:** Alta  
**Localização:** TaskRequest no argumento id  
**Descrição:** Ao registrar uma tarefa, está permitindo informar o id   
**Passos para Reproduzir:**
1. No end-point para POST /api/tasks no Request body  

**Resultado Esperado:** Não permitir informar o id  
**Resultado Atual:** Permite o registro de id  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #8
**Título:** Criando tarefa campo completed  
**Severidade:** Alta  
**Localização:** TaskRequest no argumento completed  
**Descrição:** Ao registrar uma tarefa, campo completed é mostrado   
**Passos para Reproduzir:**
1. No end-point para POST /api/tasks no Request body  

**Resultado Esperado:** Não permitir informar o completed  
**Resultado Atual:** Permite o registro de completed  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #9
**Título:** Criando tarefa com campos nulos  
**Severidade:** Alta  
**Localização:** TaskRequest   
**Descrição:** Ao registrar uma tarefa com campos nulos o sistema deixa   
**Passos para Reproduzir:**
1. No end-point para POST /api/tasks no Request body  

**Resultado Esperado:** Não permitir criar tarefa com campos nulos  
**Resultado Atual:** Permitir criar tarefa com campos nulos  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #10
**Título:** Sem teste unitários para JwtTokenProvider  
**Severidade:** Alta  
**Localização:** JwtTokenProvider   
**Descrição:** Classe não tem testes   
**Passos para Reproduzir:**
1. Na pasta de testes do projeto não tem o teste para JwtTokenProvider  

**Resultado Esperado:** Testes feitos para JwtTokenProvider  
**Resultado Atual:** Sem teste para JwtTokenProvider  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

### Bug #11
**Título:** GET das tarefas sem ordem por priority  
**Severidade:** Alta  
**Localização:** GET /api/tasks 
**Descrição:** Método não tras as tarefas ordenadas por priority   
**Passos para Reproduzir:**
1. http://localhost:8080/api/tasks método GET  

**Resultado Esperado:** Tarefas ordenadas por priority  
**Resultado Atual:** Tarefas não ordenadas por priority  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 


### Bug #11
**Título:** H2-Console não aparece no navegador.  
**Severidade:** Alta  
**Localização:** http://localhost:8080/h2-console/login.do?jsessionid=8dbf6aedcd6784c531193f3703c72b31 
**Descrição:** Após acessar o site e fazer o login no h2-console, a tela seguinte não aparece.   
**Passos para Reproduzir:**
1. Acessar http://localhost:8080/h2-console/  
2. Fazer o login  

**Resultado Esperado:** Mostrar a tela de trabalho para o h2-console.  
**Resultado Atual:** Não mostrar a tela de trabalho para o h2-console.  
**Sugestão de Correção:** Alterar,incluir código(s) e/ou criar novos arquivos com código necessário. 

---

## 4. Cobertura de Testes

Descreva qual cobertura de testes você pretende alcançar e por quê.

---

## 5. Riscos e Limitações

Liste possíveis riscos ou limitações identificados no projeto.

---

## 6. Melhorias Sugeridas

Além das correções de bugs, que melhorias você sugeriria para o projeto?

