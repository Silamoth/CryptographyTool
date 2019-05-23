import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;

public class AffinePanel extends CipherPanel 
{
	private JTextField aTextField;
	private JTextField bTextField;
	
	public AffinePanel() 
	{
		this.setPreferredSize(super.getMaximumSize());
		
		JLabel lblNewLabel = new JLabel("a:\r\n");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(232, 232, 23, 23);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("b:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(232, 281, 48, 14);
		add(lblNewLabel_1);
		
		aTextField = new JTextField();
		aTextField.setBounds(265, 236, 48, 20);
		add(aTextField);
		aTextField.setColumns(10);
		
		bTextField = new JTextField();
		bTextField.setBounds(265, 281, 48, 20);
		add(bTextField);
		bTextField.setColumns(10);
	}
	
	public void goButtonClicked()
	{
		super.goButtonClicked();
		
		if (aTextField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this.getParent(), "Please input a value for 'a'", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (bTextField.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this.getParent(), "Please input a value for 'b'", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		char[] input = inputTextPane.getText().toCharArray();
		char[] output = new char[input.length];
		int a = Integer.parseInt(aTextField.getText());
		int b = Integer.parseInt(bTextField.getText());
		
		for (int i = 0; i < input.length; i++)
		{
			if (input[i] == ' ')
			{
				output[i] = ' ';
				i++;
			}
			
			int adjustmentValue;
			
			if (Character.isUpperCase(input[i]))
				adjustmentValue = 65;
			else
				adjustmentValue = 97;
			
			int asciiValue = (int)input[i];
			
			int newValue;
			
			//if (encryptRadioButton.isSelected())
				newValue = (((asciiValue - adjustmentValue) * a + b) % 26) + adjustmentValue;
			//else
				//newValue = ((asciiValue - adjustmentValue - a) % 26) + adjustmentValue;
			
			output[i] = (char)newValue;
		}
		
		outputLabel.setText(new String(output));
	}
}