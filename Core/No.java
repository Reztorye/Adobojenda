package Core;

public class No {
    Compromisso compromisso;
    No proximo;
    No anterior;

    public No(Compromisso compromisso) {
        this.compromisso = compromisso;
        this.proximo = null;
        this.anterior = null;
    }
    
    public Compromisso getCompromisso() {
        return compromisso;
    }

    public No getProximo() {
        return proximo;
    }

    public No getAnterior() {
        return anterior;
    }
}
