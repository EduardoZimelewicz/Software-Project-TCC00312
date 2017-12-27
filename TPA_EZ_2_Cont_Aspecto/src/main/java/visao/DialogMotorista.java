package visao;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
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

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import excecao.FaltaPrivilegioException;
import excecao.MotoristaNaoEncontradoException;
import excecao.ViolacaoDeConstraintDesconhecidaException;
import modelo.Motorista;
import service.MotoristaAppService;
import util.Util;

public class DialogMotorista extends JDialog implements ActionListener 
{
	private static final long serialVersionUID = 1L;

	private JButton novoButton;
	private JButton cadastrarButton;
	private JButton editarButton;
	private JButton alterarButton;
	private JButton removerButton;
	private JButton cancelarButton;
	private JButton buscarButton;
	
	private JTextField nomeTextField;
	private JTextField numCarteiraTextField;
	private JTextField descricaoTextField;
	private JTextField dataDeCadastroTextField;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel nomeMensagem;
	private JLabel numCarteiraMensagem;
	private JLabel descricaoMensagem;
	private JLabel dataDeCadastroMensagem;

	private Motorista umMotorista;

	private static MotoristaAppService motoristaAppService;
	
    static
    {
    	@SuppressWarnings("resource")
		ApplicationContext fabrica = new ClassPathXmlApplicationContext("beans-jpa.xml");

    	motoristaAppService = (MotoristaAppService)fabrica.getBean ("motoristaAppService");
    }
	
	public DialogMotorista(JFrame frame)
	{
		super(frame);
		
		setBounds(107, 151, 600, 287);
		setTitle("Cadastro de Motorista");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 554, 248);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel cadastrarLabel = new JLabel("Cadastro de Motoristas");
		cadastrarLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		cadastrarLabel.setBounds(144, 21, 190, 26);
		panel.add(cadastrarLabel);
		
		JLabel nomeLabel = new JLabel("Nome");
		nomeLabel.setBounds(42, 72, 46, 14);
		panel.add(nomeLabel);
		
		JLabel carteiraLabel = new JLabel("Carteira");
		carteiraLabel.setBounds(42, 109, 66, 14);
		panel.add(carteiraLabel);
		
		JLabel descricaoLabel = new JLabel("Descrição");
		descricaoLabel.setBounds(42, 189, 46, 14);
		panel.add(descricaoLabel);
		
		JLabel dataCadastroLabel = new JLabel("Data");
		dataCadastroLabel.setBounds(42, 152, 46, 14);
		panel.add(dataCadastroLabel);
		
