# 🐶 API de Adoção de Pets

Sistema backend desenvolvido com Spring Boot para gerenciar um processo completo de adoção de animais, incluindo cadastro de pets, pessoas interessadas, endereços e fluxo de interesse de adoção.

## 🚀 Sobre o Projeto

A API de Adoção de Pets foi criada com o objetivo de simular um sistema real de gestão de adoção, permitindo:

- **Cadastro e consulta de animais disponíveis**
- **Gerenciamento de pessoas interessadas**
- **Controle de características dos pets**
- **Registro de interesse de adoção**
- **Filtros avançados de busca**
- **Tratamento robusto de erros**

#### O projeto segue boas práticas de arquitetura em camadas:

`Controller → Service → Repository → Domain`

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Jakarta Validation
- Lombok
- Banco de Dados Relacional (ex: PostgreSQL ou H2)
- Swagger / openAPI

## 📁 Estrutura do Projeto

```bash
src/main/java/org/serratec/adocao_pets
├── controller      # Camada de entrada (REST)
├── domain          # Entidades do sistema
├── dto             # Objetos de transferência (Request/Response)
├── enumerated      # ENUMs com validação customizada
├── exception       # Tratamento global de erros
├── repository      # Acesso ao banco (JPA)
├── service         # Regras de negócio
```

---

## 🔎 Funcionalidades

#### 🐾 Animais

- Cadastro de animal
- Listagem geral
- Busca por:
  - Nome
  - Espécie
  - Tamanho
  - Sexo
  - Status de adoção
  - Listagem de características do animal

#### 👤 Pessoas

- Cadastro de pessoas
- Associação com endereço
- Atualização e exclusão

#### 📍 Endereços

- Cadastro e busca por:
- Rua
- Bairro
- Cidade
- Estado
- CEP

#### ⭐ Características

- Cadastro de características (ex: dócil, vacinado, castrado)

#### ❤️ Interesse de Adoção

- Registro de interesse (Pessoa + Animal)
- Controle de status:
- PENDENTE
- APROVADO
- REJEITADO
- CANCELADO

---

## 🔄 Relacionamentos

- Um **Animal** possui várias **Características**
- Uma **Pessoa** possui um **Endereço**
- O **Interesse de Adoção** conecta:
  - Pessoa ↔ Animal _(chave composta)_

### 📌 Exemplos de Endpoints

#### 🐶 Animais :

```bash
GET /animais
GET /animais/{id}
GET /animais/nome/{nome}
GET /animais/especie/{especie}
GET /animais/tamanho/{tamanho}
GET /animais/sexo/{sexo}
GET /animais/adocao/{status}
```

```json
[
	{
		"id": 1,
		"nome": "Max",
		"mesesVida": 24,
		"especie": "CACHORRO",
		"tamanho": "MEDIO",
		"sexo": "MACHO",
		"descricao": "Cão super ativo, ideal para quem gosta de correr.",
		"caracteristicas": [
			{
				"id": 1,
				"descricao": "Porte Pequeno"
			},
			{
				"id": 10,
				"descricao": "Vacinado"
			},
			{
				"id": 4,
				"descricao": "Muito Brincalhão"
			}
		],
		"statusAdocao": "DISPONIVEL"
	}
  ...
]
```

```bash
POST /animais/salvar
POST /animais/salvar-lista
```

```json
{
  "nome": "Luna",
  "especie": "GATO",
  "sexo": "FEMEA",
  "mesesVida": 12,
  "tamanho": "PEQUENO",
  "statusAdocao": "DISPONIVEL",
  "descricao": "Mia muito quando está com fome",
  "caracteristicas": [1, 5, 9]
}
```

```bash
PUT /animais/atualizar/{id}
```

```json
{
  "nome": "Luna",
  "especie": "GATO",
  "sexo": "FEMEA",
  "mesesVida": 13,
  "tamanho": "PEQUENO",
  "caracteristicas": [1]
}
```

```bash
DELETE /animais/excluir/{id}
```

#### 👤 Pessoas :

```bash
GET /pessoas
GET /pessoas/{id}
```

