import javax.swing.JPanel;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CipherPanel extends JPanel 
{
	protected JTextPane inputTextPane;
	protected JLabel outputLabel;
	/**
	 * Create the panel.
	 */
	public CipherPanel() 
	{
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Input:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 11, 89, 34);
		add(lblNewLabel);
		
		inputTextPane = new JTextPane();
		inputTextPane.setBounds(10, 44, 711, 169);
		add(inputTextPane);
		
		ButtonGroup buttonGroup = new ButtonGroup();
		
		JRadioButton encryptRadioButton = new JRadioButton("Encrypt");
		encryptRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		encryptRadioButton.setBounds(10, 232, 109, 23);
		add(encryptRadioButton);
		
		JRadioButton decryptRadioButton = new JRadioButton("Decrypt");
		decryptRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		decryptRadioButton.setBounds(10, 258, 109, 23);
		add(decryptRadioButton);
		
		buttonGroup.add(encryptRadioButton);
		buttonGroup.add(decryptRadioButton);
		
		JLabel lblOutput = new JLabel("Output:");
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblOutput.setBounds(10, 305, 76, 35);
		add(lblOutput);
		
		outputLabel = new JLabel("");
		outputLabel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		outputLabel.setBounds(10, 351, 711, 169);
		add(outputLabel);
		
		JButton goButton = new JButton("Go");
		goButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				goButtonClicked();
			}
		});
		goButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		goButton.setBounds(639, 232, 89, 23);
		add(goButton);

	}
	
	protected void goButtonClicked()
	{
		
	}
}