import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class VigenerePanel extends CipherPanel 
{
	private JTextField keyTextBox;

	public VigenerePanel() 
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
		
		for (int i = 0; i < input.length; i++)
		{
			char shiftLetter = keyString.toCharArray()[keyIndex % keyString.length()];
			
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
								
				if (getEncryptStatus())
					newValue = ((asciiValue - adjustmentValue + (int)shiftLetter - 97) % 26) + adjustmentValue;
				else
					newValue = ((asciiValue - adjustmentValue - (int)shiftLetter - 97) % 26) + adjustmentValue;
				
				output[i] = (char)newValue;
			}
			
			keyIndex++;
		}
		
		setOutput(new String(output));
	}
}