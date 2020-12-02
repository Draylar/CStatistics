package draylar;

import java.util.Date;

public class DateSnapshot {

    private final Date date;
    private final State state;

    private final int totalCases;
    private final int confirmedCases;
    private final int probableCases;
    private final int newCases;
    private final int probableNewCases;
    private final int totalDeaths;
    private final int confirmedDeaths;
    private final int probableDeaths;
    private final int newDeaths;
    private final int probableNewDeaths;

    private final String createdAt;
    private final Consent consentCases;
    private final Consent consentDeaths;

    private DateSnapshot(
            Date date,
            State state,
            int totalCases,
            int confirmedCases,
            int probableCases,
            int newCases,
            int probableNewCases,
            int totalDeaths,
            int confirmedDeaths,
            int probableDeaths,
            int newDeaths,
            int probableNewDeaths,
            String createdAt,
            Consent consentCases,
            Consent consentDeaths) {
        this.date = date;
        this.state = state;
        this.totalCases = totalCases;
        this.confirmedCases = confirmedCases;
        this.probableCases = probableCases;
        this.newCases = newCases;
        this.probableNewCases = probableNewCases;
        this.totalDeaths = totalDeaths;
        this.confirmedDeaths = confirmedDeaths;
        this.probableDeaths = probableDeaths;
        this.newDeaths = newDeaths;
        this.probableNewDeaths = probableNewDeaths;
        this.createdAt = createdAt;
        this.consentCases = consentCases;
        this.consentDeaths = consentDeaths;
    }

    public Date getDate() {
        return date;
    }

    public State getState() {
        return state;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public int getProbableCases() {
        return probableCases;
    }

    public int getNewCases() {
        return newCases;
    }

    public int getProbableNewCases() {
        return probableNewCases;
    }

    public int getTotalDeaths() {
        return totalDeaths;
    }

    public int getConfirmedDeaths() {
        return confirmedDeaths;
    }

    public int getProbableDeaths() {
        return probableDeaths;
    }

    public int getNewDeaths() {
        return newDeaths;
    }

    public int getProbableNewDeaths() {
        return probableNewDeaths;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Consent isConsentCases() {
        return consentCases;
    }

    public Consent isConsentDeaths() {
        return consentDeaths;
    }

    public static final class Builder {

        private final Date date;
        private final State state;

        private int totalCases;
        private int confirmedCases;
        private int probableCases;
        private int newCases;
        private int probableNewCases;
        private int totalDeaths;
        private int confirmedDeaths;
        private int probableDeaths;
        private int newDeaths;
        private int probableNewDeaths;

        private String createdAt;
        private Consent consentCases;
        private Consent consentDeaths;

        public Builder(Date date, State state) {
            this.date = date;
            this.state = state;
        }

        public Builder withTotalCases(int totalCases) {
            this.totalCases = totalCases;
            return this;
        }

        public Builder withConfirmedCases(int confirmedCases) {
            this.confirmedCases = confirmedCases;
            return this;
        }

        public Builder withProbableCases(int probableCases) {
            this.probableCases = probableCases;
            return this;
        }

        public Builder withNewCases(int newCases) {
            this.newCases = newCases;
            return this;
        }

        public Builder withProbableNewCases(int probableNewCases) {
            this.probableNewCases = probableNewCases;
            return this;
        }

        public Builder withTotalDeaths(int totalDeaths) {
            this.totalDeaths = totalDeaths;
            return this;
        }

        public Builder withConfirmedDeaths(int confirmedDeaths) {
            this.confirmedDeaths = confirmedDeaths;
            return this;
        }

        public Builder withProbableDeaths(int probableDeaths) {
            this.probableDeaths = probableDeaths;
            return this;
        }

        public Builder withNewDeaths(int newDeaths) {
            this.newDeaths = newDeaths;
            return this;
        }

        public Builder withProbableNewDeaths(int probableNewDeaths) {
            this.probableNewDeaths = probableNewDeaths;
            return this;
        }

        public Builder withCreatedAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder withConsentCases(Consent consentCases) {
            this.consentCases = consentCases;
            return this;
        }

        public Builder withConsentDeaths(Consent consentDeaths) {
            this.consentDeaths = consentDeaths;
            return this;
        }

        public DateSnapshot build() {
            return new DateSnapshot(
                    date,
                    state,
                    totalCases,
                    confirmedCases,
                    probableCases,
                    newCases,
                    probableNewCases,
                    totalDeaths,
                    confirmedDeaths,
                    probableDeaths,
                    newDeaths,
                    probableNewDeaths,
                    createdAt,
                    consentCases,
                    consentDeaths
            );
        }
    }
}
