# Plano de Testes - API de Gerenciamento de Tarefas

**Candidato:** [Seu Nome]  
**Data:** [Data de elaboração]

---

## 1. Estratégia de QA

### 1.1 Tipos de Teste
Descreva quais tipos de teste você aplicaria neste projeto:
- [ ] Testes Unitários
- [ ] Testes de Integração
- [ ] Testes de Segurança
- [ ] Testes de Performance
- [ ] Outros: _____________

### 1.2 Ferramentas Utilizadas
Liste as ferramentas que você usaria:
- Automação de Testes: _______________
- Testes Manuais/Exploratórios: _______________
- Gestão de Testes: _______________
- CI/CD: _______________

### 1.3 Priorização
Como você priorizaria os testes? Justifique.

---

## 2. Cenários de Teste

### 2.1 Autenticação

#### POST /api/auth/register

**Cenários de Sucesso:**
- [ ] Registrar usuário com dados válidos

**Cenários de Erro:**
- [ ] ?

#### POST /api/auth/login

**Cenários de Sucesso:**
- [ ] Login com credenciais válidas

**Cenários de Erro:**
- [ ] ?

### 2.2 Gerenciamento de Tarefas

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

### 2.3 Segurança JWT

**Cenários de Teste:**
- [ ] Verifique acessos dos endpoints com/sem token válido
- [ ] Verificar comportamento do token
- [ ] Verificar se usuário só acessa suas próprias tarefas
- [ ] Outros

---

## 3. Bugs Identificados

### Bug #1
**Título:** [Descreva o bug]  
**Severidade:** [Crítica/Alta/Média/Baixa]  
**Localização:** [Classe/Método]  
**Descrição:** [Descreva o problema]  
**Passos para Reproduzir:**
1. 
2. 
3. 

**Resultado Esperado:**  
**Resultado Atual:**  
**Sugestão de Correção:**

### Bug #2
[Repita o formato acima para cada bug encontrado]

---

## 4. Cobertura de Testes

Descreva qual cobertura de testes você pretende alcançar e por quê.

---

## 5. Riscos e Limitações

Liste possíveis riscos ou limitações identificados no projeto.

---

## 6. Melhorias Sugeridas

Além das correções de bugs, que melhorias você sugeriria para o projeto?

