public enum TypeTask {
    PERSONAL("личная"), WORKING("рабочая");

    private String name;

    TypeTask(String name)
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

    public String[] getListType()
    {
        String[] ListRepeat = new String[values().length];
        for (int i = 0; i < values().length; i++) {
            ListRepeat[i] = values()[i].getName();
        }
        return ListRepeat;
    }
}
