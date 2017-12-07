package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class ObjetoNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ObjetoNaoEncontradoException()
	{
	}
}	