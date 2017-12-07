package visao;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import service.MotoristaAppService;
import excecao.MotoristaNaoEncontradoException;
import modelo.Motorista;

public class MotoristaModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	public static final int COLUNA_NUMERO = 0;
	public static final int COLUNA_NOME = 1;
	public static final int COLUNA_CARTEIRA = 2;
	public static final int COLUNA_DESCRICAO = 3;
	public static final int COLUNA_DATACADASTRO = 4;
	public static final int COLUNA_ACAO = 5;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 6;

	private static MotoristaAppService motoristaService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		motoristaService = (MotoristaAppService) fabrica.getBean("motoristaAppService");
	}

	private Map<Integer, Motorista> cache;
	private int rowIndexAnterior = 0;
	private Integer qtd;
	private String nomeMotorista;

	public MotoristaModel() {
		this.qtd = null;
		this.cache = new HashMap<Integer, Motorista>(NUMERO_DE_LINHAS_POR_PAGINA * 4 / 75 / 100 + 2);
	}

	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}

	public String getColumnName(int c) {
		if (c == COLUNA_NUMERO)
			return "Número";
		if (c == COLUNA_NOME)
			return "Nome";
		if (c == COLUNA_CARTEIRA)
			return "Carteira";
		if (c == COLUNA_DESCRICAO)
			return "Descricao";
		if (c == COLUNA_DATACADASTRO)
			return "Cadastro";
		if (c == COLUNA_ACAO)
			return "Ação";
		return null;
	}

	@Override
	public int getColumnCount() {
		return 6;
	}

	@Override
	public int getRowCount() {
		if (qtd == null)
			qtd = (int) motoristaService.recuperaQtdPeloNome(nomeMotorista);

		return qtd;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!cache.containsKey(rowIndex)) {
			if (cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3)) {
				cache.clear();

				if (rowIndex >= rowIndexAnterior) {
					List<Motorista> resultados = motoristaService.recuperaPeloNome(nomeMotorista,
							rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Motorista Motorista = resultados.get(j);
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, Motorista);
					}
				} else {
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0)
						inicio = 0;

					List<Motorista> resultados = motoristaService.recuperaPeloNome(nomeMotorista, inicio,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Motorista Motorista = resultados.get(j);
						cache.put(inicio + j, Motorista);
					}
				}
			} else {
				if (rowIndex >= rowIndexAnterior) {
					List<Motorista> resultados = motoristaService.recuperaPeloNome(nomeMotorista, rowIndex,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Motorista Motorista = resultados.get(j);
						cache.put(rowIndex + j, Motorista);
					}
				} else {
					int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					if (inicio < 0)
						inicio = 0;

					List<Motorista> resultados = motoristaService.recuperaPeloNome(nomeMotorista, inicio,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Motorista Motorista = resultados.get(j);
						cache.put(inicio + j, Motorista);
					}
				}
			}
		}

		rowIndexAnterior = rowIndex;

		Motorista Motorista = cache.get(rowIndex);

		if (columnIndex == COLUNA_NUMERO)
			return Motorista.getId();
		else if (columnIndex == COLUNA_NOME)
			return Motorista.getNome();
		else if (columnIndex == COLUNA_CARTEIRA)
			return Motorista.getNumCarteira();
		else if (columnIndex == COLUNA_DESCRICAO)
			return Motorista.getDescricao();
		else if (columnIndex == COLUNA_DATACADASTRO)
			return Motorista.getDataCadastroMasc();
		else
			return null;

	}

	// Para que os campos booleanos sejam renderizados como check box.
	// Neste caso, não há campo boleano.
	public Class<?> getColumnClass(int c) {
		Class<?> classe = null;
		if (c == COLUNA_NUMERO)
			classe = Integer.class;
		if (c == COLUNA_NOME)
			classe = String.class;
		if (c == COLUNA_CARTEIRA)
			classe = String.class;
		if (c == COLUNA_DESCRICAO)
			classe = String.class;
		if (c == COLUNA_DATACADASTRO)
			classe = Calendar.class;
		if (c == COLUNA_ACAO)
			classe = ButtonColumn.class;

		return classe;
	}

	// Para que as células referentes às colunas 1 em diante possam ser editadas
	public boolean isCellEditable(int r, int c) {
		return c != 0;
	}

	@Override
	public void setValueAt(Object obj, int r, int c) {
		Motorista umMotorista = cache.get(r);

		if (c == COLUNA_NOME)
			umMotorista.setNome((String) obj);
		if (c == COLUNA_CARTEIRA)
			umMotorista.setNumCarteira((String) obj);

		if (c == COLUNA_DESCRICAO) {
			umMotorista.setDescricao((String) obj);
		}

		if (c == COLUNA_DATACADASTRO)
			umMotorista.setDataCadastro((Calendar) obj);

		try {
			motoristaService.altera(umMotorista);
		} catch (MotoristaNaoEncontradoException e) {
			e.printStackTrace();
		}
	}
}
