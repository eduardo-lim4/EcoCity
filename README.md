# 🌱 EcoCity

EcoCity é um jogo desenvolvido em **Java** com foco em **educação ambiental**, onde o jogador assume o papel de gestor de uma cidade e precisa equilibrar crescimento econômico e sustentabilidade.

---

## 🎮 Sobre o jogo

No EcoCity, você toma decisões estratégicas que impactam diretamente:

* 🏙️ População
* 💰 Dinheiro
* 🌫️ Poluição
* 🌿 Meio ambiente

Cada escolha traz consequências — crescer rápido pode destruir o ambiente, enquanto preservar demais pode limitar o desenvolvimento.

---

## 🧠 Objetivo

Alcançar 100 anos mantendo o equilíbrio da cidade.

Você perde se:

* ❌ Poluição chegar a 100%
* ❌ Meio ambiente chegar a 0%

---

## ⚙️ Mecânicas do jogo

### 🏗️ Ações disponíveis

* Construir indústria → 💰 + dinheiro | 🌫️ + poluição
* Expandir mineração → 💰 + dinheiro | 🌫️ + poluição
* Construir rodovia → 👥 + população | 🌫️ + poluição
* Plantar árvores → 🌿 + meio ambiente | 💸 custo
* Construir parque → 🌿 + meio ambiente | 👥 + população
* Energia solar → 🌿 + meio ambiente | 🌫️ - poluição

---

### 📈 Sistema de progressão

* A cada **10 anos**, os eventos ficam mais difíceis
* O jogo exige cada vez mais estratégia

---

### 🧮 Sistema de pontuação

A pontuação considera:

* Tempo sobrevivido (anos)
* Nível de meio ambiente
* Nível de poluição
* População
* Dinheiro acumulado

---

## 🧪 Tutorial interativo

O jogo conta com um sistema de tutorial em etapas que ensina:

* Como as ações afetam a cidade
* Como equilibrar economia e sustentabilidade
* Condições de vitória e derrota

---

## 🎨 Interface

* Interface gráfica com **Java Swing**
* HUD com transparência e design moderno
* Barras animadas com feedback visual
* Sistema de imagens dinâmicas conforme o estado da cidade

---

## 🚀 Tecnologias utilizadas

* Java 17+
* Java Swing (GUI)
* Maven

---

## ▶️ Como executar o projeto

### Pré-requisitos

* Java JDK 17 ou superior
* Maven instalado

### Passos

```bash
mvn clean package
```

Depois execute:

```bash
java -cp target/classes com.ecocity.Main
```

---

## 📁 Estrutura do projeto

```
src/
 └── main/
     ├── java/com/ecocity/
     │    ├── Main.java
     │    ├── Game.java
     │    ├── Cidade.java
     │    ├── Evento.java
     │    ├── Tutorial.java
     │    ├── AudioPlayer.java
     │    └── UI components
     └── resources/
```

---

## 🛠️ Melhorias implementadas

* Correção de bugs críticos
* Sistema de dificuldade progressiva
* Refatoração (DRY)
* Interface modernizada
* Sistema de tutorial
* Cache de imagens
* Controle de estados (dinheiro, população, etc)

---

## 💡 Algumas melhorias futuras

* 🏆 Sistema de ranking (leaderboard)
* 🌐 Ranking online
* 🎵 Sistema de áudio mais avançado
* 💾 Sistema de save/load
* 🎮 Modos de jogo (desafio, infinito)

---


## 📜 Licença

Este projeto é para fins educacionais.
