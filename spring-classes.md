### Classes API do Spring Boot

| Classe     | Papel | No código |
|------------|---|---|
| Controller | Garçom: leva o pedido à cozinha e traz o prato ao cliente | Recebe `GET /api/programas/5` e devolve a resposta |
| Service    | Cozinha: prepara o prato | Decide o que fazer com o pedido |
| RestClient | Fornecedor: entrega os ingredientes que a cozinha não tem | Vai até a API externa e traz os dados |
| `AppConfig` | Gerente de compras: contrata o fornecedor e guarda o endereço dele | Usa `@Configuration` e `@Bean` para criar o `RestClient` com a URL base do gov.br, lida de `servicos.base-url` no `application.properties`. O Spring entrega esse `RestClient` pronto ao service |

### Enum e DTO do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| Enum `ProgramaSocial` | Cardápio: lista fixa dos pratos que podem ser pedidos | Define quais programas existem. Guarda os 9 IDs em um lugar só, com nome legível (`PROUNI` em vez de `57`). Só os 9 programas do orientador são aceitos, e se um ID mudar, a alteração é em uma linha |
| DTO `ServicoGovResponse` | Embalagem: o molde onde o prato chega, só com o que o cliente pediu | Define como a resposta chega. Transforma o JSON do gov.br em objeto Java (`servico.nome()`), seleciona só o que o site usa e troca o `String` cru por algo tipado, então o IntelliJ avisa de erros antes de rodar |
| DTO `ProgramaResumo` | Cardápio resumido: só o nome do prato e o número para pedir | Guarda o `codigo` (usado na URL, como `bolsa-familia`) e o `nome` (mostrado na tela, como `Bolsa Família`). Monta a lista de `/api/programas` sem consultar o gov.br, e deixa os detalhes completos para o `ServicoGovResponse` |


### Tratamento de erros do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `ProgramaNaoEncontradoException` | Aviso na porta: "esse prato não existe no cardápio" | Erro lançado pelo `porCodigo` do enum quando o texto da URL não é um dos 9 programas, como `/api/programas/xyz` |
| `GovBrIndisponivelException` | Aviso na cozinha: "o fornecedor não entregou os ingredientes" | Erro lançado pelo `buscar` do service quando o gov.br devolve resposta vazia ou com `hasErro` verdadeiro |
| `ApiExceptionHandler` | Gerente: recebe os avisos de problema e explica ao cliente com educação | Usa `@RestControllerAdvice` para pegar esses erros em um lugar só. Devolve **404** para programa inexistente e **502** para gov.br fora do ar, ambos com uma mensagem clara em JSON |

### Docker e variáveis do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `docker-compose.yml` | Receita de montagem da despensa: ensina o Docker a montar o lugar onde o banco fica | Define o serviço do PostgreSQL (`postgres:16-alpine`) usando os valores do `.env`. `docker compose up -d` sobe o banco e `docker compose down` desce |
| `.env` | Cofre do gerente: guarda as chaves de verdade | Tem a senha, a porta e o nome reais do banco, e o endereço da API do gov.br. Fica só no seu computador e não sobe para o GitHub. O Docker e o Spring leem esse mesmo arquivo |
| `.env.example` | Molde do cofre: mostra quais chaves existem, sem os segredos | Tem os mesmos nomes do `.env`, com valores falsos como `troque_esta_senha`. Sobe para o GitHub, e quem baixa o projeto copia para `.env` e preenche |
| `.gitignore` | Lista do que não pode sair do restaurante | A linha `*.env` faz o Git ignorar o `.env`. O `.env.example` não é ignorado |

### Configuração do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `application.properties` | Caderno de regras da casa: o Spring lê ao ligar | Monta o endereço do banco com as variáveis do `.env` e tem o `ddl-auto=update`, que cria as tabelas sozinho a partir das classes `@Entity` |
| `pom.xml` | Lista de compras: as ferramentas que o Maven baixa | Traz o `spring-boot-starter-data-jpa`, que conversa com o banco, e o driver `postgresql` |