package modelo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import util.Util;

@NamedQueries(
		{	@NamedQuery
			(	name = "Motorista.recuperaUmMotoristaECarros",
				query = "select m from Motorista m left outer join fetch m.carros where m.id = ?1"
			),
			@NamedQuery
			(	name = "M.recuperaListaDeMotoristas",
				query = "select m from Motorista m order by m.id"
			),
			@NamedQuery
			(	name = "Motorista.recuperaListaDeMotoristasECarros",
				query = "select distinct m from Motorista m left outer join fetch m.carros order by m.id asc"
			),
			@NamedQuery
			(	name = "Motorista.recuperaConjuntoDeMotoristasECarros",
				query = "select m from Motorista m left outer join fetch m.carros order by m.id asc"
			),
			@NamedQuery
			(	name = "Motorista.recuperaMotoristasEAnimais",
				query = "select distinct m from Motorista m left outer join fetch m.carros order by m.id asc"
			),
			@NamedQuery
			(	name = "Motorista.recuperaPeloNome",
				query = "select m from Motorista m where m.nome like ?1 order by m.nome asc"
			),
			@NamedQuery
			(	name = "Motorista.recuperaQtdPeloNome",
				query = "select count(m) from Motorista m where m.nome like ?1"
			)
		})

@Entity
@Table(name="MOTORISTA")
@SequenceGenerator(name="SEQUENCIA_MOTORISTA", 
		           sequenceName="SEQ_MOTORISTA",
		           allocationSize=1)

public class Motorista
{	
	private Long id;
	private String nome;
	private String descricao;
	private String numCarteira;
	private Calendar dataCadastro;

	private List<Carro> carros = new ArrayList<Carro>();
	
	// ********* Construtores *********

	public Motorista()
	{
	}

	public Motorista(String nome, 
	               String descricao, 
	               String numCarteira, 
	               Calendar dataCadastro)
	{	this.nome = nome;
		this.descricao = descricao;
		this.numCarteira = numCarteira;
		this.dataCadastro = dataCadastro;	
	}

	// ********* Métodos do Tipo Get *********

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQUENCIA_MOTORISTA")
	@Column(name="ID")
	public Long getId()
	{	return id;
	}
	
	public String getNome()
	{	return nome;
	}
	
	public String getDescricao()
	{	return descricao;
	}
	
	@Column(name="NUM_CARTEIRA")
	public String getNumCarteira()
	{	return numCarteira;
	}

	@Column(name="DATA_CADASTRO")
	public Calendar getDataCadastro()
	{	return dataCadastro;
	}
	
	@Transient
	public String getDataCadastroMasc()
	{	return Util.calendarToStr(dataCadastro);
	}

	// ********* Métodos do Tipo Set *********

	public void setId(Long id)
	{	this.id = id;
	}

	public void setNome(String nome)
	{	this.nome = nome;
	}
	
	public void setDescricao(String descricao)
	{	this.descricao = descricao;
	}
	
	public void setNumCarteira(String numCarteira)
	{	this.numCarteira = numCarteira;
	}
	
	public void setDataCadastro(Calendar obj)
	{	this.dataCadastro = obj;	
	}
	
	// ********* Métodos para Associações *********

	@OneToMany(mappedBy="motorista")
	@OrderBy
	public List<Carro> getCarros()
	{	return carros;
	}
	
	public void setCarros(List<Carro> carros)
	{	this.carros = carros;	
	}
}

