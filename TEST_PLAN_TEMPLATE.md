# Plano de Testes - API de Gerenciamento de Tarefas

**Candidato:** Esdras Sudário  
**Data:** 15/10/2025

---

## 1. Estratégia de QA

### 1.1 Tipos de Teste
Descreva quais tipos de teste você aplicaria neste projeto:
- [ X ] Testes Unitários
- [ X ] Testes de Integração
- [ X ] Testes de Segurança
- [   ] Testes de Performance
- [ X ] Outros: Manuais / Exploratórios

### 1.2 Ferramentas Utilizadas
Liste as ferramentas que você usaria:
- Automação de Testes: Spring Boot Test(JUnit e Mockito)
- Testes Manuais/Exploratórios: Interface de usuário, do SWAGGER, no navegador.
- Gestão de Testes: Git
- CI/CD: GitHub Actions

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
- [ X ] Registrar usuário com dados válidos, status 200.

**Cenários de Erro:**
- [ X ] Registrar com nome já usado, status 400.
- [ X ] Registrar com e-mail já usado, status 400.

#### POST /api/auth/login

**Cenários de Sucesso:**
- [ ] Login com credenciais válidas

**Cenários de Erro:**
- [ ] ?

### 2.3 Gerenciamento de Tarefas

#### POST /api/tasks

**Cenários de Sucesso:**
- [ ] Criar tarefa com todos os campos válidos
- [ ] Criar tarefa sem descrição (opcional)

**Cenários de Erro:**
- [ ] ?

#### GET /api/tasks

**Cenários de Sucesso:**
- [ ] Listar tarefas do usuário autenticado
- [ ] Verificar ordenação das tarefas por prioridade

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

**Cenários de Teste:**
- [ ] Verifique acessos dos endpoints com/sem token válido
- [ ] Verificar comportamento do token
- [ ] Verificar se usuário só acessa suas próprias tarefas
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
**Localização:** AuthController  
**Descrição:** Ao tentar registrar usuário o sistema com nome já existente  
**Passos para Reproduzir:**
1. No end-point para registro de usuário tentar enviar um nome que já tem registro

**Resultado Esperado:** Status 400  
**Resultado Atual:** Status 200  
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

