package draylar.out;

import draylar.Main;
import draylar.State;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CSVCreator {

    public static final List<String> labels = new ArrayList<>();
    public static final Path output = Paths.get("output.csv");
    public static final List<String> dates = new ArrayList<>();

    static {
        labels.add("State");
        labels.add("Total Number of reported cases on November 14th");

        // November
        for(int i = 14; i <= 30; i++) {
            dates.add(String.format("11/%d/2020", i));
        }

        labels.addAll(dates);
    }

    public CSVCreator() {

    }

    public void save(Map<String, State> data) throws IOException {
        File file = output.toFile();

        // Delete file if it already exists
        if(file.exists()) {
            file.delete();
        }

        // Create file
        boolean newFile = file.createNewFile();
        List<String> toWrite = new ArrayList<>();

        // Write data
        if(newFile) {
            toWrite.add(String.join(",", labels));
            StringBuilder builder;

            for (Map.Entry<String, State> entry : data.entrySet()) {
                String name = entry.getKey();
                State state = entry.getValue();
                builder = new StringBuilder();

                // Ignore special states that don't have data (60 in total)
                // ????????? what is wrong with you oklahoma why do you have 0 numbers
                int newCases = state.getDataFor(Main.NOVEMBER_14TH).getNewCases();
//                if(newCases <= 0 && !name.equals("OK")) {
//                    continue;
//                }

                builder.append(name).append(",");
                // Append nov. 14th
                builder.append(newCases).append(",");

                // Append dates
                for (int i = 0; i < dates.size(); i++) {
                    Date date = new Date(dates.get(i));
                    builder.append(state.getDataFor(date).getNewCases());

                    if(i < dates.size()) {
                        builder.append(",");
                    }
                }

                toWrite.add(builder.toString());
            }
        }

        Files.write(output, toWrite);
    }
}
