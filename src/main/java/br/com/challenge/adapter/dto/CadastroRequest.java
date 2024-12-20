package br.com.challenge.adapter.dto;

import br.com.challenge.adapter.validator.CreateCadastroGroup;
import br.com.challenge.adapter.validator.NullOrNotBlank;
import br.com.challenge.adapter.validator.UpdateCadastroGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CadastroRequest {

    @NotBlank(message = "{validation.nome.blank}", groups = CreateCadastroGroup.class)
    @NullOrNotBlank(message = "{validation.nome.blank}", groups = UpdateCadastroGroup.class)
    private String nome;

    @NotBlank(message = "{validation.sobrenome.blank}", groups = CreateCadastroGroup.class)
    @NullOrNotBlank(message = "{validation.sobrenome.blank}", groups = UpdateCadastroGroup.class)
    private String sobrenome;

    @NotBlank(message = "{validation.cpf.blank}", groups = CreateCadastroGroup.class)
    @Size(min = 11, max = 14, message = "{validation.cpf.size}", groups = CreateCadastroGroup.class)
    private String cpf;

    @NotBlank(message = "{validation.email.blank}", groups = CreateCadastroGroup.class)
    @Email(message = "{validation.email.invalid}", groups = {CreateCadastroGroup.class, UpdateCadastroGroup.class})
    @NullOrNotBlank(message = "{validation.sobrenome.blank}", groups = UpdateCadastroGroup.class)
    private String email;

    @Min(value = 18, message = "{validation.idade.min}", groups = CreateCadastroGroup.class)
    @Max(value = 120, message = "{validation.idade.max}", groups = CreateCadastroGroup.class)
    private int idade;

    @NotBlank(message = "{validation.idade.blank}", groups = CreateCadastroGroup.class)
    @NullOrNotBlank(message = "{validation.sobrenome.blank}", groups = UpdateCadastroGroup.class)
    private String pais;

    public CadastroRequest(String nome, String sobrenome, String cpf, String email, int idade, String pais) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.email = email;
        this.idade = idade;
        this.pais = pais;
    }

    public CadastroRequest(String nome, String sobrenome, String email, String pais) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.pais = pais;
    }

    public CadastroRequest() {
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
}
