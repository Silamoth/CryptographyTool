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
		
		String input = getInput();
		
		int matrices = input.length() / keyString.length() + 1;	//TODO: Double check if need to add another or not; I think I do
		index = 0;
		split = input.length() / matrices;
		ArrayList<int[][]> inputMatrices = new ArrayList<int[][]>();
		
		for (int i = 0; i < matrices; i++)
		{
			String matrixString = input.substring(index, index + split);
			index += split;
			
			//Now have letters for a matrix; must now create the matrix
			
			int matrixSplit = matrixString.length() / length;
			int matrixIndex = 0;
			
			int newMatrix[][] = new int[length][length];
			
			for (int x = 0; x < matrixSplit; x++)
			{
				char[] row = matrixString.substring(matrixIndex, matrixIndex + matrixSplit).toCharArray();
				matrixIndex += matrixSplit;
				
				for (int y = 0; y < row.length; y++)
				{
					try
					{
						newMatrix[x][y] = (int)row[y] - 97;	//Adjust for ASCII values
					}
					catch (Exception ex)
					{
						newMatrix[x][y] = 0;
					}
				}				
			}
			
			inputMatrices.add(newMatrix);
		}
		
		//Now have encryption matrix and ArrayList of input matrices
		//Can't do this in previous set of loops because matrix multiplication
		
		ArrayList<char[][]> outputMatrices = new ArrayList<char[][]>();
		
		//Loop through each input matrix
		for (int i = 0; i < inputMatrices.size(); i++)
		{	
			char[][] intResult = multiplyMatrices(encryptionMatrix, inputMatrices.get(i));
			
			
		}
	}
	
	char[][] multiplyMatrices(int[][] matrixOne, int[][] matrixTwo)
	{
		return null;
	}
}