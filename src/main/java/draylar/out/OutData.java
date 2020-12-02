package draylar.out;

import draylar.DateSnapshot;
import draylar.State;

import java.util.ArrayList;
import java.util.List;

public class OutData {

    private final State state;
    private final List<DateSnapshot> dates = new ArrayList<>();

    public OutData(State state) {
        this.state = state;
    }

    public void addDate(DateSnapshot snapshot) {
        dates.add(snapshot);
    }
}
