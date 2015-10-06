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
}
