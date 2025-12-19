package com.moura.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Integer idade;
	private Double altura;
	private Double peso;
	private BigDecimal imc;
	private String classificacao;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Long id, String nome, int idade, double altura, double peso, BigDecimal imc,
			String classificacao) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		this.peso = peso;
		this.imc = imc;
		this.classificacao = classificacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public BigDecimal getImc() {
		return imc;
	}

	public void setImc(BigDecimal imc) {
		this.imc = imc;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
}
