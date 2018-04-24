/**
 * The Day class is an enumerated data type that
 * represents the days of the week.
 *
 * @author Alli Jones and Aidan Edwards
 *
 */

public enum Day {
    SUNDAY(0), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

    private int dayVal;

    Day(int dayVal) {
        this.dayVal = dayVal;
    }

    public int getVal() {
        return dayVal;
    }

    public static Day fromIndex(int i){
        switch(i){
            case 0:
                return SUNDAY;
            case 1:
                return MONDAY;
            case 2:
                return TUESDAY;
            case 3:
                return WEDNESDAY;
            case 4:
                return THURSDAY;
            case 5:
                return FRIDAY;
            case 6:
                return SATURDAY;
        }
        return null;
    }
}
