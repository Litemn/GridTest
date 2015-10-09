import java.io.File;

/**
 * Created by litemn on 07.10.15.
 */
public class Main {
    public static void main(String[] args) {
        try{
            System.out.println("Generate files");
            Generator.generate(100,1000,"A.csv","B.csv");
            Generator.generate(1000000,10000000,"a.csv","b.csv");
            System.out.println("Complete generating");
            CSVUtils.join(new File("A.csv"),new File("B.csv"),"Result.csv");
            System.out.println("Complete join small tables");
            System.out.println("Starting join big tables...It's take a while");
            CSVUtils.joinBigTable(new File("a.csv"),new File("b.csv"),"result.csv");
            System.out.println("Complete!");

        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
