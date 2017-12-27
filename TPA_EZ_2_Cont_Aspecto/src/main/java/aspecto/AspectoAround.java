package aspecto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.reflections.Reflections;
import org.springframework.dao.DataIntegrityViolationException;

import anotacao.ROLE_ADMIN;
import anotacao.ROLE_USER1;
import excecao.FaltaPrivilegioException;
import excecao.ViolacaoDeConstraintDesconhecidaException;
import util.PermissoesSingleton;

@Aspect
public class AspectoAround 
{
	private static Map<String, Class<?>> map = new HashMap<String, Class<?>>();
	private static List<String> listaDeNomesDeConstraints;
	private static PermissoesSingleton permissao = PermissoesSingleton.getPermissoesSingleton();
	private static ArrayList<String> permissoes = permissao.getPermissoes();
	
	static
	{
		Reflections reflections = new Reflections("excecao");

		Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(anotacao.ConstraintViolada.class);	

		for(Class<?> classe : annotated)
		{
			map.put(classe.getAnnotation(anotacao.ConstraintViolada.class).nome(), classe);
		}
		
		listaDeNomesDeConstraints = new ArrayList<String>(map.keySet());
	}
	
	@Pointcut("call(* service.*.*(..))")
	public void traduzExcecaoAround() {}

	@Around("traduzExcecaoAround()")
	public Object traduzExcecaoAround(ProceedingJoinPoint joinPoint) throws Throwable 
	{	try
		{	
			MethodSignature assinatura = (MethodSignature)joinPoint.getSignature();
			java.lang.reflect.Method metodo = assinatura.getMethod();
			
			if(metodo.isAnnotationPresent(ROLE_USER1.class)){
				if(!permissoes.contains("ROLE_USER1")) throw new FaltaPrivilegioException("O usuário "
						+ "atual não possui permissão");
			}
			
			else if(metodo.isAnnotationPresent(ROLE_ADMIN.class)){
				if(!permissoes.contains("ROLE_ADMIN")) throw new FaltaPrivilegioException("O usuário "
						+ "atual não possui permissão");
			}
			
			
			return joinPoint.proceed();
		}
		catch(org.springframework.dao.DataAccessException e)
		{	
			Throwable t = e;
			
			if( t instanceof DataIntegrityViolationException)
			{	
				t = t.getCause();
				while (t != null && !(t instanceof SQLException))
				{
					t = t.getCause();
				}
				
				String msg = (t.getMessage() != null) ? t.getMessage() : "";
				
				for(String nomeDeConstraint : listaDeNomesDeConstraints)
				{
					if(msg.indexOf(nomeDeConstraint) != -1)
					{
						throw (Exception)map.get(nomeDeConstraint).newInstance();
					}
				}
				throw new ViolacaoDeConstraintDesconhecidaException
					("A operação não foi realizada em função da violação de uma restrição no banco da dados.");
			}
			else
			{	throw e;
			}
		}
	}
}