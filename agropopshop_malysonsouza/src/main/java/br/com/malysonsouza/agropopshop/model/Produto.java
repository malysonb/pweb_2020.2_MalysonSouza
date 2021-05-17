package br.com.malysonsouza.agropopshop.model;

import java.io.Serializable;
import java.util.Base64;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="produto")
public class Produto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String nomeProduto;
	@Column(nullable=false)
	private String marca;
	private float altura;
	private float largura;  
	private float profundidade;
	private float peso;
	@Column(nullable=false)
	private double preco;
	@Lob
	private byte[] foto;

	public String getFotoBase64(){
		return Base64.getEncoder().encodeToString(this.foto);
	}
}
