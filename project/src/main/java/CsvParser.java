import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvParser {
    public static List<String[]> parse(String fileName) throws IOException {
        List<String[]> csvList;
        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            csvList = reader.readAll();
        }
        return csvList;
    }
}

