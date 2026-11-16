# Orientador de Programas Sociais - Back-end

> 🔗 **Front-end (site):** [orientador-digital-react-integrado-api](https://github.com/ThainaDev96/orientador-digital-react-integrado-api)

API em Spring Boot que faz o cadastro e o login dos usuários (guardados no PostgreSQL) e busca os programas sociais na API do gov.br. O site (front-end em React) usa esta API.

> [!IMPORTANT]
> **Para usar o site, rode estes 2 comandos, cada um no seu terminal:**
>
> **1. Back-end**, na pasta deste projeto:
> ```powershell
> .\mvnw.cmd spring-boot:run
> ```
>
> **2. Front-end**, na pasta do front:
> ```powershell
> npm run dev
> ```
>
> **3. Navegador:** abra **http://localhost:5173**

---

## Pré-requisitos

| Programa              | Versão                | Para quê                            | Como conferir      |
|-----------------------|-----------------------|-------------------------------------|--------------------|
| **JDK (Java)**        | 21                    | Rodar o back-end                    | `java -version`    |
| **Docker Desktop**    | atual                 | Rodar o banco PostgreSQL            | `docker --version` |
| **Node.js** (com npm) | 22 (ou 20.19 e acima) | Rodar o front-end                   | `node -v`          |
| **Internet**          | -                     | O back busca os programas no gov.br | -                  |

- O **Maven** não precisa ser instalado: o projeto já traz o `mvnw.cmd`.
- Opcional: **IntelliJ IDEA**, para abrir os projetos, e **pgAdmin** ou **DBeaver**, para ver o banco.

---

## Antes da primeira vez

1. Abra o **Docker Desktop**.
2. Crie o arquivo **`.env`** na pasta deste projeto, ao lado do `pom.xml`: copie o `.env.example`, dê à cópia o nome `.env` e preencha os valores. Ele não vai para o Git porque guarda a senha do banco.
3. Suba o banco PostgreSQL:
   ```powershell
   docker compose up -d
   ```
4. Na pasta do front, instale as bibliotecas (só na primeira vez):
   ```powershell
   npm install
   ```

---

## 1. Rodar o back-end

Na pasta deste projeto:

```powershell
.\mvnw.cmd spring-boot:run
```

- Está pronto quando aparecer **`Started SpringBootProjectApplication`**.
- O back roda em **http://localhost:8080**.
- Deixe este terminal aberto: se fechar, o back para.

## 2. Rodar o front-end

Abra **outro terminal**, na pasta do front:

```powershell
npm run dev
```

- Está pronto quando aparecer **`Local: http://localhost:5173/`**.

## 3. Abrir no navegador

**http://localhost:5173**

1. Clique em **Criar conta** e preencha os dados.
2. Pronto: você já entra e vê os programas.
3. Nas próximas vezes, use **Entrar** com o mesmo e-mail e senha.

> [!NOTE]
> Abra sempre por `localhost`, e não por `127.0.0.1`, porque o back só aceita pedidos do `http://localhost:5173`.
> No front, o endereço do back fica em `src/servicos/api.js`: `const API_URL = 'http://localhost:8080'`.

---

## Rotas da API

| Método | Rota                       | Para quê                                      | Precisa de login |
|--------|----------------------------|-----------------------------------------------|------------------|
| POST   | `/api/auth/cadastro`       | Criar conta `{ nome, email, senha }`          | Não              |
| POST   | `/api/auth/login`          | Entrar `{ email, senha }`                     | Não              |
| GET    | `/api/auth/me`             | Quem está logado                              | Sim              |
| POST   | `/api/auth/logout`         | Sair                                          | Sim              |
| GET    | `/api/programas`           | Lista dos programas                           | Sim              |
| GET    | `/api/programas/{codigo}`  | Detalhe de um programa (ex.: `prouni`)        | Sim              |

---

## Parar

- **Back e front:** `Ctrl+C` no terminal de cada um.
- **Banco:** `docker compose stop`. Os dados continuam guardados.