package ru.mirea;

import ru.mirea.common.CryptoUtil;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Admin on 20.09.2016.
 */
public class Database implements Serializable {
    private File tableFile = new File("table");
    private static HashMap<String, dbCell> table = new HashMap<>();
    Database() throws IOException, ClassNotFoundException {
        input();
    }
    public void output() throws IOException {
        FileOutputStream outputStream= new FileOutputStream(tableFile);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(table);

    }
    public void input() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream=new FileInputStream(tableFile);
        if(tableFile.length()!=0) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Object obj = objectInputStream.readObject();
                this.table= (HashMap<String, dbCell>) obj;

            } catch (EOFException e) {
                fileInputStream.close();
            }
        }

    }
    public String clear() {
        table.clear();
        tableFile.delete();
        return "Database was cleared";
    }
    @Override
    public String toString(){
        String string="";
        Set<String> keys = table.keySet();
        for (String key:keys
             ) {
            string+="login: "+key+table.get(key)+"\n";
        }
        return string;
    }
    @Override
    protected void finalize() throws IOException {
        output();
    }
    public String registration(String login, String password) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        if (!table.containsKey(login)){
            table.put(login,new dbCell(password));
            output();
            return "New user added";
        }else return "Login already exists";
    }
    public static boolean loginExists(String login){
        return table.containsKey(login);
    }
    public String verification (String login, String passsword) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        if(this.table.containsKey(login)){
            dbCell db = this.table.get(login);
            if(db.correctPassword(passsword)){
                return "Correct password";
            }else return "Incorrect password";
        }else return "Incorrect login!";

    }
}
