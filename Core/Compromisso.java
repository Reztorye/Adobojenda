package Core;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Compromisso implements Comparable<Compromisso>, Serializable {
    private static final long serialVersionUID = 1L;
    private static int contadorId = 0; 
    private int id;
    private String nomeCliente;
    private String telefone;
    private LocalDate data;
    private LocalTime hora;
    private LocalTime horaFim;  
    private String descricao;
    private boolean executado;
    private LocalDateTime horaExecutado;


    public Compromisso(String nomeCliente, String telefone, LocalDate data, LocalTime hora, String descricao) {
        this.id = ++contadorId;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone; 
        this.data = data;
        this.hora = hora;
        this.horaFim = hora.plusHours(1); 
        this.descricao = descricao;
        this.executado = false; 
    }

    // Getters e Setters
    public int getId() {
        return id;
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

    public LocalTime getHoraFim() {  
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) { 
        this.horaFim = horaFim;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isExecutado() {
        return executado;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
        this.horaFim = hora.plusHours(1); 
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setExecutado(boolean executado) {
        this.executado = executado;
    }
    
    public LocalDateTime getHoraExecutado() {
        return horaExecutado;
    }

    public void setHoraExecutado(LocalDateTime horaExecutado) {
        this.horaExecutado = horaExecutado;
    }

    @Override
    public int compareTo(Compromisso outro) {
        int comparacaoData = this.data.compareTo(outro.getData());
        if (comparacaoData == 0) {
            return this.hora.compareTo(outro.getHora());
        }
        return comparacaoData;
    }

    @Override
    public String toString() {
        return "Compromisso{" +
                "id=" + id +
                ", nomeCliente='" + nomeCliente + '\'' +
                ", telefone='" + telefone + '\'' +
                ", data=" + data +
                ", hora=" + hora +
                ", horaFim=" + horaFim +
                ", descricao='" + descricao + '\'' +
                ", executado=" + executado +
                '}';
    }
}
