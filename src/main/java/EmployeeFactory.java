public class EmployeeFactory {
    public static Employee create(String name, boolean[] schedule){
        Employee out = new Employee();
        out.setName(name);
        for(int i = 0; i < 7; i++){
            out.setAvalability(Day.fromIndex(i), schedule[i]);
        }
        return out;
    }
}