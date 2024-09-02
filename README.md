# **Backend da Aplicação de Filmes**<br>
Este é o backend de uma aplicação de filmes desenvolvida em Java usando Spring Boot, Hibernate, e Postgres. A aplicação consome dados da API do The Movie Database (TMDb) e fornece uma interface para um frontend React, permitindo salvar, listar e apagar filmes favoritos.<br>
Aplicação foi testada com JUnit para previnir erros e bugs.<br>
A aplicação está hospedada na Render e utiliza um banco de dados Postgres.

# **Tecnologias Utilizadas**<br>
**Java**: Linguagem de programação principal da aplicação. <br>
**Spring Boot**: Framework para desenvolvimento rápido e fácil de aplicações Java, com suporte a REST APIs.<br>
**Hibernate**: Framework de mapeamento objeto-relacional usado para interagir com o banco de dados.<br>
**PostgreSQL**: Banco de dados relacional usado para armazenar informações sobre filmes e usuários.<br>
**JUnit:** aplicação feita e testada para evitar bugs e erros.<br>
**API TMDb**: API externa utilizada para obter informações detalhadas sobre filmes.<br>
**Render**: Plataforma de hospedagem onde o backend está implementado.<br>
# **Funcionalidades**<br>
**Buscar Filmes**: A aplicação consome dados da API TMDb para buscar filmes, exibir detalhes e permitir que o usuário adicione filmes aos seus favoritos.<br><br>

# **Gerenciar Favoritos**:<br>

**Salvar Favoritos** Os usuários podem adicionar filmes à sua lista de favoritos. Os favoritos são salvos no banco de dados Postgres.
**Apagar Favoritos**: Os usuários podem remover filmes da lista de favoritos.
**Listar Favoritos**: A aplicação permite que os usuários vejam todos os filmes que foram adicionados como favoritos.
# **Compartilhar Lista de Favoritos**:

**A aplicação fornece uma URL fixa** (https://dev-elite-front.vercel.app/favorites) que pode ser compartilhada para acessar a lista de filmes favoritos diretamente no frontend.<br>
# **Como Configurar e Rodar a Aplicação**<br>
# **Pré-requisitos**<br>
Java 17 ou superior instalado.<br>
Maven para gerenciar dependências e construir o projeto.<br>
PostgreSQL para o banco de dados.<br>
Conta na Render para deploy, ou execute localmente.<br>
Credenciais da API TMDb para acessar os dados dos filmes.<br>
Passos para Configuração<br>
**Clone o Repositório**:<br>
**git clone** https://github.com/Biazindev/dev-elite-backend-java.git <br><br>

#**Configurar o Banco de Dados**:<br>

**Crie um banco de dados Postgres para a aplicação**.<br>

**Atualize as credenciais do banco no arquivo application.properties**:<br>
spring.datasource.url=jdbc:postgresql://localhost:5432/seu-banco<br>
spring.datasource.username=seu-usuario<br>
spring.datasource.password=sua-senha<br>
spring.jpa.hibernate.ddl-auto=update<br>

# **Configurar a API TMDb**:<br>
 Adicione a chave da API TMDb no application.properties:
tmdb.api.key=sua-chave-tmdb<br>

# **Rodar o Projeto**:<br>
**Compile e rode o projeto com Maven**:<br>
mvn clean install<br>
mvn spring-boot:run<br>
**A aplicação estará disponível em** http://localhost:8080.<br>

# **Endpoints Principais**<br>
Buscar Filmes
GET /api/movies?query={nome-do-filme} - Busca filmes pelo nome.<br>
Gerenciar Favoritos:<br>

POST /api/favorites - Adiciona um filme aos favoritos. Requer o ID do filme do TMDb.<br>
GET /api/favorites - Lista todos os filmes favoritos.<br>
DELETE /api/favorites/{id} - Remove um filme dos favoritos pelo ID.<br>
Compartilhar Favoritos:<br>

**A URL para acessar a lista de favoritos no frontend é https://dev-elite-front.vercel.app/favorites**.<br>

# **Deploy na Render**<br>
**Configuração do Serviço**:<br>

Crie um novo serviço Web na Render.<br>
Conecte o repositório Git.<br>
Configure o build command como ./mvnw clean install e o start command como java -jar target/nome-do-seu-app.jar.<br>
Defina as variáveis de ambiente para conectar com o banco de dados e API TMDb.<br>

# **Banco de Dados Postgres:**<br>

Adicione um banco de dados Postgres na Render e configure as variáveis de ambiente conforme necessário.<br>
**URL deploy backend Render**: https://dev-elite-backend-java.onrender.com<br>
**URL interface frontend vercel:** https://dev-elite-front.vercel.app/<br>
