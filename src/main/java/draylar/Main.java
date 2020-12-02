package draylar;

import com.sun.source.tree.Tree;
import draylar.out.CSVCreator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static final Path STAT_FILE_PATH = Paths.get("covid_stats.csv");
    public static Map<String, State> states = new HashMap<>();
    public static final Date NOVEMBER_14TH = new Date("11/14/2020");

    public static void main(String[] args) throws IOException {
        List<String> strings = Files.readAllLines(STAT_FILE_PATH);

        // First line is the header of the CSV file. All following lines contain data for a date for a state
        //    with information separated by a single comma (no space).
        // Some pieces of information have no content, which is shown by a double comma (,,), as the info is empty.
        for(int i = 1; i < strings.size(); i++) {
            DateSnapshot snapshot = parse(strings.get(i));
            snapshot.getState().addDate(snapshot.getDate(), snapshot);
        }

        TreeMap<String, State> sortedStates = new TreeMap<>();
        sortedStates.putAll(states);
        states = sortedStates;

        states.forEach((name, state) -> {
            System.out.printf("%s cases on %s: %d%n", name, NOVEMBER_14TH, states.get(name).getDataFor(NOVEMBER_14TH).getNewCases());
        });

        CSVCreator creator = new CSVCreator();
        creator.save(states);
    }

    public static DateSnapshot parse(String in) {
        // Numbers have commas in them, so we can't split by ','. Instead, we iterate over each char, and build a list of split data by hand.
        String currentData = "";
        String[] splitData = new String[15];
        boolean inQuotation = false;
        int index = 0;

        for(char c : in.toCharArray()) {
            // Toggle quotation status
            switch(c) {
                case '"':
                    inQuotation = !inQuotation;
                    break;
                case ',':
                    if(!inQuotation) {
                        splitData[index] = currentData;
                        currentData = "";
                        index++;
                    }
                    break;
                default:
                    currentData += c;
                    break;
            }
        }

        // final data doesn't end with ,
        splitData[index] = currentData;

        Date date = new Date(splitData[0]);
        String stateName = splitData[1];
        State state;

        // Get or create state
        if(states.containsKey(stateName)) {
            state = states.get(stateName);
        } else {
            state = new State(stateName);
            states.put(stateName, state);
        }

        // Create initial builder
        DateSnapshot.Builder builder = new DateSnapshot.Builder(date, state);

        // TODO: magic number key -> constant field?
        builder.withTotalCases(tryParse(splitData[2]));
        builder.withConfirmedCases(tryParse(splitData[3]));
        builder.withProbableCases(tryParse(splitData[4]));
        builder.withNewCases(tryParse(splitData[5]));
        builder.withProbableNewCases(tryParse(splitData[6]));
        builder.withTotalDeaths(tryParse(splitData[7]));
        builder.withConfirmedDeaths(tryParse(splitData[8]));
        builder.withProbableDeaths(tryParse(splitData[9]));
        builder.withNewDeaths(tryParse(splitData[10]));
        builder.withProbableNewDeaths(tryParse(splitData[11]));
        builder.withCreatedAt(splitData[12]);
        builder.withConsentCases(parseConsent(splitData[13]));
        builder.withConsentDeaths(parseConsent(splitData[14]));

        return builder.build();
    }

    /**
     * Attempts to parse the given String into an Integer.
     * If the string could not be parsed, -1 is returned.
     *
     * @param s  string to parse into an int
     * @return   the given string in integer form, or -1 if it could not be parsed
     */
    public static int tryParse(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException ignored) {

        }

        return -1;
    }

    public static Consent parseConsent(String s) {
        if(s == null || s.isEmpty()) {
            return Consent.NO_COMMENT;
        }

        switch(s.toLowerCase()) {
            case "agree":
                return Consent.AGREE;
            case "not agree":
                return Consent.DISAGREE;
        }
        return Consent.NO_COMMENT;
    }
}
