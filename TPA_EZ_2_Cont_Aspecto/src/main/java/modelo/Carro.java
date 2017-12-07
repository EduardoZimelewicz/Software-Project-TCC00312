package modelo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import util.Util;

@NamedQueries(
		{	@NamedQuery
			(	name = "Carro.recuperaListaDeCarros",
				query = "select c from Carro c order by c.id"
			),
			@NamedQuery
			(	name = "Carro.recuperaUltimoCarro",
				query = "select c from Carro c where c.motorista = ?1 order by c.id desc"
			),
			
			@NamedQuery
			(	name = "Carro.recuperaPeloNome",
				query = "select c from Carro c left outer join fetch c.motorista where c.modelo like ?1 order by c.modelo asc "
			),
			
			@NamedQuery
			(	name = "Carro.recuperaQtdPeloNome",
				query = "select count(c) from Carro c where c.modelo like ?1"
			),
			
			@NamedQuery
			(	name = "Carro.recuperaUmCarroComMotorista",
				query = "select c from Carro c left outer join fetch c.motorista where c.id = ?1"
			)
		})

@Entity
@Table(name="CARRO")
@SequenceGenerator(name="SEQUENCIA_CARRO", 
		           sequenceName="SEQ_CARRO",
		           allocationSize=1)

public class Carro
{	
	private Long id;
	private double valor;
	private String placa;
	private String modelo;
	private Calendar dataFabricacao;

	private Motorista motorista;

	// ********* Construtores *********

	public Carro()
	{
	}

	public Carro(double valor, String placa, String modelo, Calendar dataFabricacao, Motorista motorista)
	{	this.valor = valor;
		this.placa = placa;
		this.modelo = modelo;
		this.dataFabricacao = dataFabricacao;
		this.motorista = motorista;
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA_CARRO")
	@Column(name="ID")
	public Long getId()
	{	return id;
	}

	@Column(nullable = false)
	public double getValor()
	{	return valor;
	}
	
	@Column(name="DATA_FABRICACAO")
	@Temporal(value = TemporalType.DATE)
	public Calendar getDataFabricacao()
	{	return dataFabricacao;
	}
	
	@Column(name="PLACA")
	public String getPlaca(){
		return placa;
	}
	
	@Column(name="MODELO")
	public String getModelo(){
		return modelo;
	}
	
	@Transient
	public String getDataFabricacaoMasc()
	{	return Util.calendarToStr(dataFabricacao);
	}
	
	// ********* Métodos do Tipo Set *********

	@SuppressWarnings("unused")
	private void setId(Long id)
	{	this.id = id;
	}

	public void setValor(double valor)
	{	this.valor = valor;
	}
	
	public void setPlaca(String placa){
		this.placa = placa;
	}
	
	public void setModelo(String modelo){
		this.modelo = modelo;
	}

	public void setDataFabricacao(Calendar dataFabricacao)
	{	this.dataFabricacao = dataFabricacao;
	}

	// ********* Métodos para Associações *********

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MOTORISTA_ID")
	public Motorista getMotorista()
	{	return motorista;
	}
	
	public void setMotorista(Motorista motorista)
	{	this.motorista = motorista;
	}
}	