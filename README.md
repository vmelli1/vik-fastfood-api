# 🚀 FoodFlow - SaaS de Cardápio Digital & Gestão de Pedidos

O **FoodFlow** é uma solução robusta voltada para redes de fast food, permitindo que estabelecimentos gerenciem seus cardápios de forma independente e recebam pedidos de forma ágil. O projeto foi concebido com foco em **escalabilidade, segurança e manutenibilidade**.

---

## 🏗️ Arquitetura e Engenharia de Software

Diferente de projetos convencionais, o FoodFlow aplica padrões de mercado para garantir uma base sólida e preparada para crescimento:

* **Arquitetura Multi-tenant**
  Separação lógica de dados, garantindo que cada estabelecimento acesse apenas suas próprias informações.

* **Design Patterns Aplicados**

    * **Builder Pattern** → Criação de entidades complexas (Pedidos, Produtos) com mais clareza e imutabilidade
    * **Chain of Responsibility** → Validação modular de regras de negócio (cadastros e pedidos)
    * **DTO (Data Transfer Object)** → Proteção das entidades do banco, expondo apenas o necessário
    * **Strategy** → Flexibilidade para múltiplas formas de pagamento e cálculo de frete

---

## 🛠️ Tecnologias Utilizadas

* **Backend:** Java 17, Spring Boot 3
* **Banco de Dados:** MySQL (Hibernate / JPA)
* **Segurança:** Spring Security + BCrypt
* **Produtividade:** Lombok & SLF4J (logs e auditoria)
* **Frontend:** Vite + React *(em planejamento)*

---

## 📋 Status do Projeto (Roadmap)

* ✅ Módulo de Usuários e Segurança
  Cadastro de estabelecimentos, primeiro acesso e criptografia

* ✅ Gestão de Cardápio (Admin)
  CRUD completo de categorias com validação de integridade

* 🚧 Módulo de Produtos
  Cadastro detalhado com ficha técnica e precificação

* 🚧 Fluxo de Pedidos (Checkout)
  Carrinho de compras e envio de pedidos via WhatsApp

* 🔜 Painel de Controle
  Dashboard de vendas e lucratividade

---

## 🚀 Como rodar o projeto

1. Clone o repositório
2. Certifique-se de que o MySQL está em execução
3. Configure o arquivo `application.properties`
4. Execute o build:

   ```bash
   mvn clean install
   ```
5. Inicie a aplicação:

   ```bash
   mvn spring-boot:run
   ```

---

## ⚠️ Aviso Legal

Este projeto está **em fase de desenvolvimento**.

O uso, cópia, modificação ou redistribuição deste código **não é autorizado**, exceto para fins de estudo e exemplos, conforme legislação aplicável.
Qualquer outro tipo de utilização requer autorização prévia do autor.

---

## 📌 Observações

O FoodFlow foi projetado como um SaaS escalável, com foco em boas práticas de engenharia de software, podendo evoluir para suportar múltiplos estabelecimentos em produção real.
