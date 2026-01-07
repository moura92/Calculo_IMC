package com.moura.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id" , "nome", "idade", "peso", "altura", "imc", "classificacao_IMC", "data"})
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nome;
	private Integer idade;
	private Double altura;
	private Double peso;
	private BigDecimal imc;
    @JsonProperty("classificacao_IMC")
	private String classificacao;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof UsuarioDTO that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(), that.getNome()) && Objects.equals(getIdade(), that.getIdade()) && Objects.equals(getAltura(), that.getAltura()) && Objects.equals(getPeso(), that.getPeso()) && Objects.equals(getImc(), that.getImc()) && Objects.equals(getClassificacao(), that.getClassificacao()) && Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getIdade(), getAltura(), getPeso(), getImc(), getClassificacao(), getData());
    }
}
