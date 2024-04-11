package Core;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Compromisso implements Comparable<Compromisso>, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nomeCliente;
    private String telefone;
    private LocalDate data;
    private LocalTime hora;
    private String descricao;
    private boolean executado;

    public Compromisso(String nomeCliente, String telefone, LocalDate data, LocalTime hora, String descricao) {
        this.nomeCliente = nomeCliente;
        this.setTelefone(telefone); 
        this.data = data;
        this.hora = hora;
        this.descricao = descricao;
        this.executado = false; 
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return hora;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isExecutado() {
        return executado;
    }

    public void setTelefone(String telefone) {
        if (telefone.matches("\\(\\d{2}\\)\\d{5}-\\d{4}")) {
            this.telefone = telefone;
        } else {
            throw new IllegalArgumentException("Formato de telefone inválido.");
        }
    }

    public void setExecutado(boolean executado) {
        this.executado = executado;
    }

    @Override
    public String toString() {
        return "Compromisso{" +
                "nomeCliente='" + nomeCliente + '\'' +
                ", telefone='" + telefone + '\'' +
                ", data=" + data +
                ", hora=" + hora +
                ", descricao='" + descricao + '\'' +
                ", executado=" + executado +
                '}';
    }

    @Override
    public int compareTo(Compromisso outro) {
        int comparacaoData = this.data.compareTo(outro.getData());
        if (comparacaoData == 0) {
            return this.hora.compareTo(outro.getHora());
        }
        return comparacaoData;
    }
}
