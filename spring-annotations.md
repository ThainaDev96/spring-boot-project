### Annotation do Spring Boot

| Annotation | Onde se coloca | Para que serve |
|---|---|---|
| `@Configuration` | Classe | Marca a classe como de configurações. O Spring lê quando a aplicação inicia |
| `@Bean` | Método | Ensina o Spring a criar um objeto e guardá-lo para entregar a quem pedir |
| `@Value` | Parâmetro ou atributo | Busca um valor do `application.properties` e coloca na variável |
| `@Service` | Classe | Marca a classe como camada de lógica de negócio (o Spring a guarda como bean) |
| `@RestController` | Classe | Marca a classe como porta de entrada: recebe requisições HTTP e devolve dados |
| `@RequestMapping` | Classe ou método | Define o prefixo da URL (ex.: `/api/programas`) |
| `@GetMapping` | Método | Faz o método responder a requisições GET (buscar dados) |
| `@PathVariable` | Parâmetro | Pega o valor que vem dentro da URL (o `{id}`) e coloca no parâmetro |

### Annotation de cadastro e erros do Spring Boot

| Annotation | Onde se coloca | Para que serve |
|---|---|---|
| `@PostMapping` | Método | Faz o método responder a requisições POST (enviar dados, como no cadastro) |
| `@RequestBody` | Parâmetro | Pega o JSON que o site enviou e transforma em objeto Java (ex.: `CadastroRequest`) |
| `@ResponseStatus` | Método | Define o código da resposta quando dá certo (ex.: `CREATED`, que é 201, "criado") |
| `@RestControllerAdvice` | Classe | Marca a classe que pega os erros lançados no projeto e os transforma em resposta |
| `@ExceptionHandler` | Método | Diz qual erro o método trata (ex.: `EmailJaCadastradoException`) e o que responder |
| `@JsonIgnoreProperties(ignoreUnknown = true)` | Classe ou record | Ao ler o JSON, ignora os campos que o record não declarou, em vez de dar erro |

### Annotation do banco de dados (JPA)

| Annotation | Onde se coloca | Para que serve |
|---|---|---|
| `@Entity` | Classe | Marca a classe como uma tabela do banco. O Hibernate cria a tabela a partir dela ao ligar o Spring |
| `@Table` | Classe | Escolhe o nome da tabela no banco (ex.: `usuario`) |
| `@Id` | Atributo | Marca o campo como chave primária, o número que identifica cada linha |
| `@GeneratedValue` | Atributo | O valor do campo é gerado sozinho pelo banco (1, 2, 3...), você não preenche |
| `@Column` | Atributo | Define regras da coluna: `nullable = false` não aceita vazio e `unique = true` não aceita valor repetido |

### Annotation do Java

| Annotation | Onde se coloca | Para que serve |
|---|---|---|
| `@Override` | Método | Avisa que o método reescreve um que vem de uma interface ou de uma classe pai (ex.: `loadUserByUsername`). Se o nome ou os parâmetros não baterem, o IntelliJ mostra erro antes de rodar |