package draylar;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static final Path STAT_FILE_PATH = Paths.get("C:\\Users\\samue\\Desktop\\covid_stats.csv");
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

        System.out.printf("MN cases on %s: %d%n", NOVEMBER_14TH, states.get("MN").getDate(NOVEMBER_14TH).getConfirmedCases());
    }

    public static DateSnapshot parse(String in) {
        String[] splitData = in.split(",");
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

        //  Apparently string.split doesn't include elements at the end of the list when the contents between them are empty, or something.
        if(splitData.length >= 14) {
            builder.withConsentCases(parseConsent(splitData[13]));

            if(splitData.length >= 15) {
                builder.withConsentDeaths(parseConsent(splitData[14]));
            }
        }

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
        switch(s.toLowerCase()) {
            case "agree":
                return Consent.AGREE;
            case "not agree":
                return Consent.DISAGREE;
        }
        return Consent.NO_COMMENT;
    }
}
