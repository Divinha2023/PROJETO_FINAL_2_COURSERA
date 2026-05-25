# Documentação técnica

O Organizador de Tarefas é uma aplicação Spring Boot com interface web, API REST e persistência em banco relacional. O objetivo é manter o cadastro de tarefas centralizado, com prioridade, prazo, responsável, status e etiquetas.

## Arquitetura

- `controller`: recebe as requisições web e REST.
- `service`: concentra as regras de negócio e a organização dos dados.
- `repository`: isola o acesso ao banco com Spring Data JPA.
- `model`: contém a entidade persistida e os enums do domínio.
- `dto`: define os formatos de entrada e saída da API.

## Estruturas de dados

- `List`: usada para manipular coleções de tarefas retornadas pelo repositório.
- `Queue`: usada para montar a fila de prioridade das próximas tarefas.
- `Map`: usado para calcular resumos por status e por responsável.
- `Set`: usado para manter etiquetas sem duplicidade.
- `String[]`: usado no recebimento e tratamento das etiquetas enviadas pela API ou pelo formulário.
- `String`: usada na busca textual e na normalização dos valores digitados.

## Persistência

A aplicação usa Spring Data JPA com banco H2 em memória. Essa configuração mantém o projeto leve para execução local, mas conserva o mesmo padrão usado com bancos relacionais tradicionais.

## API

A API REST disponibiliza operações de consulta, criação, atualização, alteração parcial de status, exclusão, resumo e fila de prioridade. Os controllers retornam códigos HTTP compatíveis com cada operação, como `200`, `201`, `204`, `400` e `404`.
