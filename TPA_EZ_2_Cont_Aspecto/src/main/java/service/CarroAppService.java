package service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import modelo.Carro;
import modelo.Motorista;

import org.springframework.transaction.annotation.Transactional;

import anotacao.ROLE_ADMIN;
import anotacao.ROLE_USER1;
import util.Util;
import dao.CarroDAO;
import dao.MotoristaDAO;
import excecao.DataDeCarroInvalidaException;
import excecao.FaltaPrivilegioException;
import excecao.CarroNaoEncontradoException;
import excecao.ObjetoNaoEncontradoException;
import excecao.MotoristaNaoEncontradoException;
import excecao.ValorDeCarroInvalidoException;

// @Service
public class CarroAppService
{	
	private MotoristaDAO motoristaDAO = null;
	
	private CarroDAO carroDAO = null;
	
	public void setMotoristaDAO(MotoristaDAO motoristaDAO)
	{	this.motoristaDAO = motoristaDAO;
	}

	public void setCarroDAO(CarroDAO carroDAO)
	{	this.carroDAO = carroDAO;
	}
	
	public Carro recuperaUmCarroComMotorista(long numero) throws CarroNaoEncontradoException {
		try {
			return carroDAO.recuperaUmCarroComMotorista(numero);
		} catch (ObjetoNaoEncontradoException e) {
			throw new CarroNaoEncontradoException("Carro não encontrado");
		}
	}
	
	@Transactional
	@ROLE_ADMIN
	@ROLE_USER1
	public void altera(Carro umCarro)
		throws CarroNaoEncontradoException, FaltaPrivilegioException
	{	try
		{	
			carroDAO.getPorIdComLock(umCarro.getId());
			carroDAO.altera(umCarro);
		}
		catch(ObjetoNaoEncontradoException e)
		{	throw new CarroNaoEncontradoException("carro não encontrado");
		}
	}
	
	@Transactional
	@ROLE_ADMIN
	@ROLE_USER1
	public long inclui(Carro umCarro) 
		throws MotoristaNaoEncontradoException, 
		       ValorDeCarroInvalidoException, 
		       DataDeCarroInvalidaException,
		       FaltaPrivilegioException
	{	
		
		Motorista umMotorista = umCarro.getMotorista();
		
		try
		{	motoristaDAO.getPorIdComLock(umMotorista.getId());
		}
		catch(ObjetoNaoEncontradoException e)
		{	
			throw new MotoristaNaoEncontradoException("Motorista não encontado");
		}

		Carro ultimoCarro; 
		try
		{	ultimoCarro = carroDAO.recuperaUltimoCarro(umMotorista);
		}
		catch(ObjetoNaoEncontradoException e)
		{	ultimoCarro = null;	
		}

		//double valorUltimoCarro;
		Calendar dataUltimoCarro;  
		
		if (ultimoCarro == null)
		{	//valorUltimoCarro = umMotorista.getCarroMinimo() - 1;
			dataUltimoCarro  = umMotorista.getDataCadastro();
		}
		else
		{	//valorUltimoCarro = ultimoCarro.getValor();
			dataUltimoCarro  = ultimoCarro.getDataFabricacao();
		}
		
		/*
		if(umCarro.getValor() <= valorUltimoCarro)
		{	throw new ValorDeCarroInvalidoException("O valor do Carro tem que ser superior a " + valorUltimoCarro);
		}
		*/
		
		if(umCarro.getDataFabricacao().before(dataUltimoCarro))
		{	throw new DataDeCarroInvalidaException("A data de fabricação do Carro não pode ser anterior a " 
				+ Util.calendarToStr(dataUltimoCarro));
		}

		GregorianCalendar hoje = new GregorianCalendar();
	
		if(umCarro.getDataFabricacao().after(hoje))
		{	throw new DataDeCarroInvalidaException("A data de fabricação do Carro não pode ser posterior à data de hoje: " 
				+ Util.calendarToStr(hoje));
		}
		
		Carro carro = carroDAO.inclui(umCarro);

		return carro.getId();
	}
	
	@Transactional
	@ROLE_ADMIN
	public void exclui(Carro umCarro) 
		throws CarroNaoEncontradoException, FaltaPrivilegioException 
	{	
		try
		{	umCarro = carroDAO.getPorId(umCarro.getId());
			carroDAO.exclui(umCarro);	
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CarroNaoEncontradoException("Carro não encontrado.");
		}
	}
	
	public List<Carro> recuperaPeloModelo(String nome, int min,int max) {
		return carroDAO.recuperaPeloNome(nome.toUpperCase()+"%", min, max);
	}
	
	public long recuperaQtdPeloNome(String nome) 
	{	
		return carroDAO.recuperaQtdPeloNome(nome + "%");
	}

	public Carro recuperaUmCarro(long numero) 
		throws CarroNaoEncontradoException
	{	try
		{	return carroDAO.getPorId(numero);
		} 
		catch(ObjetoNaoEncontradoException e)
		{	throw new CarroNaoEncontradoException("Carro não encontrado");
		}
	}

	public List<Carro> recuperaCarros()
	{	return carroDAO.recuperaListaDeCarros();
	}
}