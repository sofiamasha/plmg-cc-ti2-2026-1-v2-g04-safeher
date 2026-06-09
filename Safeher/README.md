# 🌸💻 Estrutura de Código do Projeto

Esta seção apresenta a organização dos arquivos, diretórios e recursos que compõem a aplicação **SafeHer**. A estrutura foi planejada para facilitar a manutenção, evolução e compreensão do sistema, separando os componentes de banco de dados, interface do usuário e recursos visuais utilizados pela plataforma.

---

## 📁 Organização Geral do Projeto

O projeto está estruturado em módulos que representam as principais camadas da aplicação. Cada diretório possui uma responsabilidade específica, contribuindo para a organização do código e para a padronização do desenvolvimento.

---

## 🌷 Banco de Dados

Responsável pelo armazenamento persistente das informações da aplicação, incluindo dados de usuárias, empresas, avaliações, denúncias e demais entidades necessárias para o funcionamento da plataforma.

Os scripts contidos nesta pasta permitem a criação da estrutura do banco de dados e a configuração inicial do ambiente de persistência.

📌 Scripts utilizados para criação e manipulação do banco de dados.

* 🔗 [safeher.sql](./Codigo/Banco%20de%20Dados/safeher.sql)

---

## 🌼 Front-End

O módulo de Front-End concentra as interfaces responsáveis pela interação entre usuários e sistema. As páginas foram desenvolvidas para proporcionar uma navegação intuitiva, organizada e compatível com os principais fluxos da aplicação.

Cada arquivo representa uma funcionalidade específica disponibilizada pela plataforma.

### Páginas Disponíveis

* 🔗 API: [api.html](./Codigo/Front-End/api.html)
* 🔗 Avaliação: [avaliacao.html](./Codigo/Front-End/avaliacao.html)
* 🔗 Avaliações: [avaliacoes.html](./Codigo/Front-End/avaliacoes.html)
* 🔗 Cadastro de Empresa: [cadastro-empresa.html](./Codigo/Front-End/cadastro-empresa.html)
* 🔗 Cadastro de Mulher: [cadastro-mulher.html](./Codigo/Front-End/cadastro-mulher.html)
* 🔗 Conteúdos: [conteudos.html](./Codigo/Front-End/conteudos.html)
* 🔗 Denúncias: [denuncias.html](./Codigo/Front-End/denuncias.html)
* 🔗 Escolha de Cadastro: [escolha-cadastro.html](./Codigo/Front-End/escolha-cadastro.html)
* 🔗 Página Inicial: [index.html](./Codigo/Front-End/index.html)
* 🔗 Login: [login.html](./Codigo/Front-End/login.html)
* 🔗 Vagas: [vagas.html](./Codigo/Front-End/vagas.html)

### Principais Funcionalidades da Interface

* Cadastro e autenticação de usuários;
* Cadastro e gerenciamento de empresas;
* Consulta de conteúdos informativos;
* Registro e acompanhamento de denúncias;
* Visualização e publicação de avaliações;
* Navegação entre páginas institucionais e operacionais;
* Integração com recursos e serviços externos.

---

## 🎨 Estilização

Os arquivos de estilização são responsáveis pela identidade visual da plataforma, garantindo consistência gráfica, padronização de componentes e melhor experiência de navegação para os usuários.

📌 Arquivos de estilo da aplicação.

* 🔗 [global.css](./Codigo/Front-End/global.css)

---

## 🖼️ Recursos Visuais

Esta seção reúne os elementos gráficos utilizados ao longo da interface. Esses recursos contribuem para a comunicação visual do sistema, reforçando a identidade da plataforma e melhorando a experiência dos usuários.

📌 Imagens utilizadas na interface do sistema.

* Buildings Illustration
* Home Illustration
* Login Illustration
* Logo Escudo
* Reporting Illustration
* Artigos de Direitos
* Woman Illustration

---

## 🌺🧭 Visão Geral da Estrutura

A representação abaixo apresenta a organização simplificada dos diretórios e arquivos que compõem a aplicação.

```text
Codigo/
│
├── Banco de Dados/
│   └── safeher.sql
│
└── Front-End/
    ├── resources/
    │   └── images/
    ├── api.html
    ├── avaliacao.html
    ├── avaliacoes.html
    ├── cadastro-empresa.html
    ├── cadastro-mulher.html
    ├── conteudos.html
    ├── denuncias.html
    ├── escolha-cadastro.html
    ├── global.css
    ├── index.html
    ├── login.html
    └── vagas.html
```

## 📌 Considerações Finais

A estrutura apresentada reflete a organização adotada durante o desenvolvimento do projeto SafeHer, buscando promover clareza, modularização e facilidade de manutenção. A separação entre banco de dados, interface e recursos auxilia na evolução contínua da solução e favorece futuras expansões do sistema.
