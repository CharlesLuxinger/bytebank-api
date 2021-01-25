![Continuos Integration](https://github.com/CharlesLuxinger/bytebank-api/workflows/Continuos%20Integration/badge.svg)
# BYTEBANK API
Aplicação para criação de contas bancárias e realização de depósitos e transferência entre contas com base no identificador da conta do usuário.

## Repositórios
- [Github](https://github.com/CharlesLuxinger/bytebank-api)
- [Docker Hub](https://hub.docker.com/r/charlesluxinger/bytebank-api)

## Dependências
- [Maven 3.6.3](https://maven.apache.org/download.cgi)
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Git & Bash](https://git-scm.com/downloads)
- [Docker](https://www.docker.com/products/docker-desktop) & [Docker Compose](https://docs.docker.com/compose/install/)
- [curl](https://curl.se/)
## Instruções
#### - Subindo a api em Docker
1) Através de um terminal clone o projeto e acessa a pasta docker-local do projeto:
    ```shell
    $ git clone git@github.com:CharlesLuxinger/bytebank-api.git
    $ cd bytebank-api/docker-local
    ```
2) Iniciei a aplicação e o database com:
    ```shell
    $ bash startup-api.sh
    ```
#### - Subindo api através da IDE
1) Caso não possua o clone do projeto realize o 1º passo acima
2) Inicie o database com:
    ```shell
    $ bash startup-database.sh
    ```
3) As variáveis de ambiente necessárias para conexão com ao database estão no arquivo `.env`na raiz do projeto
___
### Utilizando a aplicação via swagger
- Acesse em um browser a url `http://localhost:9000/api/v1`
---
### Utilizando a aplicação via curl:
- Para criar uma nova conta:
    ```shell
    curl -X POST "http://localhost:9000/api/v1/account" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"ownerName\":\"João Manuel\",\"document\":\"999.999.999-99\"}"
    ```
  Body:
    ```json
      {
          "ownerName": "João Manuel",
          "document": "999.999.999-99"
      }
    ```
  Response:
    ```json
      {
          "id": "507f1f77bcf86cd799439011",
          "ownerName": "João Manuel",
          "document": "999.999.999-99"
      }
    ```
- Para realizar um depósito(Se faz necessário inserir o ID no parâmetro do path `{ID}` corresponde na URL abaixo):
    ```shell
    $ curl -X POST "http://localhost:9000/api/v1/account/{ID}" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"value\":999}"
    ```
  Body:
    ```json
      {
        "value": 999
      }
    ```
- Para realizar uma transferência
    - Se faz necessário criar 2 contas e fazer ao menos 1 depósito na conta que irá fazer a transferências 
    - O atributo do payload `{sourceAccountId}` corresponde ao ID da conta que irá realizar a transferência.
    - O atributo do payload `{targetAccountId}` corresponde ao ID da conta que irá receber a transferência.

    ```shell
      curl -X PATCH "http://localhost:9000/api/v1/account" -H  "accept: */*" -H  "Content-Type: application/json" -d "{\"sourceAccountId\":\"{sourceAccountId}\",\"targetAccountId\":\"{targetAccountId}\",\"value\":999}"
    ```
  Body:
    ```json
      {
          "sourceAccountId": "507f1f77bcf86cd799439011",
          "targetAccountId": "507f1f77bcf86cd799439012",
          "value": 999
      }
    ```
---
## Testes
- Acesse a raiz do projeto através do terminal:
    ```shell
    $ mvn clean test -U
    ```
---
## Health Check
- Acesse em um browser a url `http://localhost:9000/api/v1/health`
---
## Disponibilizando a API ao público
### Database
- Se faz necessário disponibilizar acesso a um database MongoDB com as seguintes configurações realizadas:
1) Criar database `bytebank`
1) Criar collection `account`
2) Criar o index:
    ```
    db.account.createIndex({"document":1}, { unique: true })
   ```
### API
- Para realizar deploy da aplicação em um cloud provider podemos utilizar por exemplo a AWS Cold Build, realizando um clone do projeto e utilizar os arquivos `docker-compose.yml` e `Dockerfile` na raiz do projeto, considerando que o arquivo `.env` deva possuir as variáveis de acesso ao database.