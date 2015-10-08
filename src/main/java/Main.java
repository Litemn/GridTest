import java.io.File;

/**
 * Created by litemn on 07.10.15.
 */
public class Main {
    public static void main(String[] args) {
        try{
            CSVUtils.largerFileJoid(new File("input_A.csv"),new File("input_B.csv"),"result.csv");
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
