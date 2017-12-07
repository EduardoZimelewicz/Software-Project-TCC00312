package dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import modelo.Carro;
import modelo.Motorista;
import dao.CarroDAO;
import excecao.ObjetoNaoEncontradoException;

public abstract class CarroDAOImpl 
extends JPADaoGenerico<Carro, Long> implements CarroDAO
{
	public CarroDAOImpl() 
	{	super(Carro.class);		
	}
}


/*
@Repository
public class CarroDAOImpl implements CarroDAO
{	
	@PersistenceContext
	private EntityManager em;

    public long inclui(Carro umCarro) 
	{	em.persist(umCarro);
		return umCarro.getId();
	}

    public void exclui(Carro umCarro) 
		throws ObjetoNaoEncontradoException 
	{	
    	Carro carro = em.find(Carro.class, new Long(umCarro.getId()), LockModeType.PESSIMISTIC_WRITE);
    	
    	if (carro == null)
    	{	throw new ObjetoNaoEncontradoException();	
		}
    	
		em.remove(carro);
	}

	public Carro recuperaUmCarro(long numero) 
		throws ObjetoNaoEncontradoException 
	{	
		Carro umCarro = (Carro)em.find(Carro.class, new Long(numero));

		if(umCarro == null)
		{	throw new ObjetoNaoEncontradoException();
		}

		return umCarro;
	}

	@SuppressWarnings("unchecked")
	public List<Carro> recuperaCarros()
	{	
		return em.createQuery("select c from Carro c order by c.id")
				 .getResultList();
	}

	@SuppressWarnings("unchecked")
	public Carro recuperaUltimoCarro(Motorista motorista) 
		throws ObjetoNaoEncontradoException
	{	 
		String busca = "select c from Carro c " +
				       "where c.motorista.id = :idMotorista " +
				       "order by c.id desc"; 		

		List<Carro> carros = em.createQuery(busca)
							   .setParameter("idMotorista", motorista.getId())
							   .getResultList();

		if (carros.isEmpty()) 
			throw new ObjetoNaoEncontradoException();
		else 
			return carros.get(0);
	}
}
*/
