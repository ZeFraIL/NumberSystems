package zeev.fraiman.numbersystems;

public class ResultItem {
    private final String systemName;
    private final String value;

    public ResultItem(String systemName, String value) {
        this.systemName = systemName;
        this.value = value;
    }

    public String getSystemName() {
        return systemName;
    }

    public String getValue() {
        return value;
    }
}
