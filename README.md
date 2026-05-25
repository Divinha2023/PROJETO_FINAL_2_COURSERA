# Organizador de Tarefas

Aplicação web desenvolvida em Java com Spring Boot para cadastrar, acompanhar e priorizar tarefas de uma equipe. A solução reúne uma interface simples para uso diário e uma API REST para integração com outros sistemas.

## Recursos principais

- Cadastro, edição, listagem e exclusão de tarefas.
- Interface web com Thymeleaf.
- API REST com códigos HTTP adequados.
- Persistência com Spring Data JPA e banco H2.
- Filtros por status e busca textual.
- Painel com indicadores e fila de prioridade.
- Dados iniciais para demonstrar o fluxo da aplicação.

## Estruturas de dados usadas

- `String`: normalização de textos, busca por termo e tratamento de etiquetas.
- `String[]`: separação das etiquetas digitadas pelo usuário.
- `List`: listagem das tarefas retornadas pelo banco.
- `Queue`: montagem da fila de prioridade das próximas tarefas.
- `Map`: resumo de tarefas por status e por responsável.
- `Set`: armazenamento de etiquetas sem repetição.

## Como executar

No Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
./mvnw spring-boot:run
```

Depois acesse:

- Interface web: `http://localhost:8080/tarefas`
- API REST: `http://localhost:8080/api/tarefas`
- Console H2: `http://localhost:8080/h2-console`

Configuração do H2:

- JDBC URL: `jdbc:h2:mem:organizador`
- Usuário: `sa`
- Senha: deixar em branco

## Exemplos da API

Criar tarefa:

```http
POST /api/tarefas
Content-Type: application/json
```

```json
{
  "titulo": "Preparar reunião semanal",
  "descricao": "Revisar pendências da equipe e separar os próximos encaminhamentos.",
  "responsavel": "Mariana",
  "status": "PENDENTE",
  "prioridade": "ALTA",
  "prazo": "2026-06-05",
  "tags": ["planejamento", "reuniao"]
}
```

Endpoints disponíveis:

- `GET /api/tarefas`
- `GET /api/tarefas/{id}`
- `POST /api/tarefas`
- `PUT /api/tarefas/{id}`
- `PATCH /api/tarefas/{id}/status?status=CONCLUIDA`
- `DELETE /api/tarefas/{id}`
- `GET /api/tarefas/resumo`
- `GET /api/tarefas/fila`

## Documentação técnica

A documentação técnica está em português no arquivo [docs/DOCUMENTACAO_TECNICA.md](docs/DOCUMENTACAO_TECNICA.md). Ela resume a arquitetura, as principais estruturas de dados e as decisões de implementação.

## Testes

Para executar os testes automatizados:

```powershell
.\mvnw.cmd test
```
