package visao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import modelo.Carro;
import service.CarroAppService;

import org.hibernate.Hibernate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import excecao.CarroNaoEncontradoException;
import excecao.FaltaPrivilegioException;

public class CarroModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;

	public static final int COLUNA_NUMERO = 0;
	public static final int COLUNA_MODELO = 1;
	public static final int COLUNA_MOTORISTA = 2;
	public static final int COLUNA_VALOR = 3;
	public static final int COLUNA_DATAFABRICACAO = 4;
	public static final int COLUNA_ACAO = 5;

	private final static int NUMERO_DE_LINHAS_POR_PAGINA = 6;

	
	private static CarroAppService carroService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		carroService = (CarroAppService) fabrica.getBean("carroAppService");
	}

	private Map<Integer, Carro> cache;
	private int rowIndexAnterior = 0;
	private Integer qtd;
	private String modeloCarro;

	public CarroModel() {
		this.qtd = null;
		this.cache = new HashMap<Integer, Carro>(NUMERO_DE_LINHAS_POR_PAGINA * 4 / 75 / 100 + 2);
	}

	public void setModeloCarro(String modeloCarro) {
		this.modeloCarro = modeloCarro;
	}

	public String getColumnName(int c) {
		if (c == COLUNA_NUMERO)
			return "Número";
		if (c == COLUNA_MODELO)
			return "Modelo";
		if (c == COLUNA_MOTORISTA)
			return "Motorista";
		if (c == COLUNA_VALOR)
			return "Valor";
		if (c == COLUNA_DATAFABRICACAO)
			return "FABRICACAO";
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
			qtd = (int) carroService.recuperaQtdPeloNome(modeloCarro);

		return qtd;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (!cache.containsKey(rowIndex)) {
			if (cache.size() > (NUMERO_DE_LINHAS_POR_PAGINA * 3)) {
				cache.clear();

				if (rowIndex >= rowIndexAnterior) {
					List<Carro> resultados = carroService.recuperaPeloModelo(modeloCarro,
							rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1), NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Carro carro = resultados.get(j);
						cache.put(rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA - 1) + j, carro);
					}
				} else {
					int inicio = rowIndex - NUMERO_DE_LINHAS_POR_PAGINA;
					if (inicio < 0)
						inicio = 0;

					List<Carro> resultados = carroService.recuperaPeloModelo(modeloCarro, inicio,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Carro carro = resultados.get(j);
						cache.put(inicio + j, carro);
					}
				}
			} else {
				if (rowIndex >= rowIndexAnterior) {
					List<Carro> resultados = carroService.recuperaPeloModelo(modeloCarro, rowIndex,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Carro carro = resultados.get(j);
						cache.put(rowIndex + j, carro);
					}
				} else {
					int inicio = rowIndex - (NUMERO_DE_LINHAS_POR_PAGINA * 2 - 1);
					if (inicio < 0)
						inicio = 0;

					List<Carro> resultados = carroService.recuperaPeloModelo(modeloCarro, inicio,
							NUMERO_DE_LINHAS_POR_PAGINA * 2);

					for (int j = 0; j < resultados.size(); j++) {
						Carro carro = resultados.get(j);
						cache.put(inicio + j, carro);
					}
				}
			}
		}

		rowIndexAnterior = rowIndex;

		Carro carro = cache.get(rowIndex);
		
		
		if (columnIndex == COLUNA_NUMERO)
			return carro.getId();
		else if (columnIndex == COLUNA_MODELO)
			return carro.getModelo();
		else if (columnIndex == COLUNA_MOTORISTA)
			//return "";
			return carro.getMotorista().getNome();
		else if (columnIndex == COLUNA_VALOR)
			return carro.getValor();
		else if (columnIndex == COLUNA_DATAFABRICACAO)
			return carro.getDataFabricacaoMasc();
		else
			return null;

	}

	// Para que os campos booleanos sejam renderizados como check box.
	// Neste caso, não há campo boleano.
	public Class<?> getColumnClass(int c) {
		Class<?> classe = null;
		if (c == COLUNA_NUMERO)
			classe = Integer.class;
		if (c == COLUNA_MODELO)
			classe = String.class;
		if (c == COLUNA_MOTORISTA)
			classe = String.class;
		if (c == COLUNA_VALOR)
			classe = Double.class;
		if (c == COLUNA_DATAFABRICACAO)
			classe = String.class;
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
		Carro umCarro = cache.get(r);

		if (c == COLUNA_MODELO)
			umCarro.setModelo((String) obj);
		//if (c == COLUNA_ENDERECO)
			//umAnimal.setEndereco((String) obj);

		if (c == COLUNA_VALOR) {
			umCarro.setValor(Double.parseDouble((String) obj));
		}

		if (c == COLUNA_DATAFABRICACAO)
			umCarro.setDataFabricacao((Calendar) obj);

		try {
			carroService.altera(umCarro);
		} catch (CarroNaoEncontradoException e) {
			e.printStackTrace();
		} catch(FaltaPrivilegioException fpe){
			fpe.printStackTrace();
		}
	}
}
