package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;

/**
 * Implementation of {@link MonthSorter}.
*/
public final class MonthSorterNested implements MonthSorter {

    private static final Comparator<String> BYDAYS = new SortByDays();
    private static final Comparator<String> BYMONTH = new SortByMonthOrder();

    @Override
    public Comparator<String> sortByDays() {
        return BYDAYS;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return BYMONTH;
    }

    private enum Month {
        JANUARY(31), FEBRUARY(28), MARCH(31), APRIL(30),
        MAY(31), JUNE(30), JULY(31), AUGUST(31),
        SEPTEMBER(30), OCTOBER(31), NOVEMBER(30), DECEMBER(31);

        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public static Month fromString(String value){
            final String normalizedValue = value.toLowerCase(Locale.UK);
            int matchCounter = 0;
            Month monthFound = null;
            for (final Month month : values()) {
                if (month.name().toLowerCase(Locale.UK).startsWith(normalizedValue)) {
                    monthFound = month;
                    matchCounter++;
                }
            }
            if (matchCounter == 0) {
                throw new IllegalArgumentException("Month not found with input " + value);
            }
            if (matchCounter > 1) {
                throw new IllegalArgumentException("Too many months fround with specific input " + value);
            }
            return monthFound;
        } 
    }

    private static final class SortByDays implements Comparator<String> {
        @Override
        public int compare(final String s1, final String s2) {
            final var m1 = Month.fromString(s1);
            final var m2 = Month.fromString(s2);
            return Integer.compare(m1.days, m2.days);
        }
    }

    private static final class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String s1, final String s2) {
            return Month.fromString(s1).compareTo(Month.fromString(s2));
        }
    }    
}