# Apresentação João Daniel 
#
## Primeira Parte
- Compilação exercício TransaçõesBancarias.java:

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


  
  
