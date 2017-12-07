package dao;

import java.util.List;
import java.util.Set;

import anotacao.RecuperaConjunto;
import anotacao.RecuperaLista;
import anotacao.RecuperaListaPaginada;
import anotacao.RecuperaObjeto;
import modelo.Motorista;
import excecao.ObjetoNaoEncontradoException;

public interface MotoristaDAO extends DaoGenerico<Motorista, Long>
{	
	@RecuperaObjeto
	Motorista recuperaUmMotoristaECarros(long numero) 
		throws ObjetoNaoEncontradoException;

	@RecuperaLista
	List<Motorista> recuperaListaDeMotoristas();
	
	@RecuperaObjeto
	long recuperaQtdPeloNome(String nome);
	
	@RecuperaListaPaginada
	public List<Motorista> recuperaPeloNome(String nome,int min,int max);
	
	@RecuperaLista
	List<Motorista> recuperaListaDeMotoristasECarros();

	@RecuperaConjunto
	Set<Motorista> recuperaConjuntoDeMotoristasECarros();
	/*
	public long inclui(Motorista umMotorista); 

	public void altera(Motorista umMotorista)
		throws ObjetoNaoEncontradoException; 
	
	public void exclui(long id) 
		throws ObjetoNaoEncontradoException; 
	
	public Motorista recuperaUmMotorista(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public Motorista recuperaUmMotoristaComLock(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public Motorista recuperaUmMotoristaECarros(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Motorista> recuperaMotoristaECarros();
	*/
}