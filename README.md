# 📊 Projeto: Campo Society

Este repositório contém o desenvolvimento do projeto MVC Java com Banco de dados para a disciplina POO 
do curso TADS - UFRN.

---

## 📖 Descrição

O projeto teve como proposito consolidar e fechar os estudos e conhecimentos adquiridos em POO.
Foi desenvolvido uma aplicação Desktop em java para gerenciamento de um campo society, o mesmo inclui.

Login/Sign de usuarios, Agenda de Horarios, Sistema de Admin Para controlar os agendamentos e etc.

---

## 🛠️ Tecnologias Utilizadas

- **Linguagem de Programação:** [Java]
- **Banco de Dados:** [PostgreSQL]
- **Ferramentas:** [Visual Studio code, Beekeeper Studio]

---

## 📂 Estrutura do Projeto

1. **Frontend:** Diretório contendo o código relacionado à interface do usuário.
2. **Backend:** Diretório responsável pelo processamento e regras de negócio.
3. **Banco de Dados:** Arquivos SQL ou scripts de criação de tabelas.

---

## 📜 Diagramas

### 1️⃣ Diagrama de Classes
> O diagrama de classes descreve a estrutura do projeto e a interação entre as entidades do sistema.
- Controllers.
  
  ![image](https://github.com/user-attachments/assets/edb981cd-0328-4337-9b4a-76d8dacd3e47)

- Models.
  
  ![image](https://github.com/user-attachments/assets/c032520c-18b1-4e6c-9073-349e2da485b9)



---

### 2️⃣ Diagrama Entidade-Relacionamento (DER)
> O DER representa a estrutura do banco de dados e as relações entre tabelas.

**Imagem:**
![image](https://github.com/user-attachments/assets/00541487-b48e-41ea-991b-87dc0646a070)

---

## 📋 Dicionário de Dados


### Tabela: **dias_semana**
| Coluna     | Tipo         | Tamanho   | Restrição           | Descrição                   |
|------------|--------------|-----------|---------------------|-----------------------------|
| id         | serial       | -         | PK (Chave Primária) | Identificador único do dia. |
| nome_dia   | character    | -         | NOT NULL            | Nome do dia da semana.      |

### Tabela: **horarios**
| Coluna     | Tipo         | Tamanho   | Restrição           | Descrição                   |
|------------|--------------|-----------|---------------------|-----------------------------|
| id         | serial       | -         | PK (Chave Primária) | Identificador único do horário. |
| hora       | time         | -         | NOT NULL            | Hora do agendamento.        |

### Tabela: **usuarios**
| Coluna     | Tipo         | Tamanho   | Restrição           | Descrição                   |
|------------|--------------|-----------|---------------------|-----------------------------|
| id         | serial       | -         | PK (Chave Primária) | Identificador único do usuário. |
| nome       | character    | -         | NOT NULL            | Nome do usuário.            |
| cpf        | bigint       | -         | UNIQUE, NOT NULL    | CPF do usuário (valor único). |
| senha      | character    | -         | NOT NULL            | Senha para login do usuário. |

### Tabela: **agendamentos**
| Coluna        | Tipo         | Tamanho   | Restrição           | Descrição                               |
|---------------|--------------|-----------|---------------------|-----------------------------------------|
| id            | serial       | -         | PK (Chave Primária) | Identificador único do agendamento.     |
| id_dia        | integer      | -         | FK (dias_semana.id) | Referência ao dia da semana.            |
| id_horario    | integer      | -         | FK (horarios.id)    | Referência ao horário do agendamento.   |
| vago          | boolean      | -         | NOT NULL            | Indica se o horário está disponível.    |
| agendado_por  | integer      | -         | FK (usuarios.id)    | Referência ao usuário que agendou.      |




