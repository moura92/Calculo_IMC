package com.moura.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="usuario")
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "Nome", nullable = false, length = 80)
	private String nome;
	@Column(name = "Idade", nullable = false)
	private int idade;
	@Column(name = "Altura", nullable =false)
	private Double altura;
	@Column(name = "Peso", nullable = false)
	private Double peso;
	@Column(name = "IMC", nullable = false)
	private Double imc;
	@Column(name = "Classificação IMC")
	private String classificaçao;
	
	
	public Usuario() {
	}


	public Usuario(Long id, String nome, int idade, double altura, double peso, double imc, String classificaçao) {
		this.id = id;
		this.nome = nome;
		this.idade = idade;
		this.altura = altura;
		
		this.peso = peso;
		this.imc = imc;
		this.classificaçao = classificaçao;
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


	public double getImc() {
		return imc;
	}


	public void setImc(double imc) {
		this.imc = imc;
	}


	public String getClassificaçao() {
		return classificaçao;
	}


	public void setClassificaçao(String classificaçao) {
		this.classificaçao = classificaçao;
	}


	@Override
	public int hashCode() {
		return Objects.hash(altura, classificaçao, id, idade, imc, nome, peso);
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
		return Double.doubleToLongBits(altura) == Double.doubleToLongBits(other.altura)
				&& Objects.equals(classificaçao, other.classificaçao) && Objects.equals(id, other.id)
				&& idade == other.idade && Double.doubleToLongBits(imc) == Double.doubleToLongBits(other.imc)
				&& Objects.equals(nome, other.nome)
				&& Double.doubleToLongBits(peso) == Double.doubleToLongBits(other.peso);
	}
	
	
}