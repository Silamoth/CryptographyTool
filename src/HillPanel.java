import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import javax.swing.text.DocumentFilter.FilterBypass;


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
		
		PlainDocument aDoc = (PlainDocument) keyTextBox.getDocument();
	    aDoc.setDocumentFilter(new MyAlphabetFilter());
		
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
		char[] inputArray = input.toCharArray();
		ArrayList<NonAlphabetChar> specialChars = new ArrayList<NonAlphabetChar>();
		StringBuilder sb = new StringBuilder(input);
		
		for (int i = 0; i < inputArray.length; i++)
		{
			int num = inputArray[i];
			
			if (num < 97 || num > 122)
			{
				NonAlphabetChar newChar = new NonAlphabetChar(i - specialChars.size() + 1, inputArray[i]);
				specialChars.add(newChar);
				sb.deleteCharAt(i - specialChars.size() + 1);
				System.out.println(newChar.getValue());
			}
		}
		
		input = sb.toString();
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
		
		int[][] multipliedMatrix;
		
		if (getEncryptStatus())
			multipliedMatrix = encryptionMatrix;
		else
		{
			//Must create decryption matrix
			
			multipliedMatrix = new int[encryptionMatrix.length][encryptionMatrix.length];
			
			int determinant = calcDeterminant(encryptionMatrix);
			
			int inverse = 1;
			
			while ((determinant * inverse) % 26 != 1)
			{
				inverse++;
			}
			
			int[][] adjugate = calcAdjugate(encryptionMatrix);
			
			for (int x = 0; x < multipliedMatrix.length; x++)
			{
				for (int y = 0; y < multipliedMatrix[x].length; y++)
				{
					multipliedMatrix[x][y] = (adjugate[x][y] * inverse) % 26;
					
					while (multipliedMatrix[x][y] < 0)
					{
						multipliedMatrix[x][y] += 26;
					}
				}
			}
		}
				
		//Loop through each input matrix
		for (int i = 0; i < inputMatrices.size(); i++)
		{	
			int[][] temp = inputMatrices.get(i);
			char[][] result = multiplyMatrices(multipliedMatrix, inputMatrices.get(i));
			
			for (int x = 0; x < result.length; x++)
			{
				for (int y = 0; y < result[x].length; y++)
				{
					output += result[y][x];
				}
			}
		}
		
		output = output.substring(0, originalLength);
		sb = new StringBuilder(output);
		
		for (int i = specialChars.size() - 1; i >= 0; i--)
		{
			//NonAlphabetChar temp = specialChars.get(i);
			sb.insert(specialChars.get(i).getIndex() - 1, specialChars.get(i).getValue());
		}
		
		setOutput(sb.toString());
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
	
	int calcDeterminant(int[][] matrix)
	{
		//I'm going to "cross out" the first row
		
		int determinant = 0;
		
		if (matrix.length > 2)
		{
			ArrayList<int[][]> cofactors = calcCofactors(matrix);
			
			for (int i = 0; i < cofactors.size(); i++)
			{
				determinant += matrix[0][i] * calcDeterminant(cofactors.get(i)) * Math.pow(-1, i);
			}
		}
		else if (matrix.length == 2)
			determinant = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
		else
			return matrix[0][0];
		
		while (determinant < 0)
			determinant += 26;
		
		return determinant % 26;
	}
	
	int[][] calcAdjugate(int[][] matrix)
	{
		int[][] minors = new int[matrix.length][matrix[0].length];
		
		ArrayList<int[][]> cofactors = calcCofactors(matrix);
		int index = 0;
		
		for (int x = 0; x < matrix.length; x++)
		{
			int base = (int)Math.pow(-1, x);
			//When base is 1, the minor element can never be negative
			for (int y = 0; y < matrix[x].length; y++)
			{
				int num = cofactors.get(index)[0][0];
				System.out.println(num);
				minors[x][y] = calcDeterminant(cofactors.get(index)) * (int)Math.pow(-1, y) * base;
				index++;
			}
		}
		
		int[][] adjugate = new int[matrix.length][matrix[0].length];
		
		for (int x = 0; x < minors[0].length; x++)
		{
			for (int y = 0; y < minors.length; y++)
			{
				adjugate[x][y] = minors[x][y];
			}
		}
		
		return adjugate;
	}
	
	ArrayList<int[][]> calcCofactors(int[][] matrix)
	{
		ArrayList<int[][]> cofactors = new ArrayList<int[][]>();
		
		for (int i = 0; i < matrix.length; i++)
		{
			//i represents row to get crossed out
			
			for (int j = 0; j < matrix[i].length; j++)
			{
				//j represents column to get crossed out
				
				int[][] cofactor = new int[matrix[0].length - 1][matrix[0].length - 1];
				
				int xSkip = 0;
				for (int x = 0; x < matrix.length; x++)
				{
					int ySkip = 0;
					for (int y = 0; y < matrix[x].length; y++)
					{
						if (x == i)
							xSkip = 1;
						else
						{
							if (y == j)
								ySkip = 1;
							else
							{
								cofactor[y - ySkip][x - xSkip] = matrix[y][x];
							}
						}
					}
				}
				
				cofactors.add(cofactor);
			}
		}
		
		return cofactors;
	}
}

class MyAlphabetFilter extends DocumentFilter 
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
		   //Allow empty string
		   if (text.equals(""))
			   return true;
		   
		   char[] input = text.toLowerCase().toCharArray();
		   
		   for (int i = 0; i < input.length; i++)
		   {
			   int num = input[i];
			   
			   if (num < 97 || num > 122)
				   return false;
		   }
		   
		   return true;
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

class NonAlphabetChar
{
	private int index;
	private char value;
	
	public NonAlphabetChar(int index, char value)
	{
		this.index = index;
		this.value = value;
	}
	
	public int getIndex()
	{
		return index;
	}
	
	public char getValue()
	{
		return value;
	}
}