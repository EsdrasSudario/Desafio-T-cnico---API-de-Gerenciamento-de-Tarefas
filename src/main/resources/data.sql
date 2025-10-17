-- Dados iniciais para testes
-- Usuário de teste: username=testuser, password=password123

INSERT INTO roles (name) VALUES ('ROLE_USER');

INSERT INTO roles (name) VALUES ('ROLE_MODERATOR');

INSERT INTO roles (name) VALUES ('ROLE_ADMIN');

INSERT INTO users (username, email, password) VALUES ('testuser', 'testuser@mail', '$2a$10$Snw7PpTutOfWAS6.f7CqUOXR/PXE7zYoVUJrVLOowALkt9sP2MBy2');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);

INSERT INTO user_roles (user_id, role_id) VALUES (1, 3);

--INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
--VALUES (1, 'Implementar autenticação JWT', 'Adicionar segurança com JWT na API', 'HIGH', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

--INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
--VALUES (2, 'Escrever testes unitários', 'Criar testes para os services', 'MEDIUM', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

--INSERT INTO tasks (id, title, description, priority, completed, created_at, updated_at, user_id) 
--VALUES (3, 'Documentar API', 'Adicionar Swagger/OpenAPI', 'LOW', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);
