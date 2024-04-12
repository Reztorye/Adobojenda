package View;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class FormularioAdicionar extends JDialog {
	private static final long serialVersionUID = -6272220344300236315L;
	private PlaceholderTextField campoNomeCliente, campoTelefone, campoData, campoHora;
    private JTextArea areaDescricao;
    private JButton botaoSalvar, botaoCancelar;
    private JScrollPane scrollDescricao;
    private ListaDuplamenteEncadeada listaCompromissos;
    private TelaPrincipal telaPrincipal;

    public FormularioAdicionar(JFrame parent, ListaDuplamenteEncadeada listaCompromissos, TelaPrincipal telaPrincipal) {
        super(parent, "Adicionar Compromisso", true);
        setSize(350, 450);
        setLayout(null);

        campoNomeCliente = new PlaceholderTextField("Nome Completo");
        campoTelefone = new PlaceholderTextField("(XX) 99999-9999");
        campoData = new PlaceholderTextField("dd/MM/aaaa");
        campoHora = new PlaceholderTextField("HH:mm");
        areaDescricao = new JTextArea();
        scrollDescricao = new JScrollPane(areaDescricao, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        
        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        campoNomeCliente.setBounds(20, 20, 300, 25);
        campoTelefone.setBounds(20, 60, 300, 25);
        campoData.setBounds(20, 100, 300, 25);
        campoHora.setBounds(20, 140, 300, 25);
        scrollDescricao.setBounds(20, 180, 300, 100);

        botaoSalvar.setBounds(50, 300, 100, 30);
        botaoCancelar.setBounds(200, 300, 100, 30);

        add(new JLabel("Nome do Cliente:")).setBounds(20, 0, 120, 20);
        add(new JLabel("Telefone:")).setBounds(20, 40, 120, 20);
        add(new JLabel("Data (dd/mm/aaaa):")).setBounds(20, 80, 140, 20);
        add(new JLabel("Hora (HH:mm):")).setBounds(20, 120, 120, 20);
        add(new JLabel("Descrição:")).setBounds(20, 160, 120, 20);

        add(campoNomeCliente);
        add(campoTelefone);
        add(campoData);
        add(campoHora);
        add(scrollDescricao);
        add(botaoSalvar);
        add(botaoCancelar);

        botaoCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        botaoSalvar.addActionListener(e -> {
            try {
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

                String nomeCliente = campoNomeCliente.getText().trim();
                String telefone = campoTelefone.getText().trim();
                String dataString = campoData.getText().trim();
                String horaString = campoHora.getText().trim();
                String descricao = areaDescricao.getText().trim();

                if (nomeCliente.isEmpty() || telefone.isEmpty() || dataString.isEmpty() || horaString.isEmpty() || descricao.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                LocalDate data = LocalDate.parse(dataString, dateFormatter);
                LocalTime hora = LocalTime.parse(horaString, timeFormatter);
                Compromisso novoCompromisso = new Compromisso(nomeCliente, telefone, data, hora, descricao);
                listaCompromissos.inserir(novoCompromisso);
                telaPrincipal.atualizarListaCompromissos();

                setVisible(false);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Verifique o formato da data e da hora (dd/MM/aaaa, HH:mm).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o compromisso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(parent);
    }

	public ListaDuplamenteEncadeada getListaCompromissos() {
		return listaCompromissos;
	}

	public void setListaCompromissos(ListaDuplamenteEncadeada listaCompromissos) {
		this.listaCompromissos = listaCompromissos;
	}

	public TelaPrincipal getTelaPrincipal() {
		return telaPrincipal;
	}

	public void setTelaPrincipal(TelaPrincipal telaPrincipal) {
		this.telaPrincipal = telaPrincipal;
	}
}
