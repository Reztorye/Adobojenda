package View;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javax.swing.JTextField;

import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class FormularioEditar extends JDialog {
	private static final long serialVersionUID = -2499895612334573738L;
	private JTextField campoNomeCliente, campoTelefone, campoData, campoHora;
    private JTextArea areaDescricao;
    private JButton botaoSalvar, botaoCancelar;
    @SuppressWarnings("unused")
	private Compromisso compromissoAtual;

    public FormularioEditar(JFrame parent, ListaDuplamenteEncadeada listaCompromissos, TelaPrincipal telaPrincipal, Compromisso compromissoAtual) {
        super(parent, "Editar Compromisso", true);
        this.compromissoAtual = compromissoAtual; 
        setSize(350, 450);
        setLayout(null);
        setResizable(false);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        campoNomeCliente = new JTextField(compromissoAtual.getNomeCliente());
        campoTelefone = new JTextField(compromissoAtual.getTelefone());
        campoData = new JTextField(dateFormatter.format(compromissoAtual.getData()));
        campoHora = new JTextField(compromissoAtual.getHora().toString());
        areaDescricao = new JTextArea(compromissoAtual.getDescricao(), 5, 20);
        JScrollPane scrollDescricao = new JScrollPane(areaDescricao);

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        campoNomeCliente.setBounds(20, 50, 300, 30);
        campoTelefone.setBounds(20, 100, 300, 30);
        campoData.setBounds(20, 150, 300, 30);
        campoHora.setBounds(20, 200, 300, 30);
        scrollDescricao.setBounds(20, 250, 310, 100);

        botaoSalvar.setBounds(55, 360, 100, 30);
        botaoCancelar.setBounds(195, 360, 100, 30);

        add(new JLabel("Nome do Cliente:")).setBounds(20, 30, 140, 20);
        add(campoNomeCliente);
        add(new JLabel("Telefone:")).setBounds(20, 80, 140, 20);
        add(campoTelefone);
        add(new JLabel("Data:")).setBounds(20, 130, 140, 20);
        add(campoData);
        add(new JLabel("Hora:")).setBounds(20, 180, 140, 20);
        add(campoHora);
        add(new JLabel("Descrição:")).setBounds(20, 230, 140, 20);
        add(scrollDescricao);

        add(botaoSalvar);
        add(botaoCancelar);

        botaoCancelar.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        botaoSalvar.addActionListener(e -> {
            try {
            	
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                String nomeCliente = campoNomeCliente.getText().trim();
                String telefone = campoTelefone.getText().trim();
                String dataString = campoData.getText().trim();
                String horaString = campoHora.getText().trim();
                String descricao = areaDescricao.getText().trim();

                LocalDate data = LocalDate.parse(dataString, dateFormatter);
                LocalTime hora = LocalTime.parse(horaString, timeFormatter);
                LocalDateTime compromissoDateTime = LocalDateTime.of(data, hora);

                if (compromissoDateTime.isBefore(LocalDateTime.now())) {
                    JOptionPane.showMessageDialog(this, "Não é possível agendar compromissos para datas passadas.", "Erro de Agendamento", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                System.out.println(compromissoAtual);
                
                compromissoAtual.setNomeCliente(nomeCliente);
                compromissoAtual.setTelefone(telefone);
                compromissoAtual.setData(data);
                compromissoAtual.setHora(hora);
                compromissoAtual.setDescricao(descricao);
                
                telaPrincipal.atualizarListaCompromissos();
                setVisible(false);
                dispose();
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o compromisso. Verifique os dados e tente novamente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar o compromisso: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        setLocationRelativeTo(parent);
    }
}
