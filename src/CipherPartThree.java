import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CipherPartThree 
{

	public static void main(String[] args) throws FileNotFoundException 
	{
		Scanner user = new Scanner(System.in);
		System.out.print("Would you like to encrypt, decrypt, or crack a file? ");
		String crypt = user.nextLine();
		boolean pizza = true;
		boolean endgame = true;
		String fileName = "";
		while(!crypt.equals("encrypt") && !crypt.equals("decrypt") && !crypt.equals("crack"))
		{
			System.out.print("\nInvalid Response. Would you like to encrypt or decrypt a file? ");
			crypt = user.nextLine();
		}
		
		int shift = 0;
		if(crypt.equals("encrypt"))
		{
			pizza = true;
			endgame = false;
			System.out.print("\nEnter a filename to encrypt: ");
			fileName = user.nextLine();
			System.out.print("\nHow many places should the alphabet be shifted? ");
			shift = user.nextInt();
			user.nextLine();
		}
		else if(crypt.equals("decrypt"))
		{
			pizza = false;
			endgame = false;
			System.out.print("Enter a filename to decrypt: ");
			fileName = user.nextLine();
			System.out.print("\nHow many places should the alphabet be shifted? ");
			shift = user.nextInt();
			user.nextLine();
		}
		else
		{
			pizza = false;
			endgame = true;
			System.out.print("Enter a filname to crack: ");
			fileName = user.nextLine();
		}
		
		
		String fin = caesar_cipher(fileName, pizza, shift, endgame);
		System.out.print("\n" + fin);
		
		user.close();

	}
	
	public static String caesar_cipher(String fileName, boolean encrypt, int shiftAmount, boolean crack) throws FileNotFoundException
	{
		char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h','i','j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
		char[] upperAlphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
		char[] lowerCipher = new char[26];
		char[] upperCipher = new char[26];
		int shift = shiftAmount;
		Scanner user = new Scanner(System.in);
		boolean endgame = crack;
		
		if(encrypt)
		{
			for(int i = 0; i<lowerCipher.length;i++)
			{
				if((i+shift) % 26 <0)
					lowerCipher[i] = alphabet[26 + (i+shift)];
			
				else
					lowerCipher[i]=alphabet[(i+shift) % 26];
				
			}
			
			for(int j = 0; j<upperCipher.length; j++)
				upperCipher[j] = (char) (lowerCipher[j] - 32);
			
			File myFile = new File(fileName);
			Scanner inputFile = new Scanner(myFile);
			PrintWriter outputFile = new PrintWriter(fileName.substring(0, fileName.length()-4) + "_ENC.txt");
			
			String mess = "";
			String messEnc = "";
			while(inputFile.hasNext())
			{
				mess += inputFile.nextLine();
				mess += "\n";
				
			}
			
			int index = 0;
			for(int i=0; i<mess.length(); i++)
			{
				index = 0;
				if(isUpper(mess.charAt(i)))
				{
					while(mess.charAt(i) != upperAlphabet[index])
						index++;
					messEnc += upperCipher[index];
					outputFile.print(upperCipher[index]);
				}
				else if(isLower(mess.charAt(i)))
				{
					while(mess.charAt(i) != alphabet[index])
						index++;
					messEnc += lowerCipher[index];
					outputFile.print(lowerCipher[index]);
				}
				else if(mess.charAt(i) == '\n')
				{
					messEnc += 'n';
					outputFile.println();
				}
				else
				{
					messEnc += mess.charAt(i);
					outputFile.print(mess.charAt(i));
				}
			}
			
			outputFile.close();
			inputFile.close();
			user.close();
			String result = "Result written to " + fileName.substring(0, fileName.length()-4) + "_ENC.txt";
			return result;
		}
		else if(!encrypt && !endgame)
		{
			for(int i = 0; i<lowerCipher.length;i++)
			{
				if(i + shift % 26 < 0)
					lowerCipher[i] = (char) (26 + (i+shift));
				else
					lowerCipher[i] = alphabet[(i+shift) % 26];
				
			}
			
			for(int j = 0; j<upperCipher.length; j++)
				upperCipher[j] = (char) (lowerCipher[j] - 32);
			
			File myFile = new File(fileName);
			Scanner inputFile = new Scanner(myFile);
			PrintWriter outputFile = new PrintWriter(fileName.substring(0, fileName.length()-4) + "_DEC.txt");
			
			String encMess = "";
			String decMess = "";
			while(inputFile.hasNext())
			{
				encMess += inputFile.nextLine();
				encMess += "\n";	
			}
			
			int index = 0;
			for(int i=0; i<encMess.length(); i++)
			{
				index = 0;
				if(isUpper(encMess.charAt(i)))
				{
					while(encMess.charAt(i) != upperCipher[index])
						index++;
					decMess += upperAlphabet[index];
					outputFile.print(upperAlphabet[index]);
				}
				else if(isLower(encMess.charAt(i)))
				{
					while(encMess.charAt(i) != lowerCipher[index])
						index++;
					decMess += alphabet[index];
					outputFile.print(alphabet[index]);
				}
				else if(encMess.charAt(i) == '\n')
				{
					decMess += '\n';
					outputFile.println();
				}
				else
				{
					decMess += encMess.charAt(i);
					outputFile.print(encMess.charAt(i));
				}
			}
			outputFile.close();
			inputFile.close();
			user.close();
			String result = "Result written to " + fileName.substring(0, fileName.length()-4) + "_DEC.txt";
			return result;
		}
		else
		{
			File myFile = new File(fileName);
			Scanner inputFile = new Scanner(myFile);
			PrintWriter outputFile = new PrintWriter(fileName.substring(0, fileName.length()-4) + "_DEC.txt");
			String encMess = "";
			String decMess = "";
			while(inputFile.hasNext())
			{
				encMess += inputFile.nextLine();
				encMess += "\n";
			}
					
			int q = 1;
			boolean cont = true;
			String response = "";
					
			while(cont)
			{
				decMess = "";
				for(int i = 0; i<encMess.length(); i++)
				{
					if(isUpper(encMess.charAt(i)))
					{
						if(encMess.charAt(i) + q > 'Z')
							decMess += (char) (upperAlphabet[(encMess.charAt(i) + q) % 91]);
						else
							decMess += (char) (encMess.charAt(i) + q);
					}
					else if(isLower(encMess.charAt(i)))
					{
						if(encMess.charAt(i) + q > 'z')
							decMess += (char) (alphabet[(encMess.charAt(i)+q) % 123]);
						else
							decMess += (char) (encMess.charAt(i) + q);
					}
					else
						decMess += encMess.charAt(i);
				}
				System.out.println(decMess.substring(0, 100));
				System.out.print("Does this look correct? y/n? ");
				response = user.nextLine();
				while(!response.equals("y") && !response.equals("n"))
				{
					System.out.print("\nPlease enter y/n: ");
					response = user.nextLine();
				}
				if(response.equals("n"))
					cont = true;
				else
					cont = false;
				System.out.println();
				q++;
				
			}
			for(int w = 0; w < decMess.length(); w++)
			{
				if(decMess.charAt(w) == '\n')
					outputFile.println();
				else
					outputFile.print(decMess.charAt(w));
			}
			
			outputFile.close();
			inputFile.close();
			user.close();
			String result = "Result written to " + fileName.substring(0, fileName.length()-4) + "_DEC.txt";
			return result;
			
		}
		
	}
	
	
	public static boolean isLower(char c)
	{
		return c >= 'a' && c <= 'z';
	}
	
	public static boolean isUpper(char c)
	{
		return c >= 'A' && c <= 'Z';
	}

}
