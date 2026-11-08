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