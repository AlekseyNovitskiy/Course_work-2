public enum TypeRepeat {
    ONE_TIME("однократная"), DAILY("ежедневная"), WEEKLY("еженедельная"),
    MONTHLY("ежемесячная"), ANNUAL("ежегодная");

    private String name;

    TypeRepeat(String name)
    {
        setName(name);
    }

    public String getName(){
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String[] getListRepeat()
    {
        String[] ListRepeat = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            ListRepeat[i] = values()[i].getName();
        }
        return ListRepeat;
    }
}
