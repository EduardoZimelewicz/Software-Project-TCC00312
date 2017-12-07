package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class DataDeCarroInvalidaException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public DataDeCarroInvalidaException()
	{	super();
	}

	public DataDeCarroInvalidaException(String msg)
	{	super(msg);
	}
}	