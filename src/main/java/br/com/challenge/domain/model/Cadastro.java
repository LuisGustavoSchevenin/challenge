package br.com.challenge.domain.model;

import java.util.UUID;

public class Cadastro {

    private String cadastroId;
    private String nome;
    private String sobrenome;
    private String cpf;
    private int idade;
    private String pais;
    private String dataCriacao;
    private String dataAtualizacao;


    protected Cadastro(String nome, String sobrenome, String cpf, int idade, String pais) {
        this.cadastroId = UUID.nameUUIDFromBytes(cpf.getBytes()).toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.idade = idade;
        this.pais = pais;
    }

    public static Cadastro.Builder builder() {
        return new Cadastro.Builder();
    }

    public String getCadastroId() {
        return cadastroId;
    }

    public String getNome() {
        return nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getPais() {
        return pais;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(String dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public static class Builder {

        private String nome;
        private String sobrenome;
        private String cpf;
        private int idade;
        private String pais;

        public Builder nome(final String nome) {
            this.nome = nome;
            return this;
        }

        public Builder sobrenome(final String sobrenome) {
            this.sobrenome = sobrenome;
            return this;
        }

        public Builder cpf(final String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder idade(final int idade) {
            this.idade = idade;
            return this;
        }

        public Builder pais(final String pais) {
            this.pais = pais;
            return this;
        }

        public Cadastro build() {
            return new Cadastro(nome, sobrenome, cpf, idade, pais);
        }
    }
}
