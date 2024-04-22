package View;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import com.toedter.calendar.JCalendar;

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
        setSize(940, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);
        
        JMenuBar menuBar = new JMenuBar();

        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem menuItemSair = new JMenuItem("Sair");
        menuItemSair.addActionListener(e -> System.exit(0));
        menuArquivo.add(menuItemSair);
        menuBar.add(menuArquivo);

        JMenu menuTasks = new JMenu("Tarefas");
        
        JMenuItem menuItemA = new JMenuItem("Item A - Gestão de Atendimentos Executados");
        menuItemA.addActionListener(e -> listaCompromissos.separarCompromissosExecutados());
        menuTasks.add(menuItemA);
        
        JMenuItem menuItemB = new JMenuItem("Item B - Organização Flexível de Compromissos");
        menuItemB.addActionListener(e -> listaCompromissos.apresentarDequeCompromissos());
        menuTasks.add(menuItemB);
        
        JMenuItem menuItemC = new JMenuItem("Item C - Catálogo de Clientes");
        menuItemC.addActionListener(e ->  {
            Set<Compromisso> listaTelefonica = listaCompromissos.criarListaTelefonica();
            mostrarListaTelefonica(listaTelefonica);
        });
        menuTasks.add(menuItemC);
        
        JMenuItem menuItemD = new JMenuItem("Item D - Registro de Atendimentos por Cliente");
        menuItemD.addActionListener(e -> abrirDialogoItemD());
        menuTasks.add(menuItemD);
        
        JMenuItem menuItemE = new JMenuItem("Item E - Agenda Diária Ordenada");
        menuItemE.addActionListener(e -> abrirDialogoItemE());
        menuTasks.add(menuItemE);   
           
        menuBar.add(menuTasks);	
        menuBar.add(menuArquivo);
        setJMenuBar(menuBar);

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
        
        TableColumnModel columnModel = tabelaCompromissos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(4).setPreferredWidth(70); 
        columnModel.getColumn(5).setPreferredWidth(300);
        
        listaCompromissos.inserir(new Compromisso("Ana Beatriz", "(21)98765-4321", LocalDate.of(2024, 4, 20), LocalTime.of(15, 30), "Consulta Jurídica"));
        listaCompromissos.inserir(new Compromisso("Carlos Souza", "(11)92345-6789", LocalDate.of(2024, 5, 10), LocalTime.of(9, 0), "Reunião de Planejamento"));
        listaCompromissos.inserir(new Compromisso("Juliana Martins", "(85)91234-5678", LocalDate.of(2024, 4, 25), LocalTime.of(14, 15), "Discussão de Contrato"));
        listaCompromissos.inserir(new Compromisso("Marcos Ribeiro", "(21)99876-5432", LocalDate.of(2024, 4, 15), LocalTime.of(16, 45), "Revisão de Processo"));
        listaCompromissos.inserir(new Compromisso("Fernanda Lima", "(11)98888-7777", LocalDate.of(2024, 5, 5), LocalTime.of(11, 30), "Consultoria de Negócios"));
        listaCompromissos.inserir(new Compromisso("Roberto Carlos", "(31)97777-6666", LocalDate.of(2024, 5, 22), LocalTime.of(10, 0), "Auditoria Interna"));
        listaCompromissos.inserir(new Compromisso("Luciana Alves", "(21)96666-5555", LocalDate.of(2024, 5, 18), LocalTime.of(8, 30), "Entrevista de Emprego"));
        listaCompromissos.inserir(new Compromisso("Paulo Henrique", "(11)95555-4444", LocalDate.of(2024, 4, 28), LocalTime.of(13, 0), "Acompanhamento de Projeto"));
        listaCompromissos.inserir(new Compromisso("Sandra Gomes", "(85)94444-3333", LocalDate.of(2024, 5, 12), LocalTime.of(17, 45), "Feedback do Cliente"));
        listaCompromissos.inserir(new Compromisso("Tiago Nunes", "(11)93333-2222", LocalDate.of(2024, 4, 30), LocalTime.of(12, 15), "Alinhamento Estratégico"));
        listaCompromissos.inserir(new Compromisso("Ana Beatriz", "(21)98765-4321", LocalDate.now(), LocalTime.of(10, 30), "Consulta 1"));
        listaCompromissos.inserir(new Compromisso("Carlos Souza", "(11)92345-6789", LocalDate.now(), LocalTime.of(11, 0), "Reunião 1"));
        listaCompromissos.inserir(new Compromisso("Ana Beatriz", "(21)98765-4321", LocalDate.now(), LocalTime.of(12, 30), "Consulta 2"));
        listaCompromissos.inserir(new Compromisso("Diego Martins", "(85)91234-5678", LocalDate.now(), LocalTime.of(14, 15), "Discussão de Contrato"));
        listaCompromissos.inserir(new Compromisso("Eva Mendes", "(31)97777-6666", LocalDate.now(), LocalTime.of(16, 0), "Auditoria Interna"));

        atualizarListaCompromissos();
        
        JScrollPane scrollPane = new JScrollPane(tabelaCompromissos);
        scrollPane.setBounds(10, 71, 900, 218);  
        getContentPane().add(scrollPane);
        
        JPanel painelFerramentas = new JPanel(null);
        painelFerramentas.setBounds(10, 10, 900, 50);  
        getContentPane().add(painelFerramentas);
        
        JButton botaoAdicionar = new JButton("Adicionar");
        botaoAdicionar.setBounds(0, 10, 90, 30);  
        botaoAdicionar.addActionListener(e -> {
            FormularioAdicionar formulario = new FormularioAdicionar(this, listaCompromissos, this);
            formulario.setVisible(true);
        });
        painelFerramentas.add(botaoAdicionar);
        
        JButton botaoEditar = new JButton("Editar");
        botaoEditar.setBounds(200, 10, 90, 30); 
        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabelaCompromissos.getSelectedRow();
                if (selectedRow >= 0) {
                    try {
                        int compromissoId = (Integer) modelo.getValueAt(selectedRow, 0);
                        Compromisso compromissoSelecionado = listaCompromissos.findCompromissoById(compromissoId);
                        if (compromissoSelecionado != null) {
                        	FormularioEditar formularioEdicao = new FormularioEditar(TelaPrincipal.this, listaCompromissos, TelaPrincipal.this, compromissoSelecionado);
                        	System.out.println("TESTEEEEEEEE: " + formularioEdicao.hashCode());
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
        botaoDeletar.setBounds(100, 10, 90, 30);
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
   
        JButton botaoExecutar = new JButton("Executar");
        botaoExecutar.setBounds(790, 10, 100, 30);
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
                        Compromisso compromisso = listaCompromissos.findCompromissoById(compromissoId);
                        compromisso.setExecutado(true);
                        compromisso.setHoraExecutado(LocalDateTime.now()); 
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
            
        JPanel painelPesquisa = new JPanel(null);
        painelPesquisa.setBounds(10, 300, 900, 50); 
        
        JTextField campoPesquisa = new JTextField();
        campoPesquisa.setBounds(0, 5, 200, 30);
        
        JButton botaoPesquisar = new JButton("Pesquisar");
        botaoPesquisar.setBounds(210, 5, 100, 30);  
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(botaoPesquisar);
        getContentPane().add(painelPesquisa);
        
        botaoPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String termoPesquisa = campoPesquisa.getText().trim().toLowerCase(); 

                if (!termoPesquisa.isEmpty()) {
                    List<Compromisso> compromissosFiltrados = new ArrayList<>();

                    for (Compromisso compromisso : listaCompromissos.getTodosCompromissos()) {
                        if (compromisso.getNomeCliente().toLowerCase().contains(termoPesquisa) ||
                            compromisso.getTelefone().contains(termoPesquisa) ||
                            compromisso.getData().toString().contains(termoPesquisa) ||
                            compromisso.getHora().toString().contains(termoPesquisa) ||
                            compromisso.getDescricao().toLowerCase().contains(termoPesquisa)){
                            compromissosFiltrados.add(compromisso);
                        }
                    }

                    modelo.setRowCount(0);
                    for (Compromisso compromisso : compromissosFiltrados) {
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
                } else {
                    atualizarListaCompromissos();
                }
            }
        });
    }
   
    private void abrirDialogoItemD() {
        JDialog dialog = new JDialog(this, "Buscar Atendimentos por Cliente", true);
        dialog.setSize(315, 150);
        dialog.getContentPane().setLayout(null);
        
        JLabel labelNome = new JLabel("Nome do Cliente:");
        labelNome.setBounds(10, 20, 120, 25);
        dialog.getContentPane().add(labelNome);

        JTextField textField = new JTextField();
        textField.setBounds(130, 20, 160, 25);
        dialog.getContentPane().add(textField);

        JButton button = new JButton("Buscar");
        button.setBounds(100, 60, 100, 25);
        dialog.getContentPane().add(button);

        button.addActionListener(e -> {
            String nomeCliente = textField.getText().trim();
            if (!nomeCliente.isEmpty()) {
                if (listaCompromissos.pesquisarPorNomeCliente(nomeCliente)) {
                    mostrarAtendimentosCliente(nomeCliente, dialog);
                } else {
                    JOptionPane.showMessageDialog(dialog, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Por favor, insira o nome do cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    private void abrirDialogoItemE() {
        JDialog dialog = new JDialog(this, "Buscar Atendimentos por Data", true);
        dialog.setSize(400, 300);
        dialog.getContentPane().setLayout(null);

        JCalendar calendar = new JCalendar();
        calendar.setBounds(10, 10, 300, 200);
        dialog.getContentPane().add(calendar);

        JButton button = new JButton("Buscar");
        button.setBounds(150, 220, 100, 30);
        dialog.getContentPane().add(button);

        button.addActionListener(e -> {
            LocalDate data = calendar.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            mostrarAtendimentosPorData(data, dialog);
        });

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
    
    // Método Auxiliar do item D
    private void mostrarAtendimentosCliente(String nomeCliente, JDialog parentDialog) {
        Queue<Compromisso> filaAtendimentos = listaCompromissos.criarFilaAtendimentosCliente(nomeCliente);

        if (filaAtendimentos.isEmpty()) {
            JOptionPane.showMessageDialog(parentDialog, "Nenhum atendimento encontrado para o cliente: " + nomeCliente, "Resultado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTextArea textArea = new JTextArea(10, 30);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            StringBuilder sb = new StringBuilder();
            for (Compromisso compromisso : filaAtendimentos) {
                sb.append("ID: ").append(compromisso.getId())
                  .append(", Cliente: ").append(compromisso.getNomeCliente())
                  .append(", Data: ").append(compromisso.getData())
                  .append(", Hora: ").append(compromisso.getHora())
                  .append(", Descrição: ").append(compromisso.getDescricao())
                  .append(", Executado: ").append(compromisso.isExecutado() ? "Sim" : "Não")
                  .append("\n");
            }
            textArea.setText(sb.toString());
            
            JDialog resultDialog = new JDialog(parentDialog, "Resultados da Busca", true);
            resultDialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
            resultDialog.pack();
            resultDialog.setLocationRelativeTo(parentDialog);
            resultDialog.setVisible(true);
        }
    }

    // Método Auxiliar do item B
    private void mostrarListaTelefonica(Set<Compromisso> listaTelefonica) {
        JDialog dialogoLista = new JDialog();
        dialogoLista.setTitle("Lista Telefônica");
        JTextArea textAreaLista = new JTextArea(15, 50);
        textAreaLista.setEditable(false);
        StringBuilder relatorio = new StringBuilder("Lista Telefônica:\n");

        for (Compromisso compromisso : listaTelefonica) {
            relatorio.append("Nome: ").append(compromisso.getNomeCliente())
                     .append(", Telefone: ").append(compromisso.getTelefone())
                     .append("\n");
        }

        textAreaLista.setText(relatorio.toString());
        dialogoLista.getContentPane().add(new JScrollPane(textAreaLista));
        dialogoLista.pack();
        dialogoLista.setLocationRelativeTo(null);
        dialogoLista.setVisible(true);
    }
    
    // Método Auxiliar do item E
    private void mostrarAtendimentosPorData(LocalDate data, JDialog parentDialog) {
        List<Compromisso> atendimentosData = listaCompromissos.criarListaPorData(data);

        if (atendimentosData.isEmpty()) {
            JOptionPane.showMessageDialog(parentDialog, "Não há atendimentos para esta data.", "Sem Atendimentos", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTextArea textArea = new JTextArea(10, 30);
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            StringBuilder sb = new StringBuilder();
            for (Compromisso compromisso : atendimentosData) {
                sb.append("ID: ").append(compromisso.getId())
                  .append(", Cliente: ").append(compromisso.getNomeCliente())
                  .append(", Data: ").append(compromisso.getData())
                  .append(", Hora: ").append(compromisso.getHora())
                  .append(", Descrição: ").append(compromisso.getDescricao())
                  .append(", Executado: ").append(compromisso.isExecutado() ? "Sim" : "Não")
                  .append("\n");
            }
            textArea.setText(sb.toString());

            JDialog resultDialog = new JDialog(parentDialog, "Resultados da Busca", true);
            resultDialog.getContentPane().add(scrollPane, BorderLayout.CENTER);
            resultDialog.pack();
            resultDialog.setLocationRelativeTo(parentDialog);
            resultDialog.setVisible(true);
        }
    }


    public void atualizarListaCompromissos() {
        modelo.setRowCount(0);
        for (Compromisso compromisso : listaCompromissos.getTodosCompromissos()) {
            modelo.addRow(new Object[]{
                compromisso.getId(), 
                compromisso.getNomeCliente(),
                compromisso.getTelefone(),
                compromisso.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
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
