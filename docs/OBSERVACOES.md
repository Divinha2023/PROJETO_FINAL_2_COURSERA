# Observações de implementação

Este projeto foi organizado para atender aos critérios do trabalho final sem depender de serviços externos. A aplicação sobe com dados iniciais em memória, mas todas as operações passam pelo Spring Data JPA, como aconteceria em um banco relacional comum.

## Pontos que atendem aos critérios

- O CRUD principal está na entidade `Tarefa`, com criação, leitura, atualização e exclusão pela interface web e pela API REST.
- A API REST usa `GET`, `POST`, `PUT`, `PATCH` e `DELETE`, retornando códigos como `200`, `201`, `204`, `400` e `404`.
- A conexão com banco de dados é feita com H2 e JPA. A tabela principal é criada automaticamente durante a execução.
- A interface web usa Thymeleaf para renderizar as telas no servidor.
- A classe `TarefaService` centraliza as regras de negócio e evita que os controllers tenham lógica duplicada.

## Onde aparecem as estruturas de dados

- `List`: usada para manipular as tarefas retornadas pelo repositório.
- `Queue`: usada para montar a fila de prioridade, considerando urgência e prazo.
- `Map`: usado no resumo por status e por responsável.
- `Set`: usado para guardar etiquetas sem duplicidade.
- `String[]`: usado ao transformar o texto de etiquetas em uma estrutura processável.
- `String`: usada na busca textual, na normalização de acentos e no tratamento dos campos digitados.

## Decisões de projeto

- O banco H2 foi escolhido por ser leve e facilitar a execução local durante a correção.
- As etiquetas ficam em uma coleção separada com `@ElementCollection`, o que evita salvar tudo em uma única coluna de texto.
- Os DTOs da API separam os dados recebidos e devolvidos, deixando a entidade protegida de mudanças diretas pelo cliente.
- As mensagens e comentários importantes estão em português para facilitar a leitura no GitHub e durante a apresentação.
