# Casos de Uso - SafeHer

## UC01 - Cadastro de Usuária

### Objetivo

Permitir que uma nova usuária crie uma conta na plataforma.

### Atores

* Usuária

### Pré-condições

* A usuária não possui cadastro ativo.

### Fluxo Principal

1. A usuária acessa a tela de cadastro.
2. Informa nome, e-mail e senha.
3. O sistema valida os dados.
4. O cadastro é realizado.
5. A usuária é redirecionada para a página inicial.

### Fluxos Alternativos

* E-mail já cadastrado.
* Campos obrigatórios não preenchidos.

### Pós-condições

* Conta criada com sucesso.

---

## UC02 - Login

### Objetivo

Permitir autenticação na plataforma.

### Atores

* Usuária

### Fluxo Principal

1. Informa e-mail e senha.
2. Sistema valida credenciais.
3. Acesso liberado.

### Pós-condições

* Sessão iniciada.

---

## UC03 - Registrar Denúncia

### Objetivo

Permitir o envio de denúncias anônimas.

### Atores

* Usuária

### Fluxo Principal

1. Acessa o formulário.
2. Preenche os dados.
3. Anexa evidências.
4. Envia denúncia.
5. Sistema registra ocorrência.

### Pós-condições

* Denúncia armazenada.
