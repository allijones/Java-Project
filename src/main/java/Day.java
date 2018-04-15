public enum Day {
    Sunday(0), Monday(1), Tuesday(2), Wednesday(3), Thursday(4), Friday(5), Saturday(6);

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
                return Sunday;
            case 1:
                return Monday;
            case 2:
                return Tuesday;
            case 3:
                return Wednesday;
            case 4:
                return Thursday;
            case 5:
                return Friday;
            case 6:
                return Saturday;
        }
        return null;
    }
}
