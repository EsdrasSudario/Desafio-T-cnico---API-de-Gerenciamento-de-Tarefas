-- Dados iniciais para testes
-- Usuário de teste: username=testuser, password=password123

INSERT INTO users (id, username, password) VALUES (1, 'testuser', 'password123');

INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
VALUES (1, 'Implementar autenticação JWT', 'Adicionar segurança com JWT na API', 'HIGH', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
VALUES (2, 'Escrever testes unitários', 'Criar testes para os services', 'MEDIUM', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
VALUES (3, 'Documentar API', 'Adicionar Swagger/OpenAPI', 'LOW', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

