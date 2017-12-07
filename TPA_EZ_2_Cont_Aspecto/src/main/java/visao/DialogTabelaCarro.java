package visao;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


public class DialogTabelaCarro extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private JTextField nomeTextField;
	private JTable table;
	private CarroModel carroModel;
	private JScrollPane scrollPane;
	
	private TableColumnModel columnModel;

	
	DialogCarro dialogCarro;
	
	public DialogTabelaCarro(DialogCarro dialogCarro)
	{
		super(dialogCarro);
		
		this.dialogCarro = dialogCarro;
		
		setTitle("Pesquisa de Carros");
		setBounds(110, 171, 608, 301);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 588, 111);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblPesquisaPorModelo = new JLabel("Pesquisa por Modelo");
		lblPesquisaPorModelo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPesquisaPorModelo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPesquisaPorModelo.setBounds(203, 11, 195, 22);
		panel.add(lblPesquisaPorModelo);
		
		JLabel lblModelo = new JLabel("Modelo");
		lblModelo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModelo.setBounds(92, 42, 55, 22);
		panel.add(lblModelo);
		
		nomeTextField = new JTextField();
		nomeTextField.setBackground(UIManager.getColor("Button.light"));
		nomeTextField.setForeground(SystemColor.desktop);
		nomeTextField.setBounds(142, 44, 324, 20);
		panel.add(nomeTextField);
		nomeTextField.setColumns(10);
		
		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(this);
		btnPesquisar.setBounds(249, 75, 102, 23);
		panel.add(btnPesquisar);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(0, 112, 588, 144);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);

		/**********************************************************************/
		

		columnModel = table.getColumnModel();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		carroModel = new CarroModel();	
		carroModel.setModeloCarro(nomeTextField.getText());
		table.setModel(carroModel);

		// Designa um renderer e um editor para os botões.
		// Para cada botão exibido é executado o método getTableCellRendererComponent() 
		// que retorna o botão que deve ser renderizado.
		// E sempre que um botão é clicado é executado o método getTableCellEditorComponent()
		// que retorna o botão que foi clicado para que o listener deste botão possa ser executado.
		new ButtonColumnCarro(table, 5, this, dialogCarro); //Parametros na ordem ==> Tabela, número da coluna onde está o botão, this da janela de busca, janela anterior a janela de busca.

        // Designa um novo editor para a coluna da tabela
        // Este novo editor é um ComboBox
	//	sexoColumn = columnModel.getColumn(AnimalModel.COLUNA_ENDERECO);
		//sexoColumn.setCellEditor(new DefaultCellEditor(sexoComboBox));
		
        // Designa um novo editor para a coluna da tabela
        // Este novo editor é um ComboBox
		//idadeColumn = columnModel.getColumn(AnimalModel.COLUNA_IDADE);
	//	idadeColumn.setCellEditor(new DefaultCellEditor(idadeComboBox));
		
		// Designa um valor preferido para a coluna. Se ele for menor
		// ou maior do que o máximo possível, ele será ajustado.
		// columnModel.getColumn(0).setPreferredWidth(50);
		columnModel.getColumn(0).setMinWidth(0);
		columnModel.getColumn(0).setMaxWidth(0);
		columnModel.getColumn(1).setPreferredWidth(200);
		columnModel.getColumn(2).setPreferredWidth(150);
		columnModel.getColumn(3).setPreferredWidth(30);
	
		columnModel.getColumn(4).setPreferredWidth(70);
		columnModel.getColumn(5).setPreferredWidth(60);
		
		scrollPane.setVisible(true);
	}
}
