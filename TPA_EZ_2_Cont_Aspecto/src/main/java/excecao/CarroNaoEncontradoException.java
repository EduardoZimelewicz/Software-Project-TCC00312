package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class CarroNaoEncontradoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public CarroNaoEncontradoException()
	{	super();
	}

	public CarroNaoEncontradoException(String msg)
	{	super(msg);
	}
}	