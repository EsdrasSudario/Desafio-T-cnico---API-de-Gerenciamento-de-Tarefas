import http from 'k6/http';
import { check, sleep } from 'k6';

// 🔧 Configuração do teste
export const options = {
  vus: 10,           // usuários virtuais simultâneos
  duration: '30s',   // duração total do teste
  thresholds: {       // critérios de sucesso
    http_req_duration: ['p(95)<800'],  // 95% das requisições < 800ms
    checks: ['rate>0.95'],             // 95% das validações devem passar
  },
};

// 🌐 URLs base da API
const BASE_URL = 'http://localhost:8080/api';
const LOGIN_URL = `${BASE_URL}/auth/login`;

// ⚙️ Função de setup: executa uma vez antes do teste
export function setup() {
  // 🔐 Faz login para obter o token JWT
  const loginPayload = JSON.stringify({
    username: 'testuser',  // ajuste conforme seu usuário
    password: 'password123', // ajuste conforme sua senha
  });

  const loginHeaders = { 'Content-Type': 'application/json' };

  const loginRes = http.post(LOGIN_URL, loginPayload, { headers: loginHeaders });

  check(loginRes, {
    'login OK (200)': (r) => r.status === 200,
    'token presente': (r) => r.json('token') !== undefined,
  });

  const token = loginRes.json('token');
  return { token }; // passa o token para o teste principal
}

// 🧪 Teste principal — executado por cada usuário virtual
export default function (data) {
  const headers = {
    Authorization: `Bearer ${data.token}`,
    'Content-Type': 'application/json',
  };

  // 1️⃣ GET — listar tarefas
  let getRes = http.get(`${BASE_URL}/tasks`, { headers });
  check(getRes, {
    'GET /tasks -> 200': (r) => r.status === 200,
  });

  // 2️⃣ POST — criar nova tarefa
  const newTask = JSON.stringify({
    title: 'Tarefa teste',
    description: 'Criada via k6',
    priority: 'HIGH',
  });

  let postRes = http.post(`${BASE_URL}/tasks`, newTask, { headers });
  check(postRes, {
    'POST /tasks -> 201': (r) => r.status === 201,
  });

  const createdTask = postRes.json();
  const taskId = createdTask?.id;

  // 3️⃣ PUT — atualizar tarefa (se criada com sucesso)
  if (taskId) {
    const updatedTask = JSON.stringify({
      title: 'Tarefa atualizada',
      description: 'Atualizada via k6',
      priority: 'LOW',
    });

    let putRes = http.put(`${BASE_URL}/tasks/${taskId}`, updatedTask, { headers });
    check(putRes, {
      'PUT /tasks/{id} -> 200': (r) => r.status === 200,
    });
  }

  // 4️⃣ DELETE — remover tarefa (se criada)
  if (taskId) {
    let delRes = http.del(`${BASE_URL}/tasks/${taskId}`, null, { headers });
    check(delRes, {
      'DELETE /tasks/{id} -> 204': (r) => r.status === 204,
    });
  }

  // Pausa entre as requisições (simula uso real)
  sleep(1);
}