```json
{
  "id": 10,
  "cpf": "620.550.560-69",
  "nome": "Beatriz Gomez",
  "telefone": "(24) 99111-2233",
  "email": "beatriz@email.com",
  "endereco": {
    "id": 9,
    "rua": "Rua Coronel Veiga",
    "numero": "550",
    "bairro": "Coronel Veiga",
    "cidade": "Petrópolis",
    "estado": "RJ",
    "cep": "25655-003"
  }
}
```

```bash
POST /pessoas
```

```json
{
  "nome": "Lucas Leal da Silva",
  "email": "lucasleal@gmail.com",
  "cpf": "340.034.040-50",
  "telefone": "(24) 99999-9999",
  "endereco": {
    "id": 1
  }
}
```

```bash
PUT /pessoas/{id}
```

```json
{
  "cpf": "896.670.100-07",
  "nome": "Lucas da Silva",
  "telefone": "(00) 99999-9999",
  "email": "lucasleal00100@gmail.com",
  "endereco": {
    "id": 1,
    "rua": "São Paulo maranhão",
    "numero": "N/A",
    "bairro": "Corrêas",
    "cidade": "Petrópolis",
    "estado": "RJ",
    "cep": "25620-000"
  }
}
```

```bash
DELETE /pessoas/{id}
```

#### ❤️ Interesse de Adoção :

```bash
GET /interesses-adocoes
GET /interesses-adocoes/pessoa/{pessoaId}/animal/{animalId}
```

```json
{
  "dataPedido": "2026-05-20T12:03:41.074",
  "statusProcesso": "APROVADO",
  "observacoes": "Demorou para conseguir",
  "pessoa": {
    "nome": "Carlos Silva",
    "telefone": "(24) 99123-4567",
    "email": "carlos@email.com"
  },
  "animal": {
    "nome": "Pipoca",
    "mesesVida": 10,
    "especie": "CACHORRO",
    "tamanho": "PEQUENO",
    "sexo": "FEMEA",
    "descricao": "Pequena e elétrica, adora passear na coleira.",
    "statusAdocao": "DISPONIVEL"
  },
  "id": {
    "pessoaId": 1,
    "animalId": 9
  }
}
```

```bash
POST /interesses-adocoes/salvar
```

```json
[
  {
    "pessoaId": 1,
    "animalId": 9,
    "observacoes": "Possui quintal grande e coberto.",
    "dataPedido": "2026-05-15T09:00:00",
    "statusProcesso": "PENDENTE"
  }
  ...
]
```

```bash
PUT /interesses-adocoes/pessoa/{pessoaId}/animal/{animalId}
```

```json
{
  "dataPedido": "2026-05-20T12:03:41.074Z",
  "statusProcesso": "APROVADO",
  "observacoes": "Demorou para conseguir"
}
```

### ⚠️ Tratamento de Erros

A API possui tratamento global com @ControllerAdvice, retornando respostas padronizadas:

📌 Exemplo de erro:

```json
{
  "status": 400,
  "titulo": "ENUM inválido, valor de campo incorreto!",
  "dataHora": "2026-05-20T14:30:00",
  "erros": ["Espécie inválida. Valores válidos: CACHORRO OU GATO"]
}
```

#### Tipos tratados:

- ❌ Validação de campos (@Valid)
- ❌ ENUM inválido
- ❌ JSON mal formatado
- ❌ Recurso não encontrado (404)
- ❌ Erro interno (500)

---

## 📦 Como Executar

#### 1. Clone o repositório

```bash
git clone https://github.com/seu-repo/adocao-pets.git
```

#### 2. Acesse o projeto

```bash
cd adocao-pets
```

#### 3. Execute a aplicação

```bash
./mvnw spring-boot:run
```

## 📬 Testes da API

**Você pode testar utilizando:**

- Postman
- Insomnia
- Thunder Client (VS Code)

## Arquivos de exemplo para importação

- [enderecos.json](./arquivos_json/enderecos.json)
- [pessoas.json](./arquivos_json/pessoas.json)
- [caracteristicas_animais.json](./arquivos_json/caracteristicas_animais.json)
- [animais.json](./arquivos_json/animais.json)
- [interesses_adocoes.json](./arquivos_json/interesses_adocoes.json)

---

# 👨‍💻 Autor

- Desenvolvido por [Lucas Leal](https://github.com/Phonedison)
