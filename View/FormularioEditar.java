package View;

import javax.swing.*;
import java.time.LocalTime;

import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class FormularioEditar extends JDialog {

    private static final long serialVersionUID = 1L;
    private JTextField campoNomeCliente, campoTelefone, campoData, campoHora;
    private JTextArea areaDescricao;
    private JButton botaoSalvar, botaoCancelar;
    private JScrollPane scrollDescricao;
    @SuppressWarnings("unused")
	private ListaDuplamenteEncadeada listaCompromissos;
    @SuppressWarnings("unused")
	private TelaPrincipal telaPrincipal;
    private Compromisso compromissoAtual;

    public FormularioEditar(JFrame parent, ListaDuplamenteEncadeada listaCompromissos, TelaPrincipal telaPrincipal, Compromisso compromisso) {
        super(parent, "Editar Compromisso", true);
        this.listaCompromissos = listaCompromissos;
        this.telaPrincipal = telaPrincipal;
        this.compromissoAtual = compromisso;
        
        setSize(700, 400);
        setLayout(null);

        campoNomeCliente = new JTextField(compromisso.getNomeCliente());
        campoTelefone = new JTextField(compromisso.getTelefone());
        campoData = new JTextField(compromisso.getData().toString()); 
        campoHora = new JTextField(compromisso.getHora().toString()); 
        areaDescricao = new JTextArea(compromisso.getDescricao(), 5, 20);
        scrollDescricao = new JScrollPane(areaDescricao);

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        campoNomeCliente.setBounds(150, 20, 200, 25);
        campoTelefone.setBounds(150, 60, 200, 25);
        campoData.setBounds(150, 100, 200, 25);
        campoHora.setBounds(150, 140, 200, 25);
        scrollDescricao.setBounds(150, 180, 200, 75);

        botaoSalvar.setBounds(150, 270, 95, 30);
        botaoCancelar.setBounds(255, 270, 95, 30);

        add(new JLabel("Nome do Cliente:")).setBounds(20, 20, 120, 25);
        add(campoNomeCliente);
        add(new JLabel("Telefone:")).setBounds(20, 60, 120, 25);
        add(campoTelefone);
        add(new JLabel("Data:")).setBounds(20, 100, 120, 25);
        add(campoData);
        add(new JLabel("Hora:")).setBounds(20, 140, 120, 25);
        add(campoHora);
        add(new JLabel("Descrição:")).setBounds(20, 180, 120, 25);
        add(scrollDescricao);

        add(botaoSalvar);
        add(botaoCancelar);

        botaoCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        botaoSalvar.addActionListener(e -> {
            try {
                compromissoAtual.setNomeCliente(campoNomeCliente.getText());
                compromissoAtual.setTelefone(campoTelefone.getText());
                compromissoAtual.setHora(LocalTime.parse(campoHora.getText())); 
                compromissoAtual.setDescricao(areaDescricao.getText());

                telaPrincipal.atualizarListaCompromissos();

                setVisible(false);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o compromisso. Verifique os dados e tente novamente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(parent);
    }
}
