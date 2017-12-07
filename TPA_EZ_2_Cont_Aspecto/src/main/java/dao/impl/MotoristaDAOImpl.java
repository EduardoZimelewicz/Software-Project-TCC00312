package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import modelo.Motorista;

import org.springframework.stereotype.Repository;

import dao.MotoristaDAO;
import excecao.ObjetoNaoEncontradoException;

public abstract class MotoristaDAOImpl
extends JPADaoGenerico<Motorista, Long> implements MotoristaDAO 
{   
	public MotoristaDAOImpl()
	{ 	super(Motorista.class);  
	}
}

/*
@Repository
public class MotoristaDAOImpl implements MotoristaDAO
{	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Motorista umMotorista) 
	{	
    	em.persist(umMotorista);
		return umMotorista.getId();
	}

	public void altera(Motorista umMotorista) 
		throws ObjetoNaoEncontradoException 
	{	
		Motorista Motorista = em.find(Motorista.class, umMotorista.getId(), LockModeType.PESSIMISTIC_WRITE);
		
		if(Motorista == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		em.merge(umMotorista);
	}

    public void exclui(long id) 
		throws ObjetoNaoEncontradoException 
	{	
		Motorista Motorista = em.find(Motorista.class, id, LockModeType.PESSIMISTIC_WRITE);
		
		if(Motorista == null)
		{	throw new ObjetoNaoEncontradoException();
		}
		
		em.remove(Motorista);
	}

    public Motorista recuperaUmMotorista(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Motorista umMotorista = (Motorista)em.find(Motorista.class, new Long(numero));
			
		if (umMotorista == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umMotorista;
	}

	public Motorista recuperaUmMotoristaComLock(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Motorista umMotorista = (Motorista)em
			.find(Motorista.class, new Long(numero), LockModeType.PESSIMISTIC_WRITE);

		if (umMotorista == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umMotorista;
	}

	public Motorista recuperaUmMotoristaECarros(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		try
		{	String busca = "select m from Motorista m " +
			               "left outer join fetch m.carros c " +
			               "where m.id = :id";

			Motorista umMotorista =
				(Motorista) em.createQuery(busca)
						    .setParameter("id", numero)
						    .getSingleResult();
			return umMotorista;
		} 
		catch(NoResultException e)
		{	throw new ObjetoNaoEncontradoException();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Motorista> recuperaMotoristaECarros()
	{	
		List<Motorista> Motoristas = em
			.createQuery("select distinct m from Motorista m " +
					     "left outer join fetch m.carros c " +
					     "order by m.id asc")
			.getResultList();

		return Motoristas;
	}

}
*/