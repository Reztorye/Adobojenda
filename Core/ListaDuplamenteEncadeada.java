package Core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ListaDuplamenteEncadeada {
    private No cabeca;
    private No cauda;
    private int tamanho;

    public ListaDuplamenteEncadeada() {
        this.cabeca = null;
        this.cauda = null;
        this.tamanho = 0;
    }

    public void inserir(Compromisso compromisso) {
        No novoNo = new No(compromisso);
        if (cabeca == null) {
            cabeca = novoNo;
            cauda = novoNo;
        } else {
            cauda.proximo = novoNo;
            novoNo.anterior = cauda;
            cauda = novoNo;
        }
        tamanho++;
    }

    public void confirmarAtendimento(int index) {
        No atual = cabeca;
        int contador = 0;
        while (atual != null && contador != index) {
            atual = atual.proximo;
            contador++;
        }
        if (atual != null) {
            atual.compromisso.setExecutado(true);
        }
    }

    public void excluir(int index) {
        No atual = cabeca;
        int contador = 0;
        while (atual != null && contador != index) {
            atual = atual.proximo;
            contador++;
        }
        if (atual != null) {
            if (atual.anterior != null) {
                atual.anterior.proximo = atual.proximo;
            } else {
                cabeca = atual.proximo;
            }
            if (atual.proximo != null) {
                atual.proximo.anterior = atual.anterior;
            } else {
                cauda = atual.anterior;
            }
            tamanho--;
        }
    }

    public boolean pesquisarPorNomeCliente(String nomeCliente) {
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getNomeCliente().equalsIgnoreCase(nomeCliente)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }
    
    public Compromisso findCompromissoById(int id) {
        No atual = cabeca;
        while (atual != null) {
            if (atual.getCompromisso().getId() == id) {
                return atual.getCompromisso();
            }
            atual = atual.getProximo();
        }
        return null;
    }


    public Stack<Compromisso> criarPilhaExecutados() {
        Stack<Compromisso> pilha = new Stack<>();
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.isExecutado()) {
                pilha.push(atual.compromisso);
            }
            atual = atual.proximo;
        }
        return pilha;
    }

    public Queue<Compromisso> criarFilaNaoExecutados() {
        Queue<Compromisso> fila = new LinkedList<>();
        No atual = cabeca;
        while (atual != null) {
            if (!atual.compromisso.isExecutado()) {
                fila.add(atual.compromisso);
            }
            atual = atual.proximo;
        }
        return fila;
    }

    public Deque<Compromisso> criarDeque() {
        Deque<Compromisso> deque = new LinkedList<>();
        No atual = cabeca;
        while (atual != null) {
            if (!atual.compromisso.isExecutado()) {
                deque.addLast(atual.compromisso);
            }
            atual = atual.proximo;
        }
        atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.isExecutado()) {
                deque.addFirst(atual.compromisso);
            }
            atual = atual.proximo;
        }
        return deque;
    }

    public Set<Compromisso> criarListaTelefonica() {
        Set<Compromisso> listaTelefonica = new TreeSet<>(Comparator.comparing(Compromisso::getNomeCliente));
        No atual = cabeca;
        while (atual != null) {
            listaTelefonica.add(atual.compromisso);
            atual = atual.proximo;
        }
        return listaTelefonica;
    }

    public Queue<Compromisso> criarFilaPorCliente(String nomeCliente) {
        Queue<Compromisso> filaCliente = new LinkedList<>();
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getNomeCliente().equalsIgnoreCase(nomeCliente)) {
                filaCliente.add(atual.compromisso);
            }
            atual = atual.proximo;
        }
        return filaCliente;
    }

    public List<Compromisso> criarListaPorData(LocalDate data) {
        List<Compromisso> listaData = new ArrayList<>();
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getData().isEqual(data)) {
                listaData.add(atual.compromisso);
            }
            atual = atual.proximo;
        }
        listaData.sort(Comparator.comparing(Compromisso::getHora));
        return listaData;
    }

    public void imprimirLista() {
        No atual = cabeca;
        while (atual != null) {
            System.out.println(atual.compromisso);
            atual = atual.proximo;
        }
    }

    public int getTamanho() {
        return tamanho;
    }
    
    public List<Compromisso> getTodosCompromissos() {
        List<Compromisso> todosCompromissos = new ArrayList<>(); 
        No atual = cabeca; 

        while (atual != null) {
            todosCompromissos.add(atual.compromisso); 
            atual = atual.proximo; 
        }

        return todosCompromissos; 
    }

    private class No {
        Compromisso compromisso;
        No proximo;
        No anterior;

        public No(Compromisso compromisso) {
            this.compromisso = compromisso;
            this.proximo = null;
            this.anterior = null;
        }

		public No getProximo() {
			// TODO Auto-generated method stub
			return null;
		}

		public Compromisso getCompromisso() {
			// TODO Auto-generated method stub
			return null;
		}
    }
}
