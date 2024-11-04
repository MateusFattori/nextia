package br.com.fiap.nextia.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClienteDTO {
    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String genero;
    private LocalDate dt_nascimento;
    private String telefone;
    private int pontos;
    private String fidelidade;
    private LocalDate dt_filiacao;
    private String perfilCompra;
    private String classificacao;
}
