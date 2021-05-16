package br.com.malysonsouza.agropopshop.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="cliente")
public class Cliente implements Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String nome;
	private String email;
	private String genero;
	private String estado;
	private String cidade;
	private String bairro;
	private String endereco;
	private String telefone;
	private String CEP;
	
	@Column(nullable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "clienteDependente")
	private List<Dependente> dependentes;

	@OneToMany(mappedBy = "clientePedido")
	private List<Pedido> pedidos;
}
