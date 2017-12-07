package excecao;

import anotacao.ExcecaoDeAplicacao;

@ExcecaoDeAplicacao
public class ValorDeCarroInvalidoException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public ValorDeCarroInvalidoException()
	{	super();
	}

	public ValorDeCarroInvalidoException(String msg)
	{	super(msg);
	}
}	