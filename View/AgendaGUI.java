package View;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import Core.ListaDuplamenteEncadeada;
import java.awt.Font; 

public class AgendaGUI extends JFrame {

    private static final long serialVersionUID = -6946979306532456906L;

    public AgendaGUI() {
        super("Bem-Vindo à Adobojenda");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        JLabel mensagemBoasVindas = new JLabel("<html><center>Bem-vindo à Adobojenda!<br>Sua agenda confiável.</center></html>", JLabel.CENTER);
        mensagemBoasVindas.setFont(new Font("Georgia", Font.PLAIN, 21));
        mensagemBoasVindas.setBounds(10, 10, 364, 86);
        getContentPane().add(mensagemBoasVindas, BorderLayout.CENTER);

        JButton botaoEntrar = new JButton("Entrar");
        botaoEntrar.setFont(new Font("Verdana", Font.PLAIN, 20));
        botaoEntrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ListaDuplamenteEncadeada listaCompromissos = new ListaDuplamenteEncadeada();
                
                TelaPrincipal telaPrincipal = new TelaPrincipal(listaCompromissos);
                telaPrincipal.setVisible(true);
                AgendaGUI.this.setVisible(false);
                AgendaGUI.this.dispose();
            }
        });
        botaoEntrar.setBounds(10, 107, 364, 43);
        getContentPane().add(botaoEntrar, BorderLayout.SOUTH);
        
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AgendaGUI().setVisible(true);
            }
        });
    }
}
