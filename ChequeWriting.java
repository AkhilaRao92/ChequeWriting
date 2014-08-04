import java.util.*;
public class ChequeWriting{

    static String[] oneToNineteen = { " ", "ONE ", "TWO ", "THREE ", "FOUR ", "FIVE ", "SIX ",
            "SEVEN ", "EIGHT ", "NINE ", "TEN ", "ELEVEN ", "TWELVE ",
            "THIRTEEN ", "FOURTEEN ", "FIFTEEN ", "SIXTEEN ", "SEVENTEEN ",
            "EIGHTEEN ", "NINETEEN " };
    
    static String[] tens = { " ", " ", "TWENTY ", "THIRTY ", "FOURTY ", "FIFTY ",
            "SIXTY ", "SEVENTY ", "EIGHTY ", "NINETY " };
            
    static HashMap<Integer,String> numToWordMap = new HashMap<Integer,String>(){
        {
            put(1,"");
            put(10,"");
            put(100,"HUNDRED ");
            put(1000,"THOUSAND ");
            put(100000,"LAKH ");
            put(10000000,"CRORE ");
        }
    };
    
    static  List<String> denoms = Arrays.asList("HUNDRED","THOUSAND","LAKH","CRORE");
    static List<Integer> powersOfTen = Arrays.asList(10000000,100000,1000,100,10,1);
    
    public static String getPaise(String amount)
    {
        String paise = String.valueOf(amount); 
        paise = paise.split("\\.")[1];
        return paise; 
    }
     public static String getRupee(String amount)
    {
        String rupee = String.valueOf(amount); 
        rupee = rupee.split("\\.")[0];
        return rupee; 
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

            
    public static String numToStr(int amount) {
        String str = "";
        int highestPower = 0;
        
        
        if(amount == 0)
            return new String("");
            
        for(int num : powersOfTen){
        
            if(amount/num > 0){
                highestPower = num;
                break;
            }
        }
        
        if (amount >= highestPower) {
            if(highestPower == 10){
                if(amount >=20)
                str += tens[ amount / highestPower] + oneToNineteen[ amount % highestPower]  ;
                else
                str += oneToNineteen[ amount ];
                return str;
            }
            else
                if(amount/highestPower > 20)
                    str += tens[amount / highestPower/10] + oneToNineteen[(amount/highestPower)%10]+  numToWordMap.get(highestPower)  ;
                else
                    str += oneToNineteen[ amount / highestPower] + numToWordMap.get(highestPower) ;
                
            str += numToStr(amount % highestPower);
        }
        
        else if (amount < 20) {
            
            str += oneToNineteen[ amount ];
        }
        
        else {
              str += tens[amount / 10];
              str += numToStr(amount % 10);
        }
        
        return str;
    }
    
    public static String process(String rupee){
        boolean hasDenom = false; 
        String lastDenom = null;
         
        for(String denom : denoms){

            if(rupee.contains(denom)){                    
                 if(!hasDenom){
                    hasDenom = true;
                    lastDenom = denom;
                 }
                 else{
                     rupee=rupee.replace(lastDenom,lastDenom+" AND");
                     break;
                 }
                   
            }
        }
        for(String denom : denoms){
            if(rupee.replace(" AND","").indexOf(denom)+denom.length()+1 >=  rupee.replace(" AND","").length()-1){
                rupee = rupee.replace(" AND","");
            }
        }
        
        
        return rupee;    
        
    }
    public static void main(String[] args){
        String amount = "9389008.99";
    	String paise = getPaise(amount);
		paise = updatePaiseFormat(paise);
		paise = paiseWords(paise);
        String rupee = getRupee(amount);
        rupee = numToStr(Integer.parseInt(rupee));
        rupee = process(rupee) + "RUPEES ";
        if(paise.length() > 2)
            rupee += "AND " + paise.toUpperCase()+"PAISE";
        
		System.out.println(rupee);
        
    }

}
