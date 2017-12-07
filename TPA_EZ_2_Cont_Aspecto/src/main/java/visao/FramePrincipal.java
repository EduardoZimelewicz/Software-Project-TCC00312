package visao;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class FramePrincipal extends JFrame
{
	private static final long serialVersionUID = 1L;

	private JMenuItem menuItemMotorista;
	private JMenuItem menuItemCarro;
	private JMenuItem menuItemSair;
	private JDesktopPane desktopPane;
	private JFrame framePrincipal;
	private JMenu mnLookAndFeal;
	private JMenuItem mntmLookAndFeel;
	
	public FramePrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 614, 345);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnCadastrar = new JMenu("Cadastro");
		menuBar.add(mnCadastrar);
		
		framePrincipal = this;
		
		menuItemMotorista = new JMenuItem("Motorista");
		menuItemMotorista.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemMotorista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogMotorista dialogMotorista = new DialogMotorista(framePrincipal);
				dialogMotorista.novo();
				dialogMotorista.setVisible(true);
			}
		});
		mnCadastrar.add(menuItemMotorista);
		
		menuItemSair = new JMenuItem("Sair");
		menuItemSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK)); // Diz a combinação necessaria para chamar os action listeners
		menuItemSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnCadastrar.add(menuItemSair);
		
		menuItemCarro = new JMenuItem("Carro");
		menuItemCarro.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		menuItemCarro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogCarro dialogCarro = new DialogCarro(framePrincipal);
				dialogCarro.novo();
				dialogCarro.setVisible(true);
			}
		});
		mnCadastrar.add(menuItemCarro);
		mnCadastrar.add(menuItemSair);
		
		mnLookAndFeal = new JMenu("Configuração");
		menuBar.add(mnLookAndFeal);
		
		mntmLookAndFeel = new JMenuItem("Look and Feel");
		mntmLookAndFeel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FrameLook frameLook = new FrameLook();
				desktopPane.add(frameLook);
				frameLook.setVisible(true);
			}
		});
		mnLookAndFeal.add(mntmLookAndFeel);
		
		getContentPane().setLayout(null);
		
		desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.LIGHT_GRAY);
		desktopPane.setBounds(0, 0, 598, 286);
		getContentPane().add(desktopPane);
		
		try
		{
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
}
}
