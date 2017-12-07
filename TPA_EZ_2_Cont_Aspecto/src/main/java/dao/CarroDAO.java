package dao;

import java.util.List;

import anotacao.RecuperaLista;
import anotacao.RecuperaListaPaginada;
import anotacao.RecuperaObjeto;
import anotacao.RecuperaUltimoOuPrimeiro;
import modelo.Carro;
import modelo.Motorista;
import excecao.ObjetoNaoEncontradoException;

public interface CarroDAO extends DaoGenerico<Carro, Long>
{	
	
	@RecuperaLista
	List<Carro> recuperaListaDeCarros();
	
	@RecuperaUltimoOuPrimeiro
	Carro recuperaUltimoCarro(Motorista motorista)
		throws ObjetoNaoEncontradoException; 
	
	@RecuperaObjeto
	long recuperaQtdPeloNome(String nome);
	
	@RecuperaListaPaginada
	public List<Carro> recuperaPeloNome(String nome,int min,int max);
	
	@RecuperaObjeto
	Carro recuperaUmCarroComMotorista(long id) throws ObjetoNaoEncontradoException;
	/*
	public long inclui(Carro umCarro);

	public void exclui(Carro umCarro) 
		throws ObjetoNaoEncontradoException; 
	
	public Carro recuperaUmCarro(long numero) 
		throws ObjetoNaoEncontradoException; 
	
	public List<Carro> recuperaCarros();
	
	public Carro recuperaUltimoCarro(Motorista motorista)
		throws ObjetoNaoEncontradoException; 
	*/
}
