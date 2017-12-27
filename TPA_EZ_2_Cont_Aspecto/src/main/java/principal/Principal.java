package principal;

import java.util.ArrayList;

import util.PermissoesSingleton;
import visao.FramePrincipal;

public class Principal{
	
	private static ArrayList<String> permissoes;
	static {
		permissoes = new ArrayList<String>();
		permissoes.add("ROLE_ADMIN");
		permissoes.add("ROLE_USER1");
		PermissoesSingleton perm = PermissoesSingleton.getPermissoesSingleton();
		perm.setPermissoes(permissoes);
	}
	
	public static void main(String[] args) {
		FramePrincipal framePrincipal = new FramePrincipal();
		framePrincipal.setVisible(true);
	}	
}

