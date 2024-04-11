package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class TelaPrincipal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5421586212616730303L;
	private JTable tabelaCompromissos;
    private DefaultTableModel modelo;
    private ListaDuplamenteEncadeada listaCompromissos;
    

	public TelaPrincipal(ListaDuplamenteEncadeada listaCompromissos) {
        super("Adobojenda - Tela Principal");
        this.listaCompromissos = listaCompromissos;
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Barra de Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem menuItemSair = new JMenuItem("Sair");
        menuArquivo.add(menuItemSair);
        menuBar.add(menuArquivo);

        JMenu menuCompromissos = new JMenu("Compromissos");
        JMenuItem menuItemAdicionar = new JMenuItem("Adicionar");
        menuItemAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioCompromisso formulario = new FormularioCompromisso(TelaPrincipal.this, listaCompromissos, TelaPrincipal.this);
                formulario.setVisible(true);
            }
        });
        JMenuItem menuItemEditar = new JMenuItem("Editar");
        JMenuItem menuItemExcluir = new JMenuItem("Excluir");
        menuCompromissos.add(menuItemAdicionar);
        menuCompromissos.add(menuItemEditar);
        menuCompromissos.add(menuItemExcluir);
        menuBar.add(menuCompromissos);

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem menuItemSobre = new JMenuItem("Sobre");
        menuAjuda.add(menuItemSobre);
        menuBar.add(menuAjuda);

        setJMenuBar(menuBar);

        String[] colunas = {"Nome do Cliente", "Telefone", "Data", "Hora", "Descrição"};
        modelo = new DefaultTableModel(colunas, 0);
        tabelaCompromissos = new JTable(modelo);

        JScrollPane scrollPane = new JScrollPane(tabelaCompromissos);
        add(scrollPane, BorderLayout.CENTER); 

        // Barra de Ferramentas (Botões de Ação)
        JPanel painelFerramentas = new JPanel();
        JButton botaoAdicionar = new JButton("Adicionar");
        JButton botaoEditar = new JButton("Editar");
        JButton botaoExcluir = new JButton("Excluir");
        painelFerramentas.add(botaoAdicionar);
        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormularioCompromisso formulario = new FormularioCompromisso(TelaPrincipal.this, listaCompromissos, TelaPrincipal.this);
                formulario.setVisible(true);
            }
        });
        painelFerramentas.add(botaoEditar);
        painelFerramentas.add(botaoExcluir);
        add(painelFerramentas, BorderLayout.NORTH);

        // Campo de Pesquisa
        JPanel painelPesquisa = new JPanel();
        JTextField campoPesquisa = new JTextField(20);
        JButton botaoPesquisar = new JButton("Pesquisar");
        painelPesquisa.add(campoPesquisa);
        painelPesquisa.add(botaoPesquisar);
        add(painelPesquisa, BorderLayout.SOUTH);

        atualizarListaCompromissos(); 
        setLocationRelativeTo(null);
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
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                ListaDuplamenteEncadeada listaCompromissos = new ListaDuplamenteEncadeada(); 
	                new TelaPrincipal(listaCompromissos).setVisible(true); 
	            }
	        });
	    }
	}