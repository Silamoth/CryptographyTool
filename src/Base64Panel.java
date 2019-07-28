import javax.swing.JPanel;

public class Base64Panel extends CipherPanel 
{
	public Base64Panel() 
	{
		this.setPreferredSize(super.getMaximumSize());
	}
	
	public void goButtonClicked()
	{
		super.goButtonClicked();
		
		char[] input = getInput().toCharArray();
		char[] encoding = new char[64];
		int index = 0;
		
		//Create base64 indexing
		for (int i = 65; i <= 90; i++)
		{
			encoding[index] = (char)i;
			index++;
		}
		for (int i = 97; i <= 122; i++)
		{
			encoding[index] = (char)i;
			index++;
		}
		for (int i = 0; i <= 9; i++)
		{
			encoding[index] = Integer.toString(i).toCharArray()[0];
			index++;
		}	
		encoding[62] = '+';
		encoding[63] = '/';
		
		if (getEncryptStatus())
		{
			String binaryString = "";
			
			//Convert each character of the input into binary
			for (int i = 0; i < input.length; i++)
			{
				int asciiValue = (int)input[i];
				
				for (int pow = 7; pow >= 0; pow--)
				{
					int digit = (int)Math.pow(2, pow);
					
					if (asciiValue - digit >= 0)
					{
						asciiValue -= digit;
						binaryString += "1";
					}
					else
						binaryString += "0";
				}
			}
			
			int paddedCount = 0;
			while (binaryString.length() % 24 != 0)
			{
				binaryString += "0";
				paddedCount++;
			}
			
			String output = "";
			
			//Put binary digits into groups of 6
			for (int i = 0; i < binaryString.length(); i += 6)
			{	
				String group = binaryString.substring(i, i + 6);
				
				if (group.equals("000000") && paddedCount >= 6)
				{
					output += "=";
					paddedCount -= 6;
				}
				else
				{
					int value = 0;
					
					for (int x = group.length() - 1; x >= 0; x--)
					{
						if (group.substring(x, x + 1).equals("1"))
							value += (int)Math.pow(2, group.length() - x - 1);
					}
					
					output += encoding[value];
				}
			}
			
			setOutput(output);
		}
		else
		{
			String binaryString = "";
			
			for (int i = 0; i < input.length; i++)
			{
				int num = 0;
				
				if (input[i] != '=')
				{
					//Get indexing of current base64 character
					for (int x = 0; x < encoding.length; x++)
					{
						if (encoding[x] == input[i])
							num = x;
					}
					
					//Get 6 bit binary representation of number				
					for (int pow = 5; pow >= 0; pow--)
					{
						int digit = (int)Math.pow(2, pow);
						
						if (num - digit >= 0)
						{
							num -= digit;
							binaryString += "1";
						}
						else
							binaryString += "0";
					}
				}
			}
						
			//Remove padded 0's
			while (binaryString.length() % 8 != 0)
				binaryString = binaryString.substring(0, binaryString.length() - 1);
			
			//Split into 8 bit groupings
			
			String output = "";
			
			for (int i = 0; i < binaryString.length(); i += 8)
			{
				String group = binaryString.substring(i, i + 8);
				
				int value = 0;
				
				for (int x = group.length() - 1; x >= 0; x--)
				{
					if (group.substring(x, x + 1).equals("1"))
						value += (int)Math.pow(2, group.length() - x - 1);
				}
				
				output += (char)value;
			}
			
			setOutput(output);
		}
	}
}