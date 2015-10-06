import java.io.File;

/**
 * Created by litemn on 07.10.15.
 */
public class Main {
    public static void main(String[] args) {
        try{
            CSVUtils.join(new File("A.csv"),new File("B.csv"),"Result.csv");
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
