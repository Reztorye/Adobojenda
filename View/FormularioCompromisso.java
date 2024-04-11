package View;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;

import Core.Compromisso;
import Core.ListaDuplamenteEncadeada;

public class FormularioCompromisso extends JDialog {

    private static final long serialVersionUID = -6638740306635907415L;
    private PlaceholderTextField campoNomeCliente, campoTelefone, campoData, campoHora;
    private JTextArea areaDescricao;
    private JButton botaoSalvar, botaoCancelar;
    private ListaDuplamenteEncadeada listaCompromissos;
    private TelaPrincipal telaPrincipal;

    public FormularioCompromisso(JFrame parent, ListaDuplamenteEncadeada listaCompromissos, TelaPrincipal telaPrincipal) {
        super(parent, "Compromisso", true);
        this.listaCompromissos = listaCompromissos;
        this.telaPrincipal = telaPrincipal;
        setSize(700, 400);
        setLayout(new GridLayout(6, 2));

        campoNomeCliente = new PlaceholderTextField("Digite o nome do cliente");
        campoTelefone = new PlaceholderTextField("Digite o telefone");
        campoData = new PlaceholderTextField("Digite a data (dd/mm/aaaa)");
        campoHora = new PlaceholderTextField("Digite a hora (HH:mm)");
        areaDescricao = new JTextArea(5, 20); 

        botaoSalvar = new JButton("Salvar");
        botaoCancelar = new JButton("Cancelar");

        add(new JLabel("Nome do Cliente:"));
        add(campoNomeCliente);
        add(new JLabel("Telefone:"));
        add(campoTelefone);
        add(new JLabel("Data:"));
        add(campoData);
        add(new JLabel("Hora:"));
        add(campoHora);
        add(new JLabel("Descrição:"));
        add(new JScrollPane(areaDescricao));



        getContentPane().add(botaoSalvar);
        getContentPane().add(botaoCancelar);

        botaoCancelar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                dispose();
            }
        });

        botaoSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeCliente = campoNomeCliente.getText();
                    String telefone = campoTelefone.getText();
                    String dataString = campoData.getText();
                    String horaString = campoHora.getText();
                    String descricao = areaDescricao.getText();

                    if(nomeCliente.isEmpty() || telefone.isEmpty() || dataString.isEmpty() || horaString.isEmpty() || descricao.isEmpty()) {
                        JOptionPane.showMessageDialog(FormularioCompromisso.this, "Todos os campos devem ser preenchidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return; 
                    }

                    LocalDate data = LocalDate.parse(dataString);
                    LocalTime hora = LocalTime.parse(horaString);

                    Compromisso novoCompromisso = new Compromisso(nomeCliente, telefone, data, hora, descricao);

                    listaCompromissos.inserir(novoCompromisso);
                    
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            telaPrincipal.atualizarListaCompromissos();
                        }
                    });

                    setVisible(false);
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(FormularioCompromisso.this, "Erro ao salvar o compromisso. Verifique os dados e tente novamente.\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
	

        setLocationRelativeTo(parent);
    }
}
