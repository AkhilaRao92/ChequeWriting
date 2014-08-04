import java.util.Map.Entry;
import java.util.*;

class Pair<L,R> {
    private L l;
    private R r;
    public Pair(L l, R r){
        this.l = l;
        this.r = r;
    }
    public L getL(){ return l; }
    public R getR(){ return r; }
    public void setL(L l){ this.l = l; }
    public void setR(R r){ this.r = r; }
}

public class Fig2word {
	
	public static Map <Integer , String> denominations;
	public static String [] tens;
	public static String [] upto19;
	public static String rupeeInWords = new String();
	public static boolean isWestern;
	
	public static void setDenominations(){
		if (isWestern) {
			denominations = new LinkedHashMap<Integer, String>();

			denominations.put(1000000000, "billion ");
			denominations.put(1000000, "million ");
			denominations.put(1000, "thousand ");
			denominations.put(1, "");
		} else {
			denominations = new LinkedHashMap<Integer, String>();

			denominations.put(10000000, "crore ");
			denominations.put(100000, "lakh ");
			denominations.put(1000, "thousand ");
			denominations.put(100, "hundred ");
			denominations.put(1, "");
		}
		
		upto19 = new String[] {"", "one ", "two ", "three ", "four ", "five ", "six ", "seven ", "eight ", "nine ", "ten ",
				"eleven ", "twelve ", "thirteen ", "fourteen ", "fifteen ", "sixteen ", "seventeen ", "eighteen ", "ninteen "};
		tens = new String[] {"","", "twenty ", "thirty ", "forty ", "fifty ", "sixty ", "seventy ", "eighty ", "ninty "};
	}	

	public static void main(String args[])
	{
		String amount = 
				//"011,020,012"; 
				"01,10,22,110";
		amount = amount.replace(",", "");
//		String mode = "I";
//		if (mode.equals("W"))
//			isWestern = true;
//		else if (mode.equals("I"))
//			isWestern = false;
//		else {
//			System.out.println("system of digits:" + mode + " not supported");
//			System.exit(0);
//		}
		isWestern = false;
		setDenominations();
		ArrayList<List<Integer>> valueDenomListIndian = convertToDenomValuePairs(Integer.parseInt(amount));
		System.out.println(convertToWords(valueDenomListIndian).toUpperCase()+"\n");
		
		isWestern = true;
		setDenominations();
		ArrayList<List<Integer>> valueDenomListWestern = convertToDenomValuePairs(Integer.parseInt(amount));
		System.out.println(convertToWords(valueDenomListWestern).toUpperCase());
		
	}

	private static String convertToWords(
			ArrayList<List<Integer>> valueDenomList) {
		
		System.out.println(valueDenomList);
		List<List<String>> valueDenomListAsWord = convertValuesToWord(valueDenomList);
		System.out.println(valueDenomListAsWord);
		List<List<String>> pairListAsWord = convertDenomsToWord(valueDenomListAsWord);
		System.out.println(pairListAsWord);
		String amountAsWords = convertPairToWords(pairListAsWord);
		return amountAsWords;
		
	}

	private static List<List<String>> convertDenomsToWord(
			List<List<String>> valueDenomListAsWord) {
		List<List<String>> pairOfList = new LinkedList<List<String>>();
		for(List<String> value : valueDenomListAsWord){
			List<String> pair = new LinkedList<String>();
			pair.add(value.get(0));
			pair.add(denominations.get(Integer.parseInt(value.get(1))));
			pairOfList.add(pair);
		}
		return pairOfList;
	}

	private static List<List<String>> convertValuesToWord(
			ArrayList<List<Integer>> valueDenomList) {
		List<List<String>> pairOfList = new LinkedList<List<String>>();
		for(List<Integer> value : valueDenomList){
			List<String> pair = new LinkedList<String>();
			pair.add(convertDigitToWord(value.get(0)));
			pair.add(String.valueOf(value.get(1)));
			pairOfList.add(pair);
		}
		return pairOfList;
	}

	private static ArrayList<List<Integer>> convertToDenomValuePairs(int number) {
		List<Integer> denominationKeyList = new LinkedList<Integer>();
		denominationKeyList.addAll( denominations.keySet());
		Collections.sort(denominationKeyList,Collections.reverseOrder());
		ArrayList<List<Integer>> coefficients  = new ArrayList<List<Integer>>();
		for (int denomAsNum : denominationKeyList) {
			
			Integer digit = number / denomAsNum;
			if(digit > 0){
				List<Integer> pair = new LinkedList<Integer>();
				pair.add(digit);
				pair.add(denomAsNum);
				coefficients.add(pair);
			}
			number %= denomAsNum;
		}
		return coefficients;
	}

	private static String convertPairToWords(
			List <List<String>> denomList) {
		
		String amountAsWords = new String(); 
		for(List<String>  pair : denomList){
			String and = new String();
			if(!isWestern &&  pair.get(1).equals(""))
				and = "and ";
			amountAsWords += and + " "+ pair.get(0)+pair.get(1);
		}
		return amountAsWords;
	}

	 

	private static String convertDigitToWord(Integer amount) {
		if (amount < 19) {
			return upto19[amount];
		} else if (amount >= 100 && isWestern) {
			int hundred = amount / 100;
			String and = new String();
			if(amount %100 != 0)
				and = "and ";
			return upto19[hundred] + "hundred " + and
					+ convertDigitToWord(amount % 100);
		} else {
			int ten = amount % 100;
			return tens[(ten / 10)] + upto19[ten % 10];
		}
	}
		
}
