import java.util.List;

/**
 * Represents an Employee and the days they are avalible.
 *
 * @author  Alli Jones
 */
public class Employee {
    private Integer id = 0;
    private String name;
    private boolean[] schedule = new boolean[7];

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isAvalible(Day day) {
        return schedule[day.getVal()];
    }
    public void setAvalability(Day day, boolean status) {
        schedule[day.getVal()] = status;
    }

    public boolean[] getSchedule() {
        return schedule;
    }

    public void setSchedule(boolean[] schedule) {
        this.schedule = schedule;
    }
    public void setSchedule(boolean sun, boolean mon, boolean tues, boolean wed, boolean thur, boolean fri, boolean sat) {
        this.schedule = new boolean[]{sun, mon, tues, wed, thur, fri, sat};
    }
}
