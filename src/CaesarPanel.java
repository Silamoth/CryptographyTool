import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.swing.JLabel;

public class CaesarPanel extends CipherPanel 
{
	JSpinner shiftSpinner;
	
	public CaesarPanel() 
	{
		encryptRadioButton.setSelected(true);
		this.setPreferredSize(super.getMaximumSize());
		
		shiftSpinner = new JSpinner();
		shiftSpinner.setFont(new Font("Tahoma", Font.PLAIN, 30));
		shiftSpinner.setModel(new SpinnerNumberModel(0, 0, 26, 1));
		shiftSpinner.setBounds(216, 228, 66, 64);
		shiftSpinner.setEditor(new JSpinner.DefaultEditor(shiftSpinner));
		
		add(shiftSpinner);
		
		JLabel lblNewLabel = new JLabel("Shift:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(135, 250, 71, 28);
		add(lblNewLabel);
	}
	
	public void goButtonClicked()
	{
		super.goButtonClicked();
		
		char[] input = inputTextPane.getText().toCharArray();
		char[] output = new char[input.length];
		
		for (int i = 0; i < input.length; i++)
		{
			if ((int)input[i] < 65 || (int)input[i] > 97)		
				output[i] = input[i];
			else
			{
				int adjustmentValue;
				
				if (Character.isUpperCase(input[i]))
					adjustmentValue = 65;
				else
					adjustmentValue = 97;
				
				int asciiValue = (int)input[i];
				
				int newValue;
				
				if (encryptRadioButton.isSelected())
					newValue = ((asciiValue - adjustmentValue + (int)shiftSpinner.getValue()) % 26) + adjustmentValue;
				else
					newValue = ((asciiValue - adjustmentValue - (int)shiftSpinner.getValue()) % 26) + adjustmentValue;
				
				output[i] = (char)newValue;
			}		
		}
		
		outputLabel.setText(new String(output));	
	}

}