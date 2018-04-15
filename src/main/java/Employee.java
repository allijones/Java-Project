import java.util.List;

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
}