		nomeTextField = new JTextField();
		nomeLabel.setLabelFor(nomeTextField);
		nomeTextField.setBounds(98, 69, 278, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(50);
		panel.add(nomeLabel);
		
		numCarteiraTextField = new JTextField();
		carteiraLabel.setLabelFor(numCarteiraTextField);
		numCarteiraTextField.setBounds(98, 106, 278, 20);
		panel.add(numCarteiraTextField);
		numCarteiraTextField.setColumns(50);
		panel.add(carteiraLabel);
		
		descricaoTextField = new JTextField();
		descricaoLabel.setLabelFor(descricaoTextField);
		descricaoTextField.setBounds(98, 186, 278, 20);
		panel.add(descricaoTextField);
		descricaoTextField.setColumns(50);
		panel.add(descricaoLabel);
		
		dataDeCadastroTextField = new JTextField();
		dataCadastroLabel.setLabelFor(dataDeCadastroTextField);
		dataDeCadastroTextField.setBounds(98, 149, 278, 20);
		panel.add(dataDeCadastroTextField);
		dataDeCadastroTextField.setColumns(50);
		panel.add(dataCadastroLabel);
		
		nomeMensagem = new JLabel("");
		nomeMensagem.setForeground(Color.RED);
		nomeMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		nomeMensagem.setBounds(98, 87, 241, 14);
		panel.add(nomeMensagem);
		
		numCarteiraMensagem = new JLabel("");
		numCarteiraMensagem.setForeground(Color.RED);
		numCarteiraMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		numCarteiraMensagem.setBounds(98, 124, 241, 14);
		panel.add(numCarteiraMensagem);
		
		descricaoMensagem = new JLabel("");
		descricaoMensagem.setForeground(Color.RED);
		descricaoMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		descricaoMensagem.setBounds(98, 203, 241, 14);
		panel.add(descricaoMensagem);
		
		dataDeCadastroMensagem = new JLabel("");
		dataDeCadastroMensagem.setForeground(Color.RED);
		dataDeCadastroMensagem.setFont(new Font("Tahoma", Font.PLAIN, 9));
		dataDeCadastroMensagem.setBounds(98, 169, 241, 14);
		panel.add(dataDeCadastroMensagem);
		
		novoButton = new JButton("Novo");
		novoButton.setBounds(425, 23, 96, 23);
		panel.add(novoButton);
		novoButton.addActionListener(this);

		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(425, 50, 96, 23);
		panel.add(cadastrarButton);
		cadastrarButton.addActionListener(this);
		
		editarButton = new JButton("Editar");
		editarButton.setBounds(425, 77, 96, 23);
		panel.add(editarButton);
		editarButton.addActionListener(this);
		
		alterarButton = new JButton("Alterar");
		alterarButton.setBounds(425, 104, 96, 23);
		panel.add(alterarButton);
		alterarButton.addActionListener(this);

		removerButton = new JButton("Remover");
		removerButton.setBounds(425, 131, 96, 23);
		panel.add(removerButton);
		removerButton.addActionListener(this);

		cancelarButton = new JButton("Cancelar");
		cancelarButton.setBounds(425, 158, 96, 23);
		panel.add(cancelarButton);
		cancelarButton.addActionListener(this);

		buscarButton = new JButton("Buscar");
		buscarButton.setBounds(425, 185, 96, 23);
		panel.add(buscarButton);
		buscarButton.addActionListener(this);
	}
	
