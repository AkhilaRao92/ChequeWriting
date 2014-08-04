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
                if(amount >= 20)
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
        return str;
    }
    
    private static String fixAnd(String rupee){
        ArrayList<String> denomArray = new ArrayList<String>();
        denomArray.add("BILLION");
        denomArray.add("MILLION");
        denomArray.add("THOUSAND");
        denomArray.add("AND");
        
        String [] splittedRupee = rupee.split(" ");
        for(int i=0;i<splittedRupee.length;i++)
        {
            if(splittedRupee[i].equals("HUNDRED") && !denomArray.contains(splittedRupee[i+1]))
            {
                splittedRupee[i] = splittedRupee[i].replace("HUNDRED", "HUNDRED AND");
            }
        }
        String finalCurrency=new String("");
        for(String s:splittedRupee)
        {
            finalCurrency+=s+" ";
        }
        return finalCurrency;
    }
    
    public static String threeDigits(int s)
    {
        int quotient = s/100;
        int reminder = s%100;
        return numToStr(s);
        
    }
    
    public static String printInWesternCurrency(int n)
    {
        Map<Integer, String> MultipleofTens = new HashMap(){{
            put(1000000000,"TRILLION ");
            put(1000000, "MILLION ");
            put(1000,"THOUSAND ");
            put(100,"HUNDRED ");
            put(1,"");}};
        
        String RupeesInWords = "";
        
        int[] denom = {1000000000, 1000000, 1000, 100, 1};
        
        int Rupees = n;
        if(Rupees == 0)
            return "Zero Rupees only ";
        for(int j=0; j < denom.length; j++)
        {
            int d = Rupees/denom[j];
            if(d>0)
            {
                RupeesInWords += numToStr(d) + MultipleofTens.get(denom[j]);
            }
            
            Rupees = Rupees % denom[j];
        }
        return RupeesInWords ;
    }
    
    public static void main(String[] args){
        String amount = "900389008.99";
        String paise = getPaise(amount);
        paise = updatePaiseFormat(paise);
        paise = paiseWords(paise);
        String rupee = getRupee(amount);
        rupee = printInWesternCurrency(Integer.parseInt(rupee));
        rupee = fixAnd(rupee) + "RUPEES ";
        if(paise.length() > 2)
            rupee += "AND " + paise.toUpperCase()+"PAISE";
        rupee += " ONLY";
        System.out.println(rupee);
        
    }
    
}