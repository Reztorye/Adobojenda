	package View;
	
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JMenu;
	import javax.swing.JMenuBar;
	import javax.swing.JMenuItem;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
	import javax.swing.JTextField;
	import javax.swing.SwingUtilities;
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
	        setSize(618, 406);
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
	        
	        String[] colunas = {"Nome do Cliente", "Telefone", "Data", "Hora", "Descrição"};
	        modelo = new DefaultTableModel(colunas, 0);
	       
	        tabelaCompromissos = new JTable(modelo);
	        
	        atualizarListaCompromissos();
	        JScrollPane scrollPane = new JScrollPane(tabelaCompromissos);
	        scrollPane.setBounds(10, 60, 580, 250);  
	        getContentPane().add(scrollPane);
	        
	        JPanel painelFerramentas = new JPanel(null);
	        painelFerramentas.setBounds(10, 0, 580, 50);  
	        getContentPane().add(painelFerramentas);
	        
	        JButton botaoAdicionar = new JButton("Adicionar");
	        botaoAdicionar.setBounds(10, 10, 100, 30);  
	        botaoAdicionar.addActionListener(e -> {
	            FormularioCompromisso formulario = new FormularioCompromisso(this, listaCompromissos, this);
	            formulario.setVisible(true);
	        });
	        painelFerramentas.add(botaoAdicionar);
	        
	        JButton botaoEditar = new JButton("Editar");
	        botaoEditar.setBounds(120, 10, 100, 30); 
	        botaoEditar.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                int selectedRow = tabelaCompromissos.getSelectedRow();
	                if (selectedRow >= 0) {
	                    int compromissoId = (Integer) modelo.getValueAt(selectedRow, 0);
	                    Compromisso compromissoParaEditar = listaCompromissos.findCompromissoById(compromissoId);
	                    if (compromissoParaEditar != null) {
	                        FormularioEdicao formularioEdicao = new FormularioEdicao(TelaPrincipal.this, listaCompromissos, TelaPrincipal.this, compromissoParaEditar);
	                        formularioEdicao.setVisible(true);
	                    } else {
	                        JOptionPane.showMessageDialog(TelaPrincipal.this, "Compromisso não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
	                    }
	                } else {
	                    JOptionPane.showMessageDialog(TelaPrincipal.this, "Selecione um compromisso para editar.", "Nenhuma seleção", JOptionPane.WARNING_MESSAGE);
	                }
	            }
	        });

	        painelFerramentas.add(botaoEditar);	        
	
	        JPanel painelPesquisa = new JPanel(null);
	        painelPesquisa.setBounds(10, 320, 580, 40); 
	        
	        JTextField campoPesquisa = new JTextField();
	        campoPesquisa.setBounds(10, 5, 200, 30);
	        
	        JButton botaoPesquisar = new JButton("Pesquisar");
	        botaoPesquisar.setBounds(220, 5, 100, 30);  
	        painelPesquisa.add(campoPesquisa);
	        painelPesquisa.add(botaoPesquisar);
	        getContentPane().add(painelPesquisa);
	        
	        
	    }
	    
	    private Compromisso getCompromissoFromRow(int row) {
	        Integer id = (Integer) modelo.getValueAt(row, 0); 
	        return listaCompromissos.findCompromissoById(id);
	    }
	
	    public void atualizarListaCompromissos() {
	        modelo.setRowCount(0);
	        for (Compromisso compromisso : listaCompromissos.getTodosCompromissos()) {
	            modelo.addRow(new Object[]{
	                    compromisso.getNomeCliente(),
	                    compromisso.getTelefone(),
	                    compromisso.getData(),
	                    compromisso.getHora(),
	                    compromisso.getDescricao()
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
