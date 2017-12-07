package visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import excecao.CarroNaoEncontradoException;
import excecao.DataDeCarroInvalidaException;
import excecao.MotoristaNaoEncontradoException;
import excecao.ValorDeCarroInvalidoException;
import modelo.Carro;
import modelo.Motorista;
import service.CarroAppService;
import util.Util;

public class DialogCarro extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;

	private static CarroAppService carroService;

	static {
		@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

		carroService = (CarroAppService) fabrica.getBean("carroAppService");
	}
	DialogCarro dialogCarro;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;

	private JTextField modeloTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel modeloMensagem;
	private JLabel valorMensagem;
	private JLabel placaMensagem;
	private JLabel motoristaMensagem;

	private JPanel panel;

	private Carro umCarro;
	private JTextField valorField;
	private JTextField placaField;
	private JTextField dataField;
	private JTextField motoristaField;

	private JLabel dataMensagem;
	private JButton buscaMotoristaButton;

	public void designateCarroAFrame(Carro umCarro) {
		this.umCarro = umCarro;

		modeloTextField.setText(umCarro.getModelo());
		valorField.setText(Util.doubleToStr(umCarro.getValor()));
		placaField.setText(umCarro.getPlaca());
		dataField.setText(Util.calendarToStr(umCarro.getDataFabricacao()));
		
		modeloMensagem.setText("");
		valorMensagem.setText("");
		placaMensagem.setText("");
		dataMensagem.setText("");
	}

	public void setMotoristaName(Motorista m) {
		if (umCarro == null)
			umCarro = new Carro();
		umCarro.setMotorista(m);
		motoristaField.setText(m.getNome());

		modeloMensagem.setText("");
	}

	public DialogCarro(JFrame frame) {
		super(frame);

		
		dialogCarro = this;
		setBounds(105, 147, 618, 330);
		setTitle("Cadastro de Carros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 602, 292);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel cadastrarLabel = new JLabel("Cadastro de Carros");
		cadastrarLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(189, 21, 190, 26);
		panel.add(cadastrarLabel);

		JLabel modeloLabel = new JLabel("Modelo");
		modeloLabel.setBounds(62, 78, 46, 14);
		panel.add(modeloLabel);

		modeloTextField = new JTextField();
		modeloLabel.setLabelFor(modeloTextField);
		modeloTextField.setBounds(148, 75, 278, 20);
		panel.add(modeloTextField);
		modeloTextField.setColumns(50);
		modeloTextField.setBackground(SystemColor.window);

		JLabel motoristaLabel = new JLabel("Motorista");
		motoristaLabel.setBounds(62, 124, 80, 14);
		panel.add(motoristaLabel);

		JLabel placaLabel = new JLabel("Placa");
		placaLabel.setBounds(62, 170, 46, 14);
		panel.add(placaLabel);

		JLabel dateLabel = new JLabel("Data");
		dateLabel.setBounds(62, 212, 78, 14);
		panel.add(dateLabel);
		
		JLabel valorLabel = new JLabel("Valor");
		valorLabel.setBounds(62, 252, 78, 14);
		panel.add(valorLabel);

		modeloMensagem = new JLabel("");
		modeloMensagem.setForeground(Color.RED);
		modeloMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		modeloMensagem.setBounds(148, 98, 241, 14);
		panel.add(modeloMensagem);

		placaMensagem = new JLabel("");
		placaMensagem.setForeground(Color.RED);
		placaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		placaMensagem.setBounds(149, 142, 241, 14);
		panel.add(placaMensagem);

		valorMensagem = new JLabel("");
		valorMensagem.setForeground(Color.RED);
		valorMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		valorMensagem.setBounds(148, 186, 241, 14);
		panel.add(valorMensagem);

		novoButton = new JButton("Novo");
		novoButton.setBounds(452, 50, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(452, 77, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);

		editarButton = new JButton("Editar");
		editarButton.setBounds(452, 104, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);

		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(452, 131, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(452, 158, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(452, 185, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(452, 212, 96, 23);
		panel.add(buscarButton);

		valorField = new JTextField();
		valorField.setColumns(50);
		valorField.setBackground(Color.WHITE);
		valorField.setBounds(148, 249, 278, 20);
		panel.add(valorField);

		motoristaField = new JTextField();
		motoristaField.setColumns(50);
		motoristaField.setBackground(Color.WHITE);
		motoristaField.setBounds(148, 121, 216, 20);
		panel.add(motoristaField);
		
		dataField = new JTextField();
		dataField.setColumns(50);
		dataField.setBackground(Color.WHITE);
		dataField.setBounds(148, 209, 278, 20);
		panel.add(dataField);
		
		placaField = new JTextField();
		placaField.setColumns(50);
		placaField.setBackground(Color.WHITE);
		placaField.setBounds(148, 167, 278, 20);
		panel.add(placaField);
		
		dataMensagem = new JLabel("");
		dataMensagem.setForeground(Color.RED);
		dataMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dataMensagem.setBounds(148, 230, 241, 14);
		panel.add(dataMensagem);

		motoristaMensagem = new JLabel("");
		motoristaMensagem.setForeground(Color.RED);
		motoristaMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		motoristaMensagem.setBounds(148, 230, 241, 14);
		panel.add(motoristaMensagem);

		
		buscaMotoristaButton = new JButton("Busca");
		buscaMotoristaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogTabelaMotorista dialog = new DialogTabelaMotorista(dialogCarro);
				dialog.setVisible(true);
			}
		});
		buscaMotoristaButton.setBounds(374, 120, 61, 23);
		buscaMotoristaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogTabelaMotorista dialog = new DialogTabelaMotorista(dialogCarro);
				dialog.setVisible(true);
			}
		});
		panel.add(buscaMotoristaButton);
		buscarButton.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == novoButton) {
			novo();
		} else if (obj == cadastrarButton) {
			boolean deuErro = validaCarro();

			if (!deuErro) {
				if (umCarro == null)
					umCarro = new Carro();
				umCarro.setModelo(modeloTextField.getText());
				umCarro.setPlaca(placaField.getText());
				umCarro.setValor(Util.strToDouble(valorField.getText()));
				umCarro.setDataFabricacao(Util.strToCalendar(dataField.getText()));
				try {
					carroService.inclui(umCarro);
				} catch (MotoristaNaoEncontradoException d) {
					JOptionPane.showMessageDialog(this, d.getMessage(), "", JOptionPane.INFORMATION_MESSAGE);
				} catch (ValorDeCarroInvalidoException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataDeCarroInvalidaException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				salvo();

				JOptionPane.showMessageDialog(this, "Carro cadastrado com sucesso", "",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (obj == editarButton) {
			editavel();
		} else if (obj == alterarButton) {
			boolean deuErro = validaCarro();

			if (!deuErro) {
				umCarro.setModelo(modeloTextField.getText());
				umCarro.setPlaca(placaField.getText());
				umCarro.setValor(Util.strToDouble(Util.calendarToStr(umCarro.getDataFabricacao())));
				umCarro.setDataFabricacao(Util.strToCalendar(dataField.getText()));
				
				try {
					carroService.altera(umCarro);

					salvo();

					JOptionPane.showMessageDialog(this, "Carro atualizado com sucesso", "",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (CarroNaoEncontradoException e1) {
					novo();

					JOptionPane.showMessageDialog(this, "Carro não encontrado", "", JOptionPane.ERROR_MESSAGE);
				}
			}
		} else if (obj == removerButton) {
			try {
				carroService.exclui(umCarro);

				removido();

				JOptionPane.showMessageDialog(this, "Carro removido com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
			} catch (CarroNaoEncontradoException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Carro não encontrado", "", JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == cancelarButton) {
			try {
				umCarro = carroService.recuperaUmCarroComMotorista((umCarro.getId()));

				modeloTextField.setText(umCarro.getModelo());
				motoristaField.setText(umCarro.getMotorista().getNome());
				valorField.setText(String.valueOf(umCarro.getValor()));
				dataField.setText(umCarro.getDataFabricacaoMasc());
				/*
				 * if(umAnimal.getSexo().equals("M"))
				 * sexoMascRadioButton.setSelected(true); else
				 * sexoFemRadioButton.setSelected(true);
				 */
				// idadeComboBox.setSelectedIndex(umAnimal.getIdade());
				// newsLetterCheckBox.setSelected(umAnimal.isNewsLetter());

				cancelado();
			} catch (CarroNaoEncontradoException e1) {
				novo();

				JOptionPane.showMessageDialog(this, "Carro não encontrado", "", JOptionPane.ERROR_MESSAGE);
			}
		} else if (obj == buscarButton) {
			DialogTabelaCarro dialog = new DialogTabelaCarro(this);
			dialog.setVisible(true);
		}
	}

	private boolean validaCarro() {
		boolean deuErro = false;
		if (modeloTextField.getText().trim().length() == 0) {
			deuErro = true;
			modeloMensagem.setText("Campo de preenchimento obrigatório.");
		} else {
			modeloMensagem.setText("");
		}

		if (motoristaField.getText().trim().length() == 0) {
			motoristaMensagem.setText("Campo de preenchimento obrigatório.");
		} else {
			motoristaMensagem.setText("");
		}

		if (valorField.getText().trim().length() == 0) {
			deuErro = true;
			valorMensagem.setText("Campo de preenchimento obrigatório.");
		} else {
			/*
			if (isDouble(valorField.getText())) {
				idadeMensagem.setText("");
			} else {
				idadeMensagem.setText("Valor inválido.");
			}
			*/

		}

		if (dataField.getText().trim().length() == 0) {
			dataMensagem.setText("Campo de preenchimento obrigatório.");
		} else {
			dataMensagem.setText("");
		}

		return deuErro;
	}

	public void novo() {
		modeloTextField.setEnabled(true);
		motoristaField.setEnabled(false);
		valorField.setEnabled(true);
		placaField.setEnabled(true);
		dataField.setEnabled(true);
		buscaMotoristaButton.setEnabled(true);

		modeloTextField.setText("");
		motoristaField.setText("");
		placaField.setText("");
		valorField.setText("");
		dataField.setText("");
		
		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void salvo() {
		modeloTextField.setEnabled(false);
		motoristaField.setEnabled(false);
		placaField.setEnabled(false);
		valorField.setEnabled(false);
		dataField.setEnabled(false);
		buscaMotoristaButton.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void editavel() {
		modeloTextField.setEnabled(true);
		motoristaField.setEnabled(false);
		valorField.setEnabled(true);
		dataField.setEnabled(true);
		placaField.setEnabled(true);
		buscaMotoristaButton.setEnabled(true);

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
	}

	public void removido() {
		modeloTextField.setEnabled(false);
		motoristaField.setEnabled(false);
		valorField.setEnabled(false);
		dataField.setEnabled(false);
		placaField.setEnabled(false);
		buscaMotoristaButton.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void cancelado() {
		modeloTextField.setEnabled(false);
		motoristaField.setEnabled(false);
		valorField.setEnabled(false);
		dataField.setEnabled(false);
		placaField.setEnabled(false);
		buscaMotoristaButton.setEnabled(false);
		
		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}
}