	public void designateMotoristaAFrame(Motorista umMotorista) {
		this.umMotorista = umMotorista;
		
		nomeTextField.setText(umMotorista.getNome());
		numCarteiraTextField.setText(umMotorista.getNumCarteira());
		descricaoTextField.setText(umMotorista.getDescricao());
		dataDeCadastroTextField.setText(Util.calendarToStr(umMotorista.getDataCadastro()));
		
		nomeMensagem.setText("");
		numCarteiraMensagem.setText("");
		descricaoMensagem.setText("");
		dataDeCadastroMensagem.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if (obj == novoButton)
		{	novo();
		}
		else if (obj == cadastrarButton)
		{
			boolean deuErro = validaMotorista();
			
			if(!deuErro)
			{	
				umMotorista = new Motorista();
				umMotorista.setNome(nomeTextField.getText());
				umMotorista.setDescricao(descricaoTextField.getText());
				umMotorista.setNumCarteira(numCarteiraTextField.getText());
				umMotorista.setDataCadastro(Util.strToCalendar(dataDeCadastroTextField.getText()));

				try{
					motoristaAppService.inclui(umMotorista);	// inclui o cliente
					salvo();
					
					JOptionPane.showMessageDialog(this, "Motorista cadastrado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				}
				catch(FaltaPrivilegioException fpe){
					JOptionPane.showMessageDialog(this, fpe.getMessage(), "", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(obj == editarButton)
		{
			editavel();
		}
		else if(obj == alterarButton)
		{
			boolean deuErro = validaMotorista();
			
			if(!deuErro)
			{	
				umMotorista.setNome(nomeTextField.getText());
				umMotorista.setDescricao(descricaoTextField.getText());
				umMotorista.setNumCarteira(numCarteiraTextField.getText());
				umMotorista.setDataCadastro(Util.strToCalendar(dataDeCadastroTextField.getText()));

				try 
				{
					motoristaAppService.altera(umMotorista);     	// altera o cliente
					
					salvo();
					
					JOptionPane.showMessageDialog(this, "Motorista atualizado com sucesso", "", 
							JOptionPane.INFORMATION_MESSAGE);
				} 
				catch (MotoristaNaoEncontradoException e1) 
				{
					removido();
					
					JOptionPane.showMessageDialog(this, "Motorista não encontrado", "", 
						JOptionPane.ERROR_MESSAGE);
				}
				catch (FaltaPrivilegioException fpe){
					JOptionPane.showMessageDialog(this, fpe.getMessage(), "", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(obj == removerButton)
		{
			try 
			{
				motoristaAppService.exclui(umMotorista);

				removido();
				
				JOptionPane.showMessageDialog(this, "Motorista removido com sucesso", "", 
						JOptionPane.INFORMATION_MESSAGE);
			} 
			catch (MotoristaNaoEncontradoException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Motorista não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
			catch (ViolacaoDeConstraintDesconhecidaException e3){
				JOptionPane.showMessageDialog(this, e3.getMessage()
						, "", JOptionPane.ERROR_MESSAGE);
			}
			catch(FaltaPrivilegioException fpe){
				JOptionPane.showMessageDialog(this, fpe.getMessage()
						, "", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == cancelarButton)
		{
			try 
			{
				umMotorista = motoristaAppService.recuperaUmMotorista(umMotorista.getId());	// recupera todas as informações do cliente no banco de dados

				nomeTextField.setText(umMotorista.getNome());
				numCarteiraTextField.setText(umMotorista.getNumCarteira());
				descricaoTextField.setText(umMotorista.getDescricao());
				dataDeCadastroTextField.setText(umMotorista.getDataCadastroMasc());
				salvo();
			} 
			catch (MotoristaNaoEncontradoException e1) 
			{
				removido();
				
				JOptionPane.showMessageDialog(this, "Motorista não encontrado", "", 
					JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(obj == buscarButton)
		{	
			DialogTabelaMotorista dialog = new DialogTabelaMotorista(this);
			dialog.setVisible(true);
		}
	}
	
	private boolean eNumero(String numero)
	{
		boolean resposta = true;
		try
		{	
			if (numero == null) return true;
			
			Long.parseLong(numero);
		}
		catch(NumberFormatException e)
		{	resposta = false;
		}
		return resposta;
	}
	
	private boolean validaMotorista()
	{
		boolean deuErro = false;
		if (nomeTextField.getText().trim().length() == 0)
		{	deuErro = true;
			nomeMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	nomeMensagem.setText("");
		}
		
		if (numCarteiraTextField.getText().trim().length() == 0)
		{	deuErro = true;
			numCarteiraMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	numCarteiraMensagem.setText("");
		}

		if (dataDeCadastroTextField.getText().trim().length() == 0)
		{	deuErro = true;
			dataDeCadastroMensagem.setText("Campo de preenchimento obrigatório");
		}
		else
		{	dataDeCadastroMensagem.setText("");
		}
		
		return deuErro;
	}
	
	public void novo()
	{
		nomeTextField.setEnabled(true);
		numCarteiraTextField.setEnabled(true);
		descricaoTextField.setEnabled(true);
		dataDeCadastroTextField.setEnabled(true);
		
		nomeTextField.setText("");
		numCarteiraTextField.setText("");
		descricaoTextField.setText("");
		dataDeCadastroTextField.setText("");

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(true);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void salvo()
	{
		nomeTextField.setEnabled(false);
		numCarteiraTextField.setEnabled(false);
		descricaoTextField.setEnabled(false);
		dataDeCadastroTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(true);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(true);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);
	}

	public void editavel()
	{
		nomeTextField.setEnabled(true);
		numCarteiraTextField.setEnabled(true);
		descricaoTextField.setEnabled(true);
		dataDeCadastroTextField.setEnabled(true);

		novoButton.setEnabled(false);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(true);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(true);
		buscarButton.setEnabled(false);
	}

	public void removido()
	{
		nomeTextField.setEnabled(false);
		numCarteiraTextField.setEnabled(false);
		descricaoTextField.setEnabled(false);
		dataDeCadastroTextField.setEnabled(false);

		novoButton.setEnabled(true);
		cadastrarButton.setEnabled(false);
		editarButton.setEnabled(false);
		alterarButton.setEnabled(false);
		removerButton.setEnabled(false);
		cancelarButton.setEnabled(false);
		buscarButton.setEnabled(true);   // novo
	}
}
