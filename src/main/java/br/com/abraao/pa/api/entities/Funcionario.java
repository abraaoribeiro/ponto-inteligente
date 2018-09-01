package br.com.abraao.pa.api.entities;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import br.com.abraao.pa.api.enun.PerfilEnum;

@Entity
@Table(name = "funcionario")
public class Funcionario implements Serializable {

	private static final long serialVersionUID = -2;

	private Long id;
	private String nome;
	private String cpf;
	private String email;
	private BigDecimal valorHora;
	private Float qtdHorasTrabalhadaDia;
	private Float qtdHoraAlmoco;
	private PerfilEnum perfilEnum;
	private Date dateCriacao;
	private Date dateAtualicacao;
	private Empresa empresa;
	private List<Lancamento> lacamentos;

	public Funcionario() {

	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "nome", nullable = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Column(name = "cpf", nullable = false)
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Column(name = "email", nullable = false)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "valor_hora", nullable = false)
	public BigDecimal getValorHora() {
		return valorHora;
	}

	public Optional<BigDecimal> getValorHoraopt() {
		return Optional.ofNullable(valorHora);
	}

	public void setValorHora(BigDecimal valorHora) {
		this.valorHora = valorHora;
	}

	@Column(name = "qtd_hora_dia", nullable = false)
	public Float getQtdHorasTrabalhadaDia() {
		return qtdHorasTrabalhadaDia;
	}

	public void setQtdHorasTrabalhadaDia(Float qtdHorasTrabalhadaDia) {
		this.qtdHorasTrabalhadaDia = qtdHorasTrabalhadaDia;
	}

	public Optional<Float> getQtdHoradiaopt() {
		return Optional.ofNullable(qtdHorasTrabalhadaDia);
	}

	@Column(name = "qtd_hora)almoco", nullable = false)
	public Float getQtdHoraAlmoco() {
		return qtdHoraAlmoco;
	}

	public void setQtdHoraAlmoco(Float qtdHoraAlmoco) {
		this.qtdHoraAlmoco = qtdHoraAlmoco;

	}

	@Transactional
	public Optional<Float> getQtdHoraAlmocoOpt() {
		return Optional.ofNullable(qtdHoraAlmoco);
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "perfil", nullable = false)
	public PerfilEnum getPerfilEnum() {
		return perfilEnum;
	}

	public void setPerfilEnum(PerfilEnum perfilEnum) {
		this.perfilEnum = perfilEnum;
	}

	public Date getDateCriacao() {
		return dateCriacao;
	}

	public void setDateCriacao(Date dateCriacao) {
		this.dateCriacao = dateCriacao;
	}

	public Date getDateAtualicacao() {
		return dateAtualicacao;
	}

	public void setDateAtualicacao(Date dateAtualicacao) {
		this.dateAtualicacao = dateAtualicacao;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Lancamento> getLacamentos() {
		return lacamentos;
	}

	public void setLacamentos(List<Lancamento> lacamentos) {
		this.lacamentos = lacamentos;
	}

	@PreUpdate
	public void preUpdate() {
		dateAtualicacao = new Date();
	}

	@PrePersist
	public void prePersist() {
		final Date atual =  new Date();
		dateCriacao = atual;
		dateAtualicacao = atual;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", email=" + email + ", valorHora="
				+ valorHora + ", qtdHorasTrabalhadaDia=" + qtdHorasTrabalhadaDia + ", qtdHoraAlmoco=" + qtdHoraAlmoco
				+ ", perfilEnum=" + perfilEnum + ", dateCriacao=" + dateCriacao + ", dateAtualicacao=" + dateAtualicacao
				+ ", empresa=" + empresa + "]";
	}

}
