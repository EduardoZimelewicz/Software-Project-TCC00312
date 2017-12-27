package excecao;

import anotacao.ExcecaoDeAplicacao;
@ExcecaoDeAplicacao
public class FaltaPrivilegioException extends Exception
{	
	private final static long serialVersionUID = 1;
	
	public FaltaPrivilegioException()
	{	super();
	}

	public FaltaPrivilegioException(String msg)
	{	super(msg);
	}
}	
