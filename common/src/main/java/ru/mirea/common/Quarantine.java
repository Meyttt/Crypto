package ru.mirea.common;

import java.io.*;
import java.util.*;

/**
 * Created by Александра on 05.11.2016.
 */
class CustomList<T> extends ArrayList<T> {
    private final int size;
    CustomList(int count){
        super(count);
        this.size=count;
    }
    public void printV(){
        System.out.println("---------------------");
        for (int i=0;i<this.size();i++){
            System.out.println(this.get(i));
        }
    }
    public void addNew(T object){
        if(this.size()==size){
            this.remove(this.size()-1);
        }
        this.add(0, object);
    }
}
public class Quarantine {
    static HashMap<Integer,Integer> loopStatistics = new HashMap<>();
    int originalLength=0;
    private int textLength=0;
    private int countOfDigits=0;
    private int countOfLowerLetters =0;
    private int countOfUpperLetters=0;
    private int countOfOtherSymbols;
    private List<Character> letters = new ArrayList<>();
    private ArrayList<String> result = new ArrayList<>();
    Random rand = new Random();
    public int counterWriting =0;
    public int counter=0;
    private CustomList<String> customList = new CustomList<>(5);
    private CustomList<String> customList2= new CustomList<>(5);
    String[] messageData;

    public Quarantine(String[] messageData) {
        this.messageData = messageData;
        for(char c='a';c<='z';c++){
            letters.add(c);
        }
        for(char c='A';c<='Z';c++){
            letters.add(c);
        }
        for(char c='0';c<='9';c++){
            letters.add(c);
        }
        letters.add(' ');
    }

    //запись в 1 очередь
    Runnable writeWord = new Runnable() {
        @Override
        public synchronized void run() {
            try {
                String oneLine = "";
                for(int i=0;i<messageData.length;i++){
                    oneLine=messageData[i];
                    Thread.sleep(1);
                    originalLength++;
                    customList.addNew(oneLine);
                    counterWriting++;

                    }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    private Thread writing= new Thread(writeWord);
    //работа с 1 очередью
    Runnable readWord = new Runnable() {
        @Override
        public synchronized void run() {
            while (writing.isAlive()|| !customList.isEmpty()) {
                if (!customList.isEmpty()) {
                    try {

                        String row = customList.remove(customList.size() - 1);
                        getStatistics(row);
                        row = deleteExcess(row);
                        customList2.addNew(row);
                        //customList.printV();
                        counter++;
                    } catch (java.lang.ArrayIndexOutOfBoundsException e1) {
                     System.err.println("Reading is faster");
                    } catch (NullPointerException e2) {
//                        System.err.println("Too fast!");
                    }
                }
            }
        }
    };
    private Thread showing = new Thread(readWord);
    Runnable runnable2 = new Runnable() {
        @Override
        public synchronized void run() {
            while(showing.isAlive()||!customList2.isEmpty()) {
                try {
                    result.add(customList2.remove(customList2.size() - 1));
                } catch (ArrayIndexOutOfBoundsException e1) {

                }
            }

        }
    };
    public Quarantine() throws FileNotFoundException {
        for(char c='a';c<='z';c++){
            letters.add(c);
        }
        for(char c='A';c<='Z';c++){
            letters.add(c);
        }
        for(char c='0';c<='9';c++){
            letters.add(c);
        }
        letters.add(' ');
    }

    private Thread finalPart = new Thread(runnable2);
    public void writeResult() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("result1.txt");
        for(int i=0;i<result.size();i++) {
            fileOutputStream.write((result.get(i)+'\n').getBytes());
        }
    }
    public void getStatistics(String row){
        for(int i=0;i<row.length();i++){
            char ch = row.charAt(i);
            textLength++;
            if(ch>='a'&&ch<='z'){
                countOfLowerLetters++;
            }else if(ch>='A'&&ch<='Z'){
                countOfUpperLetters++;
            }else if(ch>='0'&&ch<='9'){
                countOfDigits++;
            }else{
                countOfOtherSymbols++;
            }
        }
    }
    public boolean allDone(Map<Character,Boolean> map){
        Set<Character> keys = map.keySet();
        for(Character key:keys){
            if (!map.get(key)){
                return false;
            }
        }

        return true;
    }
    public String deleteExcess(String originalString){
        String result = "";
        for(int i=0;i<originalString.length();i++){
            HashMap<Character,Boolean> mapLetters = new HashMap<>();
            for(Character ch:letters){
                mapLetters.put(ch,false);
            }
            char currentCh=originalString.charAt(i);
            while (!allDone(mapLetters)){
                Character nextChar = letters.get(rand.nextInt(letters.size()));


                if(nextChar.equals(currentCh)){
                    result+=currentCh;
                    mapLetters.replace(nextChar,true);
                    break;
                }else{
                    mapLetters.replace(nextChar,true);
                }
            }
        }
        return result;
    }
    public  void oneLoop() throws InterruptedException, IOException {
            writing.start();
            showing.start();
            finalPart.start();

            writing.join();
            showing.join();
            finalPart.join();
        writeResult();
        if(loopStatistics.containsKey(result.size())){
            int count = loopStatistics.get(result.size())+1;
            loopStatistics.put(result.size(),count);
        }else{
            loopStatistics.put(result.size(),1);
        }
        Thread.sleep(100);

    }
    public String convert() throws IOException, InterruptedException {

        this.oneLoop();
        Set<Integer> keys = loopStatistics.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<result.size();i++){
            stringBuilder.append(result.get(i));
            if(i<result.size()){
                stringBuilder.append("\n");
            }
        }
        String wtf = stringBuilder.toString();
        return stringBuilder.toString();

    }


}
