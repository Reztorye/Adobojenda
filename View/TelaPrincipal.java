package View;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 5421586212616730303L;
    private JTable tabelaCompromissos;
    private DefaultTableModel modelo;
    private ListaDuplamenteEncadeada listaCompromissos;

    public TelaPrincipal(ListaDuplamenteEncadeada listaCompromissos) {
        super("Adobojenda - Tela Principal");
        this.listaCompromissos = listaCompromissos;
        setSize(890, 498);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem menuItemSair = new JMenuItem("Sair");
        menuItemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(menuItemSair);
        
        JMenu menuCompromissos = new JMenu("Compromissos");
        JMenuItem menuItemAdicionar = new JMenuItem("Adicionar");
        menuItemAdicionar.addActionListener(e -> {
            FormularioCompromisso formulario = new FormularioCompromisso(this, listaCompromissos, this);
            formulario.setVisible(true);
        });
        menuCompromissos.add(menuItemAdicionar);

        menuBar.add(menuArquivo);
        menuBar.add(menuCompromissos);

        setLocationRelativeTo(null);
        
        String[] colunas = {"ID", "Nome do Cliente", "Telefone", "Data", "Hora", "Descrição", "Executado"};
        modelo = new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 8100759408390693468L;
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) { 
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
       
        tabelaCompromissos = new JTable(modelo);
        
        
        
        tabelaCompromissos.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 6162414439180656649L;

			@Override
            public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Boolean) {
                    value = (Boolean) value ? "Sim" : "Não";
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });
        
        listaCompromissos.inserir(new Compromisso("Teste Cliente", "(11)99999-9999", LocalDate.now(), LocalTime.now(), "Descrição Teste"));
        listaCompromissos.inserir(new Compromisso("Teste Cliente", "(11)99999-9999", LocalDate.now(), LocalTime.now(), "Descrição Teste"));
        listaCompromissos.inserir(new Compromisso("Teste Cliente", "(11)99999-9999", LocalDate.now(), LocalTime.now(), "Descrição Teste"));

        
        atualizarListaCompromissos();
        
        JScrollPane scrollPane = new JScrollPane(tabelaCompromissos);
        scrollPane.setBounds(10, 90, 854, 306);  
        getContentPane().add(scrollPane);
        
        JPanel painelFerramentas = new JPanel(null);
        painelFerramentas.setBounds(10, 0, 854, 170);  
        getContentPane().add(painelFerramentas);
        
        JButton botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.setBounds(344, 51, 90, 30);  
        botaoAdicionar.addActionListener(e -> {
            FormularioCompromisso formulario = new FormularioCompromisso(this, listaCompromissos, this);
            formulario.setVisible(true);
        });
        painelFerramentas.add(botaoAdicionar);
        
        JButton botaoEditar = new JButton("Editar");
        botaoEditar.setBounds(544, 51, 90, 30); 
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaCompromissos.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int compromissoId = (Integer) modelo.getValueAt(selectedRow, 0);
                        Compromisso compromissoSelecionado = listaCompromissos.findCompromissoById(compromissoId);
                        if (compromissoSelecionado != null) {
                        	FormularioEdicao formularioEdicao = new FormularioEdicao(TelaPrincipal.this, listaCompromissos, TelaPrincipal.this, compromissoSelecionado);
                            formularioEdicao.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(TelaPrincipal.this, "Compromisso não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace(); 
                        JOptionPane.showMessageDialog(TelaPrincipal.this, "Erro ao tentar editar.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Selecione um compromisso para editar.", "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        painelFerramentas.add(botaoEditar);

        JButton botaoDeletar = new JButton("Deletar");
        botaoDeletar.setBounds(444, 51, 90, 30);
        botaoDeletar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaCompromissos.getSelectedRow();
                if (selectedRow >= 0) {
                    int decision = JOptionPane.showConfirmDialog(
                        TelaPrincipal.this,
                        "Tem certeza que deseja deletar este compromisso?",
                        "Confirmar Deleção",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                    );
                    if (decision == JOptionPane.YES_OPTION) {
                        try {
                            int compromissoId = (Integer) modelo.getValueAt(selectedRow, 0);
                            listaCompromissos.removerPeloId(compromissoId);
                            atualizarListaCompromissos();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(TelaPrincipal.this, "Erro ao tentar deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Selecione um compromisso para deletar.", "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        painelFerramentas.add(botaoDeletar);

        JButton botaoSeparar = new JButton("Separar Executados/Não Executados");
        botaoSeparar.setBounds(484, 10, 250, 30); 
        botaoSeparar.addActionListener(e -> listaCompromissos.separarCompromissosExecutados());
        painelFerramentas.add(botaoSeparar);
        
        JButton botaoExecutar = new JButton("Executar");
        botaoExecutar.setBounds(744, 10, 100, 30);
        botaoExecutar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaCompromissos.getSelectedRow();
                if (selectedRow >= 0) {
                    int compromissoId = (Integer) modelo.getValueAt(selectedRow, 0);
                    
                    int confirm = JOptionPane.showConfirmDialog(
                            TelaPrincipal.this,
                            "Deseja marcar o compromisso como executado?",
                            "Confirmar Execução",
                            JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        listaCompromissos.confirmarAtendimentoPeloId(compromissoId);  
                        atualizarListaCompromissos();  
                    }
                } else {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Selecione um compromisso para executar.", "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        tabelaCompromissos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                int selectedRow = tabelaCompromissos.getSelectedRow();
                if (selectedRow >= 0 && !event.getValueIsAdjusting()) {
                    boolean executado = (Boolean) modelo.getValueAt(selectedRow, 6);
                    botaoExecutar.setEnabled(!executado);
                }
            }
        });
        painelFerramentas.add(botaoExecutar);
        
        JButton botaoRelatorioDeque = new JButton("Relatório Deque");
        botaoRelatorioDeque.setBounds(340, 10, 127, 30);
        botaoRelatorioDeque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaCompromissos.apresentarDequeCompromissos();
            }
        });
        painelFerramentas.add(botaoRelatorioDeque);
        
        JButton botaoListaTelefonica = new JButton("Mostrar Lista Telefônica");
        botaoListaTelefonica.setBounds(644, 50, 200, 30);
        botaoListaTelefonica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<Compromisso> listaTelefonica = listaCompromissos.criarListaTelefonica();
                mostrarListaTelefonica(listaTelefonica);
            }
        });
        painelFerramentas.add(botaoListaTelefonica);

        JTextField campoNomeCliente = new JTextField();
        campoNomeCliente.setBounds(10, 50, 100, 20); 
        painelFerramentas.add(campoNomeCliente);

        JButton botaoMostrarFilaCliente = new JButton("Mostrar Fila do Cliente");
        botaoMostrarFilaCliente.setBounds(120, 50, 200, 20);
        botaoMostrarFilaCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeCliente = campoNomeCliente.getText().trim(); 
                if (nomeCliente.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Por favor, insira o nome do cliente.", "Nome do Cliente Vazio", JOptionPane.WARNING_MESSAGE);
                } else if (!listaCompromissos.pesquisarPorNomeCliente(nomeCliente)) {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Cliente não encontrado.", "Cliente Inexistente", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    Queue<Compromisso> filaAtendimentos = listaCompromissos.criarFilaAtendimentosCliente(nomeCliente);
                    if (filaAtendimentos.isEmpty()) {
                        JOptionPane.showMessageDialog(TelaPrincipal.this, "Não há atendimentos para este cliente.", "Nenhum Atendimento", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        mostrarFilaAtendimentosCliente(filaAtendimentos);
                    }
                }
            }
        });
        painelFerramentas.add(botaoMostrarFilaCliente);
        
        JLabel labelData = new JLabel("Data:");
        JSpinner spinnerData = new JSpinner(new SpinnerDateModel());
        spinnerData.setEditor(new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy"));
        spinnerData.setBounds(10, 15, 100, 20);
        painelFerramentas.add(labelData);
        painelFerramentas.add(spinnerData);

        JButton botaoMostrarListaData = new JButton("Mostrar Atendimentos Data");
        botaoMostrarListaData.setBounds(130, 15, 200, 20);
        botaoMostrarListaData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date dataSelecionada = (Date) spinnerData.getValue();
                LocalDate data = LocalDate.ofInstant(dataSelecionada.toInstant(), ZoneId.systemDefault());

                List<Compromisso> atendimentosData = listaCompromissos.criarListaPorData(data);
                if (atendimentosData.isEmpty()) {
                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Não há atendimentos para esta data.", "Sem Atendimentos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    mostrarAtendimentosData(atendimentosData);
                }
            }
        });
        painelFerramentas.add(botaoMostrarListaData);
   
        JPanel painelPesquisa = new JPanel(null);
        painelPesquisa.setBounds(10, 407, 854, 41); 
        
        JTextField campoPesquisa = new JTextField();
        campoPesquisa.setBounds(10, 5, 200, 30);
        
        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.setBounds(220, 5, 100, 30);  
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(botaoPesquisar);
        getContentPane().add(painelPesquisa);
    }
    
    private void mostrarAtendimentosData(List<Compromisso> atendimentosData) {
        JDialog dialogoAtendimentos = new JDialog();
        dialogoAtendimentos.setTitle("Atendimentos da Data");
        JTextArea textAreaAtendimentos = new JTextArea(15, 50);
        textAreaAtendimentos.setEditable(false);
        StringBuilder relatorio = new StringBuilder("Atendimentos:\n");

        for (Compromisso compromisso : atendimentosData) {
            relatorio.append(compromisso.toString()).append(" - Executado: ")
                     .append(compromisso.isExecutado() ? "Sim" : "Não")
                     .append("\n");
        }

        textAreaAtendimentos.setText(relatorio.toString());
        dialogoAtendimentos.getContentPane().add(new JScrollPane(textAreaAtendimentos));
        dialogoAtendimentos.pack();
        dialogoAtendimentos.setLocationRelativeTo(null);
        dialogoAtendimentos.setVisible(true);
    }

    
    private void mostrarFilaAtendimentosCliente(Queue<Compromisso> filaAtendimentos) {
        JDialog dialogoFila = new JDialog();
        dialogoFila.setTitle("Fila de Atendimentos do Cliente");
        JTextArea textAreaFila = new JTextArea(15, 50);
        textAreaFila.setEditable(false);
        StringBuilder relatorio = new StringBuilder("Fila de Atendimentos:\n");

        for (Compromisso compromisso : filaAtendimentos) {
            relatorio.append(compromisso.toString()).append("\n");
        }

        textAreaFila.setText(relatorio.toString());
        dialogoFila.getContentPane().add(new JScrollPane(textAreaFila));
        dialogoFila.pack();
        dialogoFila.setLocationRelativeTo(null);
        dialogoFila.setVisible(true);
    }
    
    private void mostrarListaTelefonica(Set<Compromisso> listaTelefonica) {
        JDialog dialogoLista = new JDialog();
        dialogoLista.setTitle("Lista Telefônica");
        JTextArea textAreaLista = new JTextArea(15, 50);
        textAreaLista.setEditable(false);
        StringBuilder relatorio = new StringBuilder("Lista Telefônica:\n");

        for (Compromisso compromisso : listaTelefonica) {
            relatorio.append("ID: ").append(compromisso.getId())
                     .append(", Nome: ").append(compromisso.getNomeCliente())
                     .append(", Telefone: ").append(compromisso.getTelefone())
                     .append("\n");
        }

        textAreaLista.setText(relatorio.toString());
        dialogoLista.getContentPane().add(new JScrollPane(textAreaLista));
        dialogoLista.pack();
        dialogoLista.setLocationRelativeTo(null);
        dialogoLista.setVisible(true);
    }

    public void atualizarListaCompromissos() {
        modelo.setRowCount(0);
        for (Compromisso compromisso : listaCompromissos.getTodosCompromissos()) {
            modelo.addRow(new Object[]{
                compromisso.getId(), 
                compromisso.getNomeCliente(),
                compromisso.getTelefone(),
                compromisso.getData().toString(),
                compromisso.getHora().toString(),
                compromisso.getDescricao(),
                compromisso.isExecutado()  
            });
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ListaDuplamenteEncadeada listaCompromissos = new ListaDuplamenteEncadeada();
            new TelaPrincipal(listaCompromissos).setVisible(true);
        });
    }
}
