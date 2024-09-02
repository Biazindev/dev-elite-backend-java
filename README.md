# **Backend da Aplicação de Filmes**
Este é o backend de uma aplicação de filmes desenvolvida em Java usando Spring Boot, Hibernate, e Postgres. A aplicação consome dados da API do The Movie Database (TMDb) e fornece uma interface para um frontend React, permitindo salvar, listar e apagar filmes favoritos. A aplicação está hospedada na Render e utiliza um banco de dados Postgres.

# **Tecnologias Utilizadas**
**Java**: Linguagem de programação principal da aplicação.
**Spring Boo**t: Framework para desenvolvimento rápido e fácil de aplicações Java, com suporte a REST APIs.
**Hibernate**: Framework de mapeamento objeto-relacional usado para interagir com o banco de dados.
**PostgreSQL**: Banco de dados relacional usado para armazenar informações sobre filmes e usuários.
**API TMDb**: API externa utilizada para obter informações detalhadas sobre filmes.
**Render**: Plataforma de hospedagem onde o backend está implementado.
# **Funcionalidades**
**Buscar Filmes**: A aplicação consome dados da API TMDb para buscar filmes, exibir detalhes e permitir que o usuário adicione filmes aos seus favoritos.

# **Gerenciar Favoritos**:

**Salvar Favoritos** Os usuários podem adicionar filmes à sua lista de favoritos. Os favoritos são salvos no banco de dados Postgres.
**Apagar Favoritos**: Os usuários podem remover filmes da lista de favoritos.
**Listar Favoritos**: A aplicação permite que os usuários vejam todos os filmes que foram adicionados como favoritos.
# **Compartilhar Lista de Favoritos**:

**A aplicação fornece uma URL fixa** (https://dev-elite-front.vercel.app/favorites) que pode ser compartilhada para acessar a lista de filmes favoritos diretamente no frontend.
# **Como Configurar e Rodar a Aplicação**
# **Pré-requisitos**
**Java 17 ou superior instalado**.
**Maven para gerenciar dependências e construir o projeto**.
**PostgreSQL para o banco de dados**.
**Conta na Render para deploy, ou execute localmente**.
**Credenciais da API TMDb para acessar os dados dos filmes**.
**Passos para Configuração**
**Clone o Repositório**:

# **bash**
**Copiar código**
**git clone** https://github.com/Biazindev/dev-elite-backend-java.git <br>

**Configurar o Banco de Dados**:

# **Crie um banco de dados Postgres para a aplicação**.

# **Atualize as credenciais do banco no arquivo application.properties**:

**properties**
**Copiar código**
spring.datasource.url=jdbc:postgresql://localhost:5432/seu-banco<br>
spring.datasource.username=seu-usuario<br>
spring.datasource.password=sua-senha<br>
spring.jpa.hibernate.ddl-auto=update<br>

# **Configurar a API TMDb**:
<br>
<br>
 **Adicione a chave da API TMDb no application.properties:**

**properties**
**Copiar código**
tmdb.api.key=sua-chave-tmdb

# **Rodar o Projeto**:

**Compile e rode o projeto com Maven**:

**bash**
**Copiar código**
mvn clean install
mvn spring-boot:run
**A aplicação estará disponível em** http://localhost:8080.

# **Endpoints Principais**
**Buscar Filmes**:

GET /api/movies?query={nome-do-filme} - Busca filmes pelo nome.
Gerenciar Favoritos:

POST /api/favorites - Adiciona um filme aos favoritos. Requer o ID do filme do TMDb.<br>
GET /api/favorites - Lista todos os filmes favoritos.<br>
DELETE /api/favorites/{id} - Remove um filme dos favoritos pelo ID.<br>
Compartilhar Favoritos:<br>

**A URL para acessar a lista de favoritos no frontend é https://dev-elite-front.vercel.app/favorites**.

# **Deploy na Render**
**Configuração do Serviço**:

Crie um novo serviço Web na Render.
Conecte o repositório Git.
Configure o build command como ./mvnw clean install e o start command como java -jar target/nome-do-seu-app.jar.
Defina as variáveis de ambiente para conectar com o banco de dados e API TMDb.
Banco de Dados Postgres:

Adicione um banco de dados Postgres na Render e configure as variáveis de ambiente conforme necessário.
**URL deploy backend Render**: https://dev-elite-backend-java.onrender.com
**URL interface frontend vercel:** https://dev-elite-front.vercel.app/
