package br.edu.idp.disciplinas.poo2023_1;


import java.time.LocalDateTime;


public class Pessoa {

    private String nome;
    private Integer quantidadeAcesso;
    private String naturalidade;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getQuantidadeAcesso() {
        return quantidadeAcesso;
    }

    public void setQuantidadeAcesso(Integer quantidadeAcesso) {
        this.quantidadeAcesso = quantidadeAcesso;
    }

    public String getNaturalidade() {
        return naturalidade;
    }

    public void setNaturalidade(String naturalidade) {
        this.naturalidade = naturalidade;
    }

}
