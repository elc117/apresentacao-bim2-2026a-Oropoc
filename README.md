# Apresentação João Daniel 
#
## Primeira Parte A
- Compilação exercício "TransaçõesBancarias.java":

  Não consegui usar o bash mas eu fui compilando e não demorou muito para dar um resultado diferente;

  <img width="1078" height="310" alt="gravação paradigmas 2 0" src="https://github.com/user-attachments/assets/e4d7409e-cc2c-4586-af4d-82c704b5b4a6" />

  O erro acontece porque o as trheads acessam e modificam os dados ao mesmo tempo (sem sincronização)
#
  
- Correção do problema:
  
  Primeiro tenho que colocar o "synchronized" nos métodos da conta, isso deixa o método bloqueado para outra thread enquanto uma ainda está usando ele.

```JAVA
public synchronized void deposita(float valor) {
    saldo += valor;
}

public synchronized void retira(float valor) {
    saldo -= valor;
}
```

- Porque foram esses métodos?

  O erro estava sendo no valor final do saldo, então teria que ser em algum método que mexia com isso, acontece que o programa roda duas threads que ficavam repetidamente acessando, depositando ou retirando e atualizando o código, e sempre a última atualização que contava para o número final da conta, então poderia dar que enquanto a thread 1 estava acessando o saldo ele já poderia estar desatualizado por causa da ação da thread 1, perdendo assim uma ação da Thread 1, interferindo no valor final.

#
# Primeira Parte B
- Análise do código "SharedCounter.java":

  O código tem três objetos, um que vai acrescentando de 1 em 1, outro que é a Thread e que delimita quanto que vai ser a soma e outro que é o objeto que está a main, ele cria uma "conta" e cria dois Threads para irem adicionando até 1000 vezes, o resultado então seria a conta com 2000 (1000 + 1000).
#
- Compilando:

<img width="1090" height="316" alt="gravação 2 1" src="https://github.com/user-attachments/assets/1fa6f28d-d6bf-42c1-91c2-5da216d868b4" /> 

  Aqui teve o mesmo erro de desatualização do código anterior

#
- Correção e compilação:

```JAVA
public synchronized void increment() {
    this.count++;
}
```

<img width="1080" height="176" alt="gravação 2 2" src="https://github.com/user-attachments/assets/ad12b7c1-90e7-4c9c-b0d4-e25b65cfc7e4" /> 

#

# Segunda Parte:






  
