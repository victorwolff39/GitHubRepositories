# GitHubRepositories
Simple app made in Kotlin that lists GitHub projects also made in Kotlin

## Instalação

Para executar o projeto é necessário abri-lo com o [Android Studio](https://developer.android.com/studio/install).

Para isso
- Clone o repositório em algum diretório ou [importe-o](https://developer.android.com/studio/projects/create-project#ImportAProject) no Android Studuio.
- Execute o aplicativo em um [dispositivo físico](https://developer.android.com/studio/run/device) ou [emulador](https://developer.android.com/studio/run/emulator)

## GitHub API

A API do GitHub utilizada nesse projeto tem [rate limit](https://docs.github.com/pt/enterprise-cloud@latest/rest/rate-limit), e para usuários não autenticados é muito fácil atingir o limite de requisições.
Caso o limite seja atingido, não será possível obter os repositórios.

No caso dos limites para a api de [usuários](https://docs.github.com/en/enterprise-cloud@latest/rest/search#get-a-user) o limite é bem mais baixo. Por esse motivo, existe a opção de realizar requests autenticados utilizando um personal access token.
Esse token pode ser criado seguindo [estas instruções](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)

###### Utilizando o personal access token:
Por motivos óbvios, esse token não deve ser divulgado, portanto, ao gerar seu personal access token insira-o no arquivo [config.properties](https://github.com/victorwolff39/GitHubRepositories/blob/master/app/src/main/res/raw/config.properties) presente em `GitHubRepositories/app/src/main/res/raw/config.properties`.

Basta substituir `YOUR_GITHUB_PERSONAL_ACCESS_TOKEN` por seu access token:
```diff
- github_key=YOUR_GITHUB_PERSONAL_ACCESS_TOKEN
+ github_key=asdfhjkahkjhjkadshfhjkfhkjdhjf
```

