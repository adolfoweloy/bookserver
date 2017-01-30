package br.com.casadocodigo.registro;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.casadocodigo.estante.Estante;
import lombok.Getter;

@Entity
public class Usuario {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Getter
	private String nome;

	@Getter
	@JsonIgnore
	private Credenciais credenciais;

	@Getter
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Estante estante = new Estante();

	@Deprecated
	Usuario() {
	}

	public Usuario(String nome, Credenciais credenciais) {
		super();
		this.nome = nome;
		this.credenciais = credenciais;
	}

}
