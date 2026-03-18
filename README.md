# 📡 Projeto de Comunicação com Sockets em Java

## 🧠 Resumo do Projeto

O objetivo desta atividade é implementar e analisar diferentes modelos de comunicação em rede utilizando **Sockets em Java**.

Foram desenvolvidas três versões do sistema:

* Comunicação **UDP**
* Comunicação **TCP Sequencial**
* Comunicação **TCP Concorrente (Multithread)**

Em todas as versões, o cliente envia ao servidor uma **região geográfica (ZoneId)** e o servidor retorna o **horário atual daquela região**.

A atividade tem como finalidade compreender:

* Diferença entre protocolos **TCP e UDP**
* Funcionamento de **Sockets**
* Conceito de **bloqueio de conexão**
* Importância da **concorrência em servidores**
* Impacto na **performance e escalabilidade**

---

## ⚙️ Instruções de Execução

### ✅ Verificar versão do Java

No terminal:

```bash
java -version
javac -version
```

⚠️ Ambas devem estar na **mesma versão**.

---

## 🔨 Compilação do Projeto (obrigatória)

Na **raiz da pasta `Atividade-Soquete`** executar:

```bash
javac --release (VersãO do java) -d . src/main/java/org/example/tcp_clock_multithread/*.java
```

---

## 🌐 Execução — Versão UDP

### ▶️ Rodar o servidor

```bash
java org.example.udp_clock.ServidorUDP
```

### ▶️ Rodar o cliente (em outro terminal)

```bash
java org.example.udp_clock.ClienteUDP
```

Digite uma região por exemplo:

```text
America/Sao_Paulo
```

---

## 🔵 Execução — Versão TCP Sequencial

### ▶️ Rodar o servidor

```bash
java org.example.tcp_clock_simple.ServidorTCP
```

### ▶️ Rodar o cliente (em outro terminal)

```bash
java org.example.tcp_clock_simple.ClienteTCP
```

Nesta versão, o servidor atende **um cliente por vez**.

Digite uma região por exemplo:

```text
America/Sao_Paulo
```

---

## 🔥 Execução — Versão TCP Concorrente (Multithread)

### ▶️ Rodar o servidor

```bash
java org.example.tcp_clock_multithread.ServidorTCP
```

### ▶️ Rodar múltiplos clientes simultaneamente (PowerShell)

```bash
for ($i=0; $i -lt 5; $i++) {
    Start-Process powershell -ArgumentList "java org.example.tcp_clock_multithread.ClienteTCP"
}
```

Este comando abre **5 clientes simultâneos**, permitindo observar o comportamento concorrente do servidor.

---

## 📊 Análise Técnica — Performance

Na **Versão TCP Sequencial**, o servidor atende os clientes de forma **bloqueante**, ou seja, enquanto está processando uma requisição, não pode aceitar novas conexões.

Isso gera:

* Aumento do tempo de espera
* Formação de fila de clientes
* Baixa escalabilidade
* Subutilização de recursos do processador

Já na **Versão TCP Multithread**, o servidor cria uma **nova Thread para cada cliente conectado**, permitindo que múltiplas requisições sejam processadas simultaneamente.

Como consequência:

* Redução do tempo de resposta quando vários clientes conectam ao mesmo tempo
* Melhor aproveitamento da CPU
* Maior escalabilidade do sistema
* Atendimento concorrente de conexões

Durante os testes, foi possível observar que, ao simular múltiplos clientes simultâneos, o servidor multithread respondeu praticamente no mesmo intervalo de tempo para todos, enquanto o servidor sequencial respondeu em ordem de chegada, aumentando o tempo total de atendimento.

---

## 👨‍💻 Autor

José Victor
Curso: Sistemas de Informação
Disciplina: Sistemas Distribuídos 
