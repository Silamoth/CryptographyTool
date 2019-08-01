import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.PlainDocument;

import java.awt.Font;

import javax.swing.JLabel;

public class PortaPanel extends CipherPanel 
{
	private JTextField keyTextBox;
	
	public PortaPanel() 
	{
		this.setPreferredSize(super.getMaximumSize());
			
		JLabel lblNewLabel = new JLabel("Key:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(201, 230, 74, 29);
		add(lblNewLabel);
		
		keyTextBox = new JTextField();
		keyTextBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
		keyTextBox.setBounds(285, 237, 96, 20);
		add(keyTextBox);
		keyTextBox.setColumns(10);	
		
		PlainDocument aDoc = (PlainDocument) keyTextBox.getDocument();
	    aDoc.setDocumentFilter(new MyAlphabetFilter());		
	}
	
	public void goButtonClicked()
	{
		super.goButtonClicked();
		
		String keyString = keyTextBox.getText().toLowerCase();
		
		char[] input = getInput().toCharArray();
		char[] output = new char[input.length];
		int keyIndex = 0;
		
		int[] adjustedKey = new int[keyString.length()];
		char[] key = keyString.toCharArray();
		
		for (int i = 0; i < key.length; i++)
		{
			int adjustedValue = 13 + ((int)key[i] - 97) / 2;
			
			adjustedKey[i] = adjustedValue;
		}
		
		for (int i = 0; i < input.length; i++)
		{			
			if (!Character.isJavaLetter(input[i]))
			{
				output[i] = input[i];
				keyIndex--;
			}
			else
			{
				int adjustmentValue;
				
				if (Character.isUpperCase(input[i]))
					adjustmentValue = 65;
				else
					adjustmentValue = 97;
				
				int asciiValue = (int)input[i];
				
				int newValue;
								
				newValue = (asciiValue - adjustmentValue + adjustedKey[keyIndex % adjustedKey.length]);
								
				while (newValue >= 26)
					newValue -= 13;
				
				newValue += adjustmentValue;
								
				output[i] = (char)newValue;
			}
			
			keyIndex++;
		}
		
		setOutput(new String(output));
	}
}