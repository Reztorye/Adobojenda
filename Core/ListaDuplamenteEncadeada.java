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

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
    
    public void confirmarAtendimentoPeloId(int id) {
        No atual = cabeca;
        while (atual != null) {
            if (atual.getCompromisso().getId() == id) {
                atual.getCompromisso().setExecutado(true);
                return;
            }
            atual = atual.getProximo();
        }
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
            if (atual.getCompromisso() != null && atual.getCompromisso().getId() == id) {
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
    
    public void apresentarDequeCompromissos() {
        Deque<Compromisso> deque = new LinkedList<>();

        for (Compromisso compromisso : getTodosCompromissos()) {
            if (compromisso.isExecutado()) {
                deque.addFirst(compromisso);
            } else {
                deque.addLast(compromisso);
            }
        }

        JDialog dialogoRelatorio = new JDialog();
        dialogoRelatorio.setTitle("Relatório de Compromissos");
        JTextArea textAreaRelatorio = new JTextArea(15, 50);
        textAreaRelatorio.setEditable(false);

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("Atendimentos Não Executados:\n");
        for (Compromisso compromisso : deque) {
            if (!compromisso.isExecutado()) {
                relatorio.append(compromisso.toString()).append("\n");
            }
        }

        relatorio.append("\nAtendimentos Executados:\n");
        for (Compromisso compromisso : deque) {
            if (compromisso.isExecutado()) {
                relatorio.append(compromisso.toString()).append("\n");
            }
        }

        textAreaRelatorio.setText(relatorio.toString());
        dialogoRelatorio.add(new JScrollPane(textAreaRelatorio));
        dialogoRelatorio.pack();
        dialogoRelatorio.setLocationRelativeTo(null);
        dialogoRelatorio.setVisible(true);
    }

    public Set<Compromisso> criarListaTelefonica() {
        Set<Compromisso> listaTelefonica = new TreeSet<>(new Comparator<Compromisso>() {
            @Override
            public int compare(Compromisso c1, Compromisso c2) {
                int nomeComp = c1.getNomeCliente().compareToIgnoreCase(c2.getNomeCliente());
                if (nomeComp != 0) {
                    return nomeComp;
                }
                return Integer.compare(c1.getId(), c2.getId());
            }
        });
        No atual = cabeca;
        while (atual != null) {
            listaTelefonica.add(atual.compromisso);
            atual = atual.getProximo();
        }
        return listaTelefonica;
    }

    public Queue<Compromisso> criarFilaAtendimentosCliente(String nomeCliente) {
        Queue<Compromisso> filaAtendimentos = new LinkedList<>();
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getNomeCliente().equalsIgnoreCase(nomeCliente)) {
                filaAtendimentos.add(atual.compromisso);
            }
            atual = atual.getProximo();
        }
        return filaAtendimentos;
    }
    
    public List<Compromisso> criarListaPorData(LocalDate data) {
        List<Compromisso> listaData = new ArrayList<>();
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getData().equals(data)) {
                listaData.add(atual.compromisso);
            }
            atual = atual.getProximo();
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
    
    public void removerPeloId(int id) {
        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.getId() == id) {
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
                return; 
            }
            atual = atual.proximo;
        }
    }
    
    public void separarCompromissosExecutados() {
        Stack<Compromisso> pilhaExecutados = new Stack<>();
        Queue<Compromisso> filaNaoExecutados = new LinkedList<>();

        No atual = cabeca;
        while (atual != null) {
            if (atual.compromisso.isExecutado()) {
                pilhaExecutados.push(atual.compromisso);
            } else {
                filaNaoExecutados.add(atual.compromisso);
            }
            atual = atual.proximo;
        }

        JDialog resultadoDialogo = new JDialog();
        resultadoDialogo.setTitle("Compromissos Separados");
        JTextArea textAreaResultados = new JTextArea(10, 40);
        textAreaResultados.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaResultados);
        
        StringBuilder resultados = new StringBuilder();
        resultados.append("Pilha de Compromissos Executados:\n");
        while (!pilhaExecutados.isEmpty()) {
            Compromisso c = pilhaExecutados.pop();
            resultados.append("ID: ").append(c.getId())
                      .append(", Cliente: ").append(c.getNomeCliente())
                      .append(", Telefone: ").append(c.getTelefone())
                      .append(", Data: ").append(c.getData())
                      .append(", Hora: ").append(c.getHora())
                      .append(", Descrição: ").append(c.getDescricao())
                      .append(", Executado: ").append(c.isExecutado() ? "Sim" : "Não")
                      .append("\n");
        }

        
        resultados.append("\nFila de Compromissos Não Executados:\n");
        while (!filaNaoExecutados.isEmpty()) {
            resultados.append(filaNaoExecutados.remove().toString()).append("\n");
        }
        
        textAreaResultados.setText(resultados.toString());
        resultadoDialogo.add(scrollPane);
        resultadoDialogo.pack();
        resultadoDialogo.setLocationRelativeTo(null); 
        resultadoDialogo.setVisible(true);
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


}
