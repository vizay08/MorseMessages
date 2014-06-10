package utility.first.morsemessages.morseconverter;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes.Name;

import android.util.Log;

//import soundsupport.Sound;

public class MorseConverter {

	//private Sound sound = new Sound();
	
	private final int MSG = 2345;
	private final int NAME = 5234;
	private final int TEST = 2837;
	
	private char[] alphabets={'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','1','2','3','4','5','6','7','8','9','0'};
	private String[] morse={ ".-"
	  ,"-..."
	  , "-.-."
	  , "-.."
	  , "."
	  , "..-."
	  , "--."
	  , "...."
	  , ".."
	  ,".---"
	  , "-.-"
	  , ".-.."
	  , "--"
	  , "-."
	  , "---"
	  , ".--."
	  , "--.-"
	  , ".-."
	  , "..."
	  
	  , "-"
	  , "..-"
	  , "...-"
	  , ".--"
	  , "-..-"
	  , "-.--"
	  , "--.."
	  
	  ////numbers//////////
	  , ".----"
	  , "..---"
	  , "...--"
	  , "....-"
	  , "....."
	  , "-...."
	  , "--..."
	  , "---.."
	  , "----."
	  , "-----"
	  };
	
	public MorseConverter()
	{
		mapMorseCode();
	}
	
	public char[] getAlphabets(){
		return alphabets;
	}
	
	public String[] getMorseCodes()
	{
		return morse;
	}
	private Map<Character, String> morseMap = new HashMap<Character,String>();
	
	private void mapMorseCode()
	{
		for(int i=0;i<alphabets.length;i++)
		{
			morseMap.put(alphabets[i], morse[i]);
		}
	}
	
	public String getMorseCode(String input,int flag)
	{
	int count = 0,max = 0;
	int len = input.length();
	switch(flag)
	{
	case NAME:
		max = 5;
		break;
	case MSG:
		max = 15;
		break;
	case TEST:
		max = len;
		break;
	default:
		max = 1;
		break;
	}
		/*if(input.contains("+"))
			input = input.replace("+", "");*/
		input = input.toLowerCase();
		Log.d("in MorseConverter getMorseCode method","input "+input);
		String output = "";
		char temp;
		String mt="";
		
		for(int i=0;i<len && count<=max;i++)
		{
			temp=input.charAt(i);
			if(temp==' ')
				output+=" ";
			else{
			mt = morseMap.get(temp);
			
			
			if(mt!=null){
				count++;
				output+=mt;
			}
			}
			output+="  ";
			
		}
		
		Log.d("in MorseConverter getMorseCode"," "+output);
		return output;
	}
	/////dummy for now
	public void playMorseSound(String morseCode) throws InterruptedException
	{
		int i=0;
		char c;
		while(i<morseCode.length())
		{
			c=morseCode.charAt(i);
			if(c=='.'||c=='-')
			{
	//			sound.playSound(c);
				Thread.sleep(400);
			}
			if(c==' ')
			{
				Thread.sleep(1000);
			}
			i++;
		}
	}
}
