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
}
