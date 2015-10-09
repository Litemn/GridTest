import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

/**
 * Created by litemn on 07.10.15.
 */
public class Generator {
    public static int randomInt(int min, int max, Random rand){

        return (rand.nextInt((max - min) + 1) + min);
    }

    public static String randomString(int digit,Random rand){
        int a = 97;
        int b = 122;
        char[] string = new char[digit];
        for(int i = 0; i<digit; ++i){
            string[i] = (char) randomInt(a,b,rand);
        }
        return new String(string);
    }

    public static void generate(int range, int lines, String name1, String name2){

        Random rand = new Random();



        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(name1)), "utf-8"))) {

            for(int i = 0; i<lines; i++){
                writer.write(String.format("%09d,%s\n",randomInt(0,range,rand),randomString(14,rand)));
            }

        } catch (Exception e){

        }
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(new File(name2)), "utf-8"))) {

            for(int i = 0; i<lines; i++){
                writer.write(String.format("%09d,%s\n",randomInt(0,range,rand),randomString(14,rand)));
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        if(args.length != 4){
            System.out.print("Enter 4 arguments(range and file names)");
            return;
        }
        int range = Integer.valueOf(args[0]);
        int lines = Integer.valueOf(args[1]);
        String name1 = args[2];
        String name2 = args[3];



        generate(range,lines,name1,name2);
}}
