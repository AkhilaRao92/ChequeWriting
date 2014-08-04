package temp;

public class paise {

	public static String getPaise(double amount)
    {
	String paise = String.valueOf(amount); 
    paise = paise.split("\\.")[1];
    return paise; 
    }
	
	public static String updatePaiseFormat(String paise)
	{
		if (paise.length() <2)
			paise += "0";
		if (paise.length()>2)
			paise = paise.substring(0, Math.min(paise.length(), 2));
		return paise;
	}
	
	public static String paiseWords(String paise)
	{
		String[] tens = {"", "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninty"};
		String[] ones = {"", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
		String paiseWord=new String();
		
		char[] paiseChar = paise.toCharArray();
		int count = 1;
		for(char p: paiseChar)
		{
			if (count==1)
				paiseWord +=tens[p-'0'];
			else
				paiseWord +=ones[p-'0'];
			paiseWord +=" ";
			count++;
		}
		
		return paiseWord;
	}
	
	
	public static void main(String[] args)
	{
		double amount = 102.987;
		String paise = getPaise(amount);
		paise = updatePaiseFormat(paise);
		paise = paiseWords(paise);
		
		System.out.println(paise);
	}
	
}
