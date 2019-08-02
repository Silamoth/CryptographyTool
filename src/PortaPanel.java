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
		
		String[] alphabets = new String[13];
		char[] key = keyString.toCharArray();
		
		//Generate base string to work off of 
		
		String baseString = "";
		for (int x = 110; x <= 122; x++)
		{
			baseString += (char)x;
		}
		for (int x = 97; x < 110; x++)
		{
			baseString += (char)x;
		}
		
		//Generate alphabets
		for (int i = 0; i < 13; i++)
		{
			String beginning = baseString.substring(0, i);	
			String ending = baseString.substring(26 - i);
			
			String alphabet = baseString.replace(beginning, "");
			alphabet = alphabet.replace(ending, "");
			
			String firstHalf = alphabet.substring(0, 13 - i);
			String secondHalf = alphabet.substring(13 - i);
			
			alphabet = firstHalf + beginning + ending + secondHalf;
			
			alphabets[i] = alphabet;
		}
		
		//Generate correlations for key letters to alphabets
		char[] firstHalf = new char[13];
		char[] secondHalf = new char[13];
		
		for (int i = 0; i < 13; i++)
			firstHalf[i] = (char)(97 + 2 * i);
		
		for (int i = 0; i < 13; i++)
			secondHalf[i] = (char)(98 + 2 * i);
		
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
								
				//Find which 'half' this letter of the key is in
				int index = -1;
				
				for (int x = 0; x < firstHalf.length; x++)
				{
					if (firstHalf[x] == key[keyIndex % key.length])
					{
						index = x;
						break;
					}
				}
				if (index == -1)	//Still hasn't been found
				{
					for (int x = 0; x < secondHalf.length; x++)
					{
						if (secondHalf[x] == key[keyIndex % key.length])
						{
							index = x;
							break;
						}
					}
				}
				
				String alphabet = alphabets[index];
				char newValue = alphabet.charAt((int)input[i] - 97);
				output[i] = newValue;
			}
			
			keyIndex++;
		}
		
		setOutput(new String(output));
	}
}