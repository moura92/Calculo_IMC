package com.moura.integrationtests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id" , "nome", "idade", "peso", "altura", "imc", "classificacao_IMC", "data"})
@XmlRootElement
public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Integer idade;
	private Double altura;
	private Double peso;
	private BigDecimal imc;
    @JsonProperty("classificacao_IMC")
	private String classificacao;
	private Boolean enabled;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date data;


	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public String getNome() {return nome;}
	public void setNome(String nome) {this.nome = nome;}

	public int getIdade() {return idade;}
	public void setIdade(int idade) {this.idade = idade;}

	public double getAltura() {return altura;}
	public void setAltura(double altura) {this.altura = altura;}

	public double getPeso() {return peso;}
	public void setPeso(double peso) {this.peso = peso;}

	public BigDecimal getImc() {return imc;}
	public void setImc(BigDecimal imc) {this.imc = imc;}

	public String getClassificacao() {return classificacao;}
	public void setClassificacao(String classificacao) {this.classificacao = classificacao;}

    public Date getData() {return data;}
    public void setData(Date data) {this.data = data;}

	public Boolean getEnabled() {return enabled;}
	public void setEnabled(Boolean enabled) {this.enabled = enabled;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		UsuarioDTO that = (UsuarioDTO) o;
		return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(idade, that.idade) && Objects.equals(altura, that.altura) && Objects.equals(peso, that.peso) && Objects.equals(imc, that.imc) && Objects.equals(classificacao, that.classificacao) && Objects.equals(enabled, that.enabled) && Objects.equals(data, that.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, idade, altura, peso, imc, classificacao, enabled, data);
	}
}
