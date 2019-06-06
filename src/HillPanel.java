import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;

public class HillPanel extends CipherPanel 
{
	private JTextField keyTextBox;
	
	public HillPanel() 
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
		
		//TODO: Add help box - possibly something you hover over - to explain things, especially the key for Hill
	}
	
	public void goButtonClicked()
	{
		super.goButtonClicked();
		
		String keyString = keyTextBox.getText().toLowerCase();
		
		int addNumber = 97;	//Start at lowercase 'a'
		
		while (Math.sqrt(keyString.length()) != (int)Math.sqrt(keyString.length()))	//Hopefully check if not perfect square
		{
			//Pad the alphabet to the key until it becomes a perfect square
			
			keyString += (char)addNumber;
			addNumber++;
			
			if (addNumber > 122)
				addNumber = 97;
		}
		
		int length = (int)Math.sqrt(keyString.length());
		int[][] encryptionMatrix = new int[length][length];
		
		int split = keyString.length() / length;
		int index = 0;
		
		for (int i = 0; i < split; i++)
		{
			char[] splitKeyRow = keyString.substring(index, index + split).toCharArray();
			
			for (int x = 0; x < splitKeyRow.length; x++)
			{
				encryptionMatrix[i][x] = (int)splitKeyRow[x] - 97;	//Adjust for ASCII values
			}
			
			index += split;
		}
		
		String input = getInput().toLowerCase();
		int originalLength = input.length();
		
		//Pad input to be divisible by key
		
		while (input.length() % keyString.length() != 0)
		{
			input += "a";
		}
		
		int matrices = input.length() / keyString.length();
		index = 0;
		split = keyString.length();
		ArrayList<int[][]> inputMatrices = new ArrayList<int[][]>();
		
		for (int i = 0; i < matrices; i++)
		{
			String matrixString;
			try
			{
				matrixString = input.substring(index, index + split);
				index += split;
			}
			catch (Exception ex)
			{
				matrixString = input.substring(index);
				index += split;
			}
			
			//Now have letters for a matrix; must now create the matrix
			
			int newMatrix[][] = new int[length][length];
			
			//System.out.println(matrixString);	//Confirms that input is being split up properly - at least for inputs divisible by key
			
			char[] row = matrixString.toCharArray();
			int counter = 0;
			for (int x = 0; x < length; x++)
			{
				for (int y = 0; y < length; y++)
				{
					newMatrix[y][x] = row[counter] - 97;
					counter++;
				}
			}
			
			inputMatrices.add(newMatrix);		//Properly populated
		}
		
		//Now have encryption matrix and ArrayList of input matrices
		//Can't do this in previous set of loops because matrix multiplication
		
		String output = "";
		
		//Loop through each input matrix
		for (int i = 0; i < inputMatrices.size(); i++)
		{	
			int[][] temp = inputMatrices.get(i);
			char[][] result = multiplyMatrices(encryptionMatrix, inputMatrices.get(i));
			
			for (int x = 0; x < result.length; x++)
			{
				for (int y = 0; y < result[x].length; y++)
				{
					output += result[y][x];
				}
			}
		}
		
		setOutput(output.substring(0, originalLength));
		//setOutput(output);
	}
	
	//Assume equally-sized square matrices
	//Safe assumption for this purpose
	char[][] multiplyMatrices(int[][] matrixOne, int[][] matrixTwo)
	{
		char[][] output = new char[matrixOne.length][matrixOne.length];
		
		//Loop through each row of first matrix
		for (int i = 0; i < matrixOne.length; i++)
		{
			//Each row of first needs to get paired with each column of second
			
			for (int x = 0; x < matrixTwo[0].length; x++)
			{
				int sum = 0;
				
				for (int y = 0; y < matrixTwo.length; y++)
				{
					sum += matrixOne[i][y] * matrixTwo[y][x];
				}
				
				output[i][x] = (char)((sum % 26) + 97);
				sum = 0;
			}
		}
		
		return output;
	}
}