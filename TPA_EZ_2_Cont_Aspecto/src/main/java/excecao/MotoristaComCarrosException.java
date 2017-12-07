package excecao;

import anotacao.ConstraintViolada;
import anotacao.ExcecaoDeAplicacao;

@ConstraintViolada(nome="CARRO_MOTORISTA_FK")
@ExcecaoDeAplicacao
public class MotoristaComCarrosException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public MotoristaComCarrosException()
	{	super();
	}

	public MotoristaComCarrosException(String msg)
	{	super(msg);
	}
}	