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
| Interface `UsuarioRepository` | Estoquista: guarda e busca as fichas dos usuários na despensa | Só lista o que ele faz, sem código dentro. Estende `JpaRepository`, que já traz `save` e `findById`, e tem `findByEmail` e `existsByEmail`. O Spring escreve o código sozinho ao ligar |

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

### Usuário e banco de dados do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `Usuario` | Ficha de cadastro: o molde que cada cliente preenche | Marcada com `@Entity` e `@Table(name = "usuario")`, vira a tabela `usuario` no PostgreSQL. Tem `id` (gerado pelo banco), `nome`, `email` (único) e `senha`, com getters e setters para ler e preencher. O Hibernate cria a tabela ao ligar o Spring |
| `UsuarioRepository` | Estoquista: entra na despensa para guardar e buscar as fichas | Interface que estende `JpaRepository<Usuario, Long>` e já traz o **CRUD** pronto (`save`, `findById`, `findAll`, `delete`). Tem também `findByEmail`, que busca pelo e-mail e devolve um `Optional`, e `existsByEmail`, que diz se o e-mail já está cadastrado. O Spring escreve o código sozinho ao ligar |

### Cadastro e segurança do Spring Boot

| Classe | Papel| No código |
|--------|------|-----------|
| `spring-boot-starter-security` (no `pom.xml`) | Contratação do segurança da porta| Dependência que traz o login e a proteção das rotas. Ao ligar o Spring, ela já tranca todas as rotas e cria um usuário de teste na memória, que depois trocamos pelo login com o nosso banco |
| `SecurityConfig` | Gerente da segurança: monta as peças que o segurança da porta precisa | Usa `@Configuration` e `@Bean` para criar o `PasswordEncoder` (BCrypt), que transforma a senha em hash (código embaralhado que não volta ao original) e confere a senha no login. Sem ele, a senha ficaria legível no banco |
| DTO `CadastroRequest` | Formulário de cadastro: o que o cliente preenche| Record com `nome`, `email` e `senha`. Transforma o JSON que o site envia em objeto Java. Fica separado da `Usuario` porque aqui a senha chega como foi digitada, e na ficha guardada ela só pode estar em hash |
| DTO `UsuarioResponse` | Comprovante: o que o cliente recebe de volta na interface, sem a senha| Record com `id`, `nome` e `email`. É a resposta do cadastro. A senha fica de fora de propósito, para nunca sair do back |
| `EmailJaCadastradoException` | Aviso no balcão: "já existe uma ficha com este e-mail" | Erro lançado pelo `cadastrar` do `UsuarioService` quando alguém tenta se cadastrar com um e-mail que já está no banco |
| `ApiExceptionHandler` (método novo) | Gerente: explica ao cliente o aviso do e-mail repetido | Ganhou um método com `@ExceptionHandler(EmailJaCadastradoException.class)` que devolve **409** (conflito) com a mensagem em JSON |
| `UsuarioService` | Cozinha do cadastro: confere o e-mail, embaralha a senha e manda guardar | O método `cadastrar` recusa e-mail repetido com `EmailJaCadastradoException`, aplica o hash na senha com o `PasswordEncoder` e salva pelo `UsuarioRepository` |
| `AuthController` | Garçom do cadastro: recebe a ficha do cliente e leva à cozinha | Rota `POST /api/auth/cadastro`: recebe o JSON, chama o `UsuarioService` e responde **201** com o `UsuarioResponse` |


### Login e sessão do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `UsuarioDetailsService` | Fichário do segurança: o segurança consulta a ficha do cliente pelo e-mail | Implementa `UserDetailsService`. O método `loadUserByUsername` recebe o e-mail, busca no banco com `findByEmail` e entrega ao Spring o e-mail e a senha em hash. Se não achar, lança `UsernameNotFoundException`. O Spring compara a senha digitada com o hash usando o BCrypt. Com ele, a linha `Using generated security password` some do log e o usuário de teste `user` deixa de existir |
| DTO `LoginRequest` | Formulário de login: só o que o cliente preenche para entrar | Record com `email` e `senha`. Transforma o JSON que o site envia no login em objeto Java. Fica separado do `CadastroRequest` porque o login não precisa do `nome` |
| `SecurityConfig` (método `filtroDeSeguranca`) | Regras da porta: quem entra livre e quem precisa de ficha | Define as regras de acesso. `/api/auth/**` (cadastro e login) fica liberado para todos, e o resto exige login. Sem entrar, a rota responde **403**. O CSRF está desligado por enquanto e vamos revisar ao ligar o front |
| `SecurityConfig` (método `authenticationManager`) | Conferente da porta: quem checa se o e-mail e a senha batem | Entrega o `AuthenticationManager` para o controller usar. Ele chama o `UsuarioDetailsService` para buscar o usuário e confere a senha com o BCrypt |
| `UsuarioService` (método novo) | Cozinha: busca a ficha do cliente que acabou de entrar | O método `buscarPorEmail` procura o usuário com `findByEmail`. É usado depois do login, para devolver `id`, `nome` e `email` |
| `AuthController` (método novo) | Garçom do login: recebe e-mail e senha e confere na porta | Rota `POST /api/auth/login`. Faz 3 passos: confere e-mail e senha com o `AuthenticationManager`, guarda na sessão que a pessoa entrou (o navegador recebe um cookie e o reenvia nas próximas chamadas) e responde com o `UsuarioResponse`, sem a senha |
| `ApiExceptionHandler` (método novo) | Gerente: explica ao cliente que o e-mail ou a senha estão errados | Ganhou um método com `@ExceptionHandler(BadCredentialsException.class)` que devolve **401** com a mensagem "E-mail ou senha incorretos." em JSON. A mensagem é a mesma para senha errada e para e-mail inexistente, para ninguém descobrir quais e-mails têm conta |

### Logout, quem sou eu e CORS do Spring Boot

| Classe | Papel | No código |
|--------|-------|-----------|
| `SecurityConfig` (logout) | Saída da porta: a pessoa devolve a ficha e sai | O `.logout(...)` no `filtroDeSeguranca` cria a rota `POST /api/auth/logout`. Ela encerra a sessão, e depois disso as rotas protegidas voltam a responder **403** para aquela pessoa |
| `SecurityConfig` (rota `/error`) | Corredor de serviço: precisa estar aberto para o aviso passar | Libera a rota interna `/error` do Spring com `permitAll`. Sem isso, o erro **401** do `/me` virava **403**, porque o Spring usa o `/error` por dentro para montar a resposta |
| `SecurityConfig` (CORS) | Aviso na porta: "siga a lista de convidados" | A linha `.cors(Customizer.withDefaults())` manda o Spring Security usar a lista da `CorsConfig` |
| `CorsConfig` | Lista de convidados: quais sites de fora podem falar com o back | Usa `@Configuration` e `@Bean` para criar o `CorsConfigurationSource`, com os endereços do front permitidos (como `http://localhost:5173`), os métodos e o `setAllowCredentials(true)`, que deixa o navegador enviar o cookie da sessão. Sem essa lista, o navegador bloqueia o front de chamar o back |
| `AuthController` (método `eu`) | Garçom: "quem é o cliente desta mesa?" | Rota `GET /api/auth/me`. Se a pessoa está logada, devolve o `UsuarioResponse`. Se não, responde **401**. O front usa ao recarregar a página, para saber se a pessoa continua logada |