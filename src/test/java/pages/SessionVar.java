package pages;

/**
 * enum для хранения конфиг переменных
 */
public enum SessionVar {
    LOGIN("login"),
    PASS("pass");

    private final String value;

    SessionVar(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
