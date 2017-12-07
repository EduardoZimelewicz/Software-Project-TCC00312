package visao;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FrameLook extends JInternalFrame implements ActionListener{

	private static final long serialVersionUID = 1L;

	private JComboBox<String> comboBox;
	
	public FrameLook() {
		setBounds(0, 0, 598, 285);
		getContentPane().setLayout(null);
		setClosable(true);
		setTitle("Look and feel");
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 434, 220);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblSelecioneOLook = new JLabel("Selecione o Look and Feel");
		lblSelecioneOLook.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSelecioneOLook.setBounds(115, 53, 203, 29);
		panel.add(lblSelecioneOLook);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"WindowsClassicLookAndFeel", "SmartLookAndFeel", "AcrylLookAndFeel", "AeroLookAndFeel", "AluminiumLookAndFeel", "BernsteinLookAndFeel", "FastLookAndFeel", "GraphiteLookAndFeel", "HiFiLookAndFeel", "LunaLookAndFeel", "McWinLookAndFeel", "MintLookAndFeel", "NoireLookAndFeel", "SmartLookAndFeel"}));
		comboBox.setBounds(123, 110, 161, 20);
		panel.add(comboBox);
		comboBox.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String lookAndFeel = comboBox.getSelectedItem().toString();
		
		try
		{
			if (lookAndFeel.equals("WindowsClassicLookAndFeel"))
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
			else if (lookAndFeel.equals("SmartLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			else if (lookAndFeel.equals("AcrylLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			else if (lookAndFeel.equals("AeroLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			else if (lookAndFeel.equals("AluminiumLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			else if (lookAndFeel.equals("BernsteinLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
			else if (lookAndFeel.equals("FastLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
			else if (lookAndFeel.equals("GraphiteLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
			else if (lookAndFeel.equals("LunaLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
			else if (lookAndFeel.equals("McWinLookAndFeel"))
				UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			else if (lookAndFeel.equals("MintLookAndFeel"))
		        UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
			else if (lookAndFeel.equals("NoireLookAndFeel"))
		        UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
			else 
		        UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		this.dispose();
	}
}
