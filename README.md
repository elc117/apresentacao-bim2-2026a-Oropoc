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

- URL do projeto: https://github.com/LeonardoZ/java-concurrency-patterns/tree/master

  É um repositório educacional que tem exemplos de mecanismos de concorrencia em JAVA, tem vários exemplos 
#
- Trecho pegado de exemplo: https://github.com/LeonardoZ/java-concurrency-patterns/blob/master/src/main/java/br/com/leonardoz/features/locks/UsingIntrinsicLocks.java

```JAVA
public class UsingIntrinsicLocks {

    private boolean state;

    // ────────────────────────────────────────
    // EXEMPLO 1: synchronized no método inteiro
    // ─────────────────────────────────────────
    public synchronized void mySynchronizedMethod() {
        state = !state;
        System.out.println("My state is:" + state);
    }
    // uma thread por vez executa o método inteiro.
    // resultado: sempre true, false, true, false...
    // sem synchronized: true, true, false, true... (imprevisível)

    // ─────────────────────────────────────────
    // EXEMPLO 2: synchronized só em um bloco
    // ─────────────────────────────────────────
    public void mySynchronizedBlock() {
        // esta linha roda FORA do lock — qualquer thread executa livremente
		// aqui dá para ver quando um thread vai dar o start
        System.out.println("Who owns my lock: " + Thread.currentThread().getName());

        synchronized (this) {
            // só aqui é protegido — apenas uma thread por vez
            state = !state;
            System.out.println("State is: " + state);
        }
    }
	// a saída pode ser vários "homl" com threads aleatórias, mas o state não vai ser, vai ser um por vez
    // útil quando só uma parte do método precisa de proteção.
    // o resto roda em paralelo normalmente — melhor desempenho.

    // ─────────────────────────────────────────
    // EXEMPLO 3: reentrância
    // ─────────────────────────────────────────
    public synchronized void reentrancy() {
    // thread-1 entra aqui e pega o lock do objeto
    // thread-2 tenta entrar mas fica bloqueada esperando
    
    System.out.println("Before acquiring again");
    // thread-1 printa "Before acquiring again"

    synchronized (this) {
        // thread-1 tenta pegar o lock de novo
        // como é reentrante, ele passa direto, sem travar
        
        System.out.println("I'm own it! " + Thread.currentThread().getName());
        // thread-1 printa "I'm own it! pool-1-thread-1"
    }
    // thread-1 libera o lock interno

} // thread-1 libera o lock externo 
  // thread-2 repete o mesmo processo
    // isso mostra que o lock interno é reentrante:
    // não causa deadlock.


    // ─────────────────────────────────────────
    // MAIN: dispara as threads para cada método
    // ─────────────────────────────────────────
    public static void main(String[] args) throws InterruptedException {
        var executor = Executors.newCachedThreadPool();
        var self = new UsingIntrinsicLocks();

        // 100 threads no método 1
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> self.mySynchronizedMethod());
        }

        Thread.sleep(1000);
		//poderia ser join()

        // 10 threads no método 2
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> self.mySynchronizedBlock());
        }

        Thread.sleep(1000);

        // 10 threads no método 3
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> self.reentrancy());
        }

        executor.shutdown();
    }
}
```

  
