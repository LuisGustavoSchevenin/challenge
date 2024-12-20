package br.com.challenge.domain.model;

import br.com.challenge.configuration.annotation.Default;

import java.time.LocalDateTime;
import java.util.UUID;

public class Cadastro {

    private String cadastroId;
    private String nome;
    private String sobrenome;
    private String cpf;
    private String email;
    private int idade;
    private String pais;
    private boolean notified;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Cadastro(String nome, String sobrenome, String cpf, String email, int idade, String pais) {
        this.cadastroId = UUID.nameUUIDFromBytes(cpf.getBytes()).toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
        this.pais = pais;
    }

    @Default
    public Cadastro(String nome, String sobrenome, String cpf, String email, int idade, String pais,
                    LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.cadastroId = UUID.nameUUIDFromBytes(cpf.getBytes()).toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
        this.pais = pais;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getCadastroId() {
        return cadastroId;
    }

    public void setCadastroId(String cadastroId) {
        this.cadastroId = cadastroId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
