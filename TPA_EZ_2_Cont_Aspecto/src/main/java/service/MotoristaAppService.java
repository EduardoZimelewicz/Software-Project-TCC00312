package service;

import java.util.List;

import modelo.Motorista;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import anotacao.ROLE_ADMIN;
import anotacao.ROLE_USER1;
import dao.MotoristaDAO;
import excecao.ObjetoNaoEncontradoException;
import excecao.FaltaPrivilegioException;
import excecao.MotoristaNaoEncontradoException;

// @Service
public class MotoristaAppService
{	
	private MotoristaDAO motoristaDAO = null;
	
	@Autowired
	public void setMotoristaDAO(MotoristaDAO motoristaDAO)
	{	this.motoristaDAO = motoristaDAO;
	}
	
	@Transactional
	@ROLE_ADMIN
	@ROLE_USER1
	public long inclui(Motorista umMotorista) throws FaltaPrivilegioException
	{	return motoristaDAO.inclui(umMotorista).getId();
	}

	public long recuperaQtdPeloNome(String nome) 
	{	
		return motoristaDAO.recuperaQtdPeloNome(nome + "%");
	}
	
	public List<Motorista> recuperaPeloNome(String nome, int min,int max) {
		return motoristaDAO.recuperaPeloNome(nome.toUpperCase()+"%", min, max);
	}
	
	@Transactional
	@ROLE_ADMIN
	@ROLE_USER1
	public void altera(Motorista umMotorista)
		throws MotoristaNaoEncontradoException, FaltaPrivilegioException
	{	try
		{	
			motoristaDAO.getPorIdComLock(umMotorista.getId());
			motoristaDAO.altera(umMotorista);
		}
		catch(ObjetoNaoEncontradoException e)
		{	throw new MotoristaNaoEncontradoException("motorista não encontrado");
		}
	}

	@Transactional
	@ROLE_ADMIN
	public void exclui(Motorista umMotorista) 
		throws MotoristaNaoEncontradoException, FaltaPrivilegioException
	{	try
		{	
			Motorista motorista = motoristaDAO.recuperaUmMotoristaECarros(umMotorista.getId());
	
		//	if(motorista.getCarros().size() > 0)
		//	{	throw new MotoristaNaoEncontradoException("Este motorista possui Carros e não pode ser removido");
		//	}
	
			motoristaDAO.exclui(motorista);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new MotoristaNaoEncontradoException("motorista não encontrado");
		}
	}

	public Motorista recuperaUmMotorista(long numero) 
		throws MotoristaNaoEncontradoException
	{	try
		{	return motoristaDAO.getPorId(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new MotoristaNaoEncontradoException("motorista não encontrado");
		}
	}

	public Motorista recuperaUmMotoristaECarros(long numero) 
		throws MotoristaNaoEncontradoException
	{	try
		{	return motoristaDAO.recuperaUmMotoristaECarros(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new MotoristaNaoEncontradoException("motorista não encontrado");
		}
	}

	public List<Motorista> recuperaMotoristasECarros()
	{	return motoristaDAO.recuperaListaDeMotoristasECarros();
	}
}