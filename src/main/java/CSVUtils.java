import java.io.*;
import java.util.*;

/**
 * Created by litemn on 07.10.15.
 */
public class CSVUtils {

    public static void join(File file1, File file2, String resultName) throws IOException {

        HashMap<String, LinkedList<String>> hashMap = new HashMap<>();
        final String delimiter = ",";
        String id;
        String text;
        String line[];
        try (Scanner scanner = new Scanner(file1)) {
            while (scanner.hasNextLine()) {
                line = scanner.nextLine().split(delimiter);
                id = line[0];
                text = line[1];
                LinkedList<String> list = new LinkedList<>();
                list.add(text);
                hashMap.put(id, list);
            }
        }

        try (Scanner scanner = new Scanner(file2)) {

            while (scanner.hasNextLine()) {
                line = scanner.nextLine().split(delimiter);
                id = line[0];
                text = line[1];
                LinkedList<String> value = hashMap.get(id);
                if (value != null) {
                    value.add(text);
                }
            }
        }


        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(resultName), "utf-8"))) {
            for (Map.Entry<String, LinkedList<String>> val : hashMap.entrySet()) {
                if (val.getValue().size() >= 2) {
                    Iterator<String> iterator = val.getValue().iterator();
                    iterator.next();
                    while (iterator.hasNext()) {
                        writer.write(String.format("%s,%s,%s\n", val.getKey(), val.getValue().getFirst(), iterator.next()));
                    }

                }
            }


        }

    }

    //set position id in index file
    private static void position(RandomAccessFile raf, int i, long pos) throws IOException {
        raf.seek((i) * 10); // 10 byte in index line
        raf.write((String.format("%09d\n", pos).getBytes()));

    }
    // get position by id from index file
    private static int position(RandomAccessFile raf, int i) throws IOException {
        raf.seek((i) * 10);
        int id = Integer.valueOf(raf.readLine());
        return id;
    }

    public static void joinBigTable(File A, File B, String Name) throws IOException {
        final long MAX = 999999999; //bigger than max range
        final int LINES_N = 1000000;
        final int CONSOLE = 200000; // magic int for showing that app not dead
        final int BYTE_IN_LINE = 25; // byte in one line
        File temp = new File(Name);
        temp.createNewFile();

        File index = new File("index"); // index file for 'A' file
        index.createNewFile();
        RandomAccessFile raf = new RandomAccessFile(index, "rw");
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(index), "utf-8"))) {
            for (int i = 0; i <= LINES_N; i++) {
                writer.write(String.format("%d\n", MAX)); // create 'empty' index file
            }
        }

        System.out.println("Created index");


        RandomAccessFile raf1 = new RandomAccessFile(A, "r");
        String line;
        int repeat = 0;
        long pos = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(A))) {
            while ((line = br.readLine()) != null) {
                int id = Integer.valueOf(line.substring(0, 9));
                position(raf, id, pos); // set index value to index file
                pos += BYTE_IN_LINE; // inc position of id
                repeat++;
                if (repeat / CONSOLE == 1) {
                    System.out.println("...");
                    repeat = 0;
                }
            }
        }
        System.out.println("indexing complete");
        repeat = 0;
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(Name), "utf-8"));
             BufferedReader br = new BufferedReader(new FileReader(B))) {

            while (((line = br.readLine()) != null)) {

                int id = Integer.valueOf(line.substring(0, 9));
                if (position(raf, id) != MAX) { // if index file have link to id
                    raf1.seek(position(raf, id));
                    String res = raf1.readLine();
                    writer.write(String.format("%s,%s\n",res,line.substring(10)));
                    repeat++;
                    if (repeat / CONSOLE == 1) {
                        System.out.println("...");
                        repeat = 0;
                    }
                }


            }
        }
        System.out.println("Join done");
        index.delete();
        raf.close();
        raf1.close();
    }




}



