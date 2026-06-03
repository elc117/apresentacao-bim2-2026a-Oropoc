public class UsingIntrinsicLocks {

    private boolean state;

    // Versão com synchronized no método inteiro
    public synchronized void mySynchronizedMethod() {
        state = !state;
        System.out.println("My state is:" + state);
        // Sem sync: sequência imprevisível — true, true, false, true...
        // Com sync:  sempre alternado  — true, false, true, false...
    }

    // Versão com synchronized apenas no bloco crítico
    public void mySynchronizedBlock() {
        // Esta linha roda SEM o lock (qualquer thread pode executar ao mesmo tempo)
        System.out.println("Who owns my lock: " + Thread.currentThread().getName());

        synchronized (this) {
            // Só esta parte é protegida
            state = !state;
            System.out.println("State is: " + state);
        }
    }

    public static void main(String[] args) {
        var executor = Executors.newCachedThreadPool();
        var self = new UsingIntrinsicLocks();

        // Dispara 100 threads ao mesmo tempo no mesmo objeto
        for (int i = 0; i < 100; i++) {
            executor.execute(() -> self.mySynchronizedMethod());
        }
    }
}