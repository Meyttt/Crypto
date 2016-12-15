package ru.mirea.common; /**
 * Created by Александра on 13.11.2016.
 */

import java.util.HashMap;

class Scrambler {
    HashMap<String,String> coder = new HashMap<String, String>();
    HashMap<String,String> decoder = new HashMap<String, String>();
    int shift;
    Scrambler(int shift){
        this.shift=shift;
        int i;
        char c;
        String other=" 0123456789,-+=.?";
        for(i=0,c='a';i<26;i++,c++){
            coder.put(c+"", String.format("%02d",i+3));
            decoder.put(String.format("%02d",i+3),c+"");
        }
        for(int k=0;k<other.length();k++){
            coder.put(other.charAt(k)+"",String.format("%02d",k+i+3));
            decoder.put(String.format("%02d",k+i+3),other.charAt(k)+"");
        }
    }
    public String encode(String originalString){
        String answer="";
        for (int i=0;i<originalString.length();i++){
            String current = originalString.charAt(i)+"";
            if(coder.containsKey(current)){
                answer+=coder.get(current);
            }
        }
        return answer;
    }
    public String decode(String originalString){
        String answer="";
        for (int i=0;i<originalString.length();i++){
            String current = originalString.charAt(i)+"";
            i++;
            current += originalString.charAt(i)+"";
            if(decoder.containsKey(current)){
                answer+=decoder.get(current);
            }else{
            }
        }
        return answer;
    }

}
