package draylar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class State {

    private final String name;
    private final Map<Date, DateSnapshot> dates = new HashMap<>();

    public State(String name) {
        this.name = name;
    }

    public void addDate(Date on, DateSnapshot data) {
        if(dates.containsKey(on)) {
            throw new IllegalArgumentException(String.format("Date %s has already been added to %s!", on.toString(), name));
        }

        dates.put(on, data);
    }

    public DateSnapshot getDataFor(Date date) {
        if(!dates.containsKey(date)) {
            throw new UnsupportedOperationException(String.format("%s requested for %s but was not found!", date, name));
        }

        return dates.get(date);
    }
}
