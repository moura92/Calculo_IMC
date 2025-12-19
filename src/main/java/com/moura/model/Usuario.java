package com.moura.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import com.moura.exception.ParametroInvalidoException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 80)
	private String nome;
	@Column(nullable = false)
	private Integer idade;
	@Column(nullable = false)
	private Double altura;
	@Column(nullable = false)
	private Double peso;
	@Column(nullable = false, precision = 5, scale = 2)
	private BigDecimal imc;
	@Column(name = "Classificacao_IMC", length = 30)
	private String classificacao;

	public void calculoImc() {
		if (peso == null || altura == null || peso <= 0.0 || altura <= 0.0) {
			throw new ParametroInvalidoException("Peso e altura devem ser informados!");
		}

		BigDecimal peso = BigDecimal.valueOf(getPeso());
		BigDecimal altura = BigDecimal.valueOf(getAltura());
		
		BigDecimal imcCalculado = peso.divide(altura.pow(2), 2, RoundingMode.HALF_UP);
		
		this.imc = imcCalculado;
		this.classificacao = classificarImc(imcCalculado);

	}

	private String classificarImc(BigDecimal imc) {
		double valor = imc.doubleValue();
		
		if (valor < 18.5) {return "Magreza";} 
		else if (valor < 25) {return "Peso normal";} 
		else if (valor < 30) {return "Sobrepeso";} 
		else if (valor < 35) {return "Obesidade grau I";} 
		else if (valor < 40) {return "Obesidade grau II";} 
		return "Obesidade grau III";
	}

	public Usuario() {
	}

	public Usuario(Long id, String nome, int idade, double altura, double peso, BigDecimal imc, String classificacao) {
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


	public String getClassificacao() {
		return classificacao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(altura, classificacao, id, idade, imc, nome, peso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(altura, other.altura) && Objects.equals(classificacao, other.classificacao)
				&& Objects.equals(id, other.id) && Objects.equals(idade, other.idade) && Objects.equals(imc, other.imc)
				&& Objects.equals(nome, other.nome) && Objects.equals(peso, other.peso);
	}



}