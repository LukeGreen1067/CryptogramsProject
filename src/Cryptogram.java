import java.util.ArrayList;
import java.util.*;

public class Cryptogram {
	private char[] abc;
    private ArrayList<Character> abckey;
    private ArrayList<Integer> numkey;
    private char[] charphrase;
    private char[] numphrase;
    private char[] temp;


    public Cryptogram(String Phrase)
    {
        abc = new char[26];
        abckey = new ArrayList<>();
        numkey = new ArrayList<>();
        charphrase = Phrase.toCharArray();
        numphrase = Phrase.toCharArray();
        for (int i = 0; i < 26; i++) {
            abc[i] = (char) (65 + i);
            abckey.add(abc[i]);
        }
        //Constructing Keys
        Collections.shuffle(abckey);
        for (int i = 0; i < abckey.size(); i++) {
            for (int j = 0; j < abc.length; j++) {
                if (abckey.get(i) == abc[j])
                {
                    numkey.add(j);
                }
            }
        }
        for (int x = 0; x < charphrase.length; x++) {
            for (int j = 0; j < 26; j++) {
                if (Character.toUpperCase(charphrase[x]) == abc[j])
                {
                    charphrase[x] = abckey.get(j);
                    int a = numkey.get(j);
                    numphrase[x] = (char) a;
                    break;
                }
            }
        }

    }

    public Cryptogram(String Phrase, String Key){
        charphrase = Phrase.toCharArray();
        abckey = new ArrayList<>();
        temp = Key.toCharArray();
        abc = new char[26];
        List<char[]> asList = Arrays.asList(temp);
        List<Character> abcSaved = abckey;
        for (char c : temp) {
            abcSaved.add(c);
        }

        for (int i = 0; i < 26; i++) {
            abc[i] = (char) (65 + i);
        }

        for (int x = 0; x < charphrase.length; x++) {
            for (int j = 0; j < 26; j++) {
                if (Character.toUpperCase(charphrase[x]) == abc[j])
                {
                    charphrase[x] = abcSaved.get(j);
                    break;
                }
            }
        }
    }

    public String getCypher(){
        String key = "";
        for(int i = 0; i < 26; i++) {
            key += abckey.get(i).toString();
        }
        return key;
    }

    public String getkey()
    {
        String key = "Original: ";
        for (int i = 0; i < 26; i++) {
            key = key + abc[i];
            if(i < 25){
                key += ", ";
            }
        }
        key = key + "\nCipher:   ";
        for (int i = 0; i < 26; i++) {
            key = key + abckey.get(i).toString();
            if(i < 25){
                key += ", ";
            }
        }
        return key;

    }

    public String getnumKey()
    {
        String key = "Original: ";
        for (int i = 0; i < 26; i++) {
            key = key + abc[i];
            if(i < 25){
                key += ", ";
            }
        }
        key = key + "\nCipher:   ";
        for (int i = 0; i < 26; i++) {
            key = key + numkey.get(i).toString();
            if(i < 25){
                key += ", ";
            }
        }
        return key;

    }

    public HashMap<Character, Integer> getFreq()
    {
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for(int i = 0; i < charphrase.length; i++){
            if(Character.isLetter(charphrase[i])){
                char c = charphrase[i];
                Integer val = map.get(c);
                if(val != null){
                    map.put(c, val + 1);
                }
                else {
                    map.put(c, 1);
                }
            }
        }
        return map;
    }

    public String getPhrase()
    {
        String Phrase = "";
        for (int i = 0; i < charphrase.length; i++) {
            Phrase = Phrase + charphrase[i];
        }
        return Phrase;
    }

    public String Decipher()
    {
        String Dephrase = "";
        for (int x = 0; x < charphrase.length; x++) {
            for (int j = 0; j < 26; j++) {
                if (charphrase[x] == abckey.get(j))
                {
                    Dephrase = Dephrase + abc[j];
                    break;
                }
                else if(charphrase[x] == Character.toLowerCase(abckey.get(j)))
                {
                    Dephrase = Dephrase + Character.toLowerCase(abc[j]);
                    break;
                }
                else if (!Character.isLetter(charphrase[x]))
                {
                    Dephrase = Dephrase + charphrase[x];
                    break;
                }
            }
        }

        return Dephrase;
    }
}
