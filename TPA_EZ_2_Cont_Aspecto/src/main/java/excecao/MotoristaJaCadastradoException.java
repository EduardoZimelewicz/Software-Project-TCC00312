package excecao;

import anotacao.ConstraintViolada;
import anotacao.ExcecaoDeAplicacao;

@ConstraintViolada(nome="MOTORISTA_NOME_UN")
@ExcecaoDeAplicacao
public class MotoristaJaCadastradoException extends RuntimeException
{	
	private final static long serialVersionUID = 1;
	
	public MotoristaJaCadastradoException()
	{	super();
	}

	public MotoristaJaCadastradoException(String msg)
	{	super(msg);
	}
}	