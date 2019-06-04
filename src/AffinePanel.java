import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;
import javax.swing.text.PlainDocument;

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
		
		PlainDocument aDoc = (PlainDocument) aTextField.getDocument();
	    aDoc.setDocumentFilter(new MyIntFilter());
	    
	    PlainDocument bDoc = (PlainDocument) bTextField.getDocument();
	    bDoc.setDocumentFilter(new MyIntFilter());   
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
		
		char[] input = getInput().toCharArray();
		char[] output = new char[input.length];
		int a = Integer.parseInt(aTextField.getText());
		int b = Integer.parseInt(bTextField.getText());
		
		if (getEncryptStatus())
		{
			for (int i = 0; i < input.length; i++)
			{
				
				if (!Character.isJavaLetter(input[i]))
					output[i] = input[i];
				else
				{
					int adjustmentValue;
					
					if (Character.isUpperCase(input[i]))
						adjustmentValue = 65;
					else
						adjustmentValue = 97;
					
					int asciiValue = (int)input[i];
										
					int newValue = ((((asciiValue - adjustmentValue) * a) + b) % 26) + adjustmentValue;
					
					output[i] = (char)newValue;
				}
			}
		}
		else
		{
			ArrayList<Integer> encryptedValues = new ArrayList<Integer>();	
			
			for (int x = 0; x < 26; x++)
			{
				encryptedValues.add((x * a + b) % 26);
			}
			
			for (int i = 0; i < input.length; i++)
			{
				
				if (!Character.isJavaLetter(input[i]))
					output[i] = input[i];
				else
				{
					int adjustmentValue;
					
					if (Character.isUpperCase(input[i]))
						adjustmentValue = 65;
					else
						adjustmentValue = 97;
					
					int asciiValue = (int)input[i];
					int adjustedValue = asciiValue - adjustmentValue;
					
					if (!encryptedValues.contains(adjustedValue))
					{
						JOptionPane.showMessageDialog(this.getParent(), "Encryption does not contain the letter '" + (char)(adjustedValue + adjustmentValue) + "'.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (encryptedValues.indexOf(adjustedValue) != encryptedValues.lastIndexOf(adjustedValue))
					{
						JOptionPane.showMessageDialog(this.getParent(), "Encryption contains multiple instances of the letter '" + (char)(adjustedValue + adjustmentValue) + "'.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
						
					int newValue = encryptedValues.indexOf(adjustedValue);
					
					output[i] = (char)(newValue + adjustmentValue);
				}
			}
		}	
		
		setOutput(new String(output));
	}
}

//Taken from StackOverflow
//https://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers

class MyIntFilter extends DocumentFilter 
{
	   @Override
	   public void insertString(FilterBypass fb, int offset, String string,
	         AttributeSet attr) throws BadLocationException 
	   {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.insert(offset, string);

	      if (test(sb.toString())) 
	      {
	         super.insertString(fb, offset, string, attr);
	      } else 
	      {
	         // warn the user and don't allow the insert
	      }
	   }

	   private boolean test(String text) 
	   {
	      try 
	      {
	         Integer.parseInt(text);
	         return true;
	      } catch (NumberFormatException e) 
	      {
	    	  //Added in to allow for empty textfield
	    	  if (text.isEmpty())
	    		  return true;
	    	  
	         return false;
	      }
	   }

	   @Override
	   public void replace(FilterBypass fb, int offset, int length, String text,
	         AttributeSet attrs) throws BadLocationException 
	   {

	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.replace(offset, offset + length, text);

	      if (test(sb.toString())) 
	      {
	         super.replace(fb, offset, length, text, attrs);
	      } else {
	         // warn the user and don't allow the insert
	      }

	   }

	   @Override
	   public void remove(FilterBypass fb, int offset, int length)
	         throws BadLocationException 
	   {
	      Document doc = fb.getDocument();
	      StringBuilder sb = new StringBuilder();
	      sb.append(doc.getText(0, doc.getLength()));
	      sb.delete(offset, offset + length);

	      if (test(sb.toString())) 
	      {
	         super.remove(fb, offset, length);
	      } else 
	      {
	         // warn the user and don't allow the insert
	      }

	   }
	}