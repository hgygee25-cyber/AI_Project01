package login;
public final class GUI_10 {
    public final String id;
    public final String passwordData;
    public final String email;
    public final String userName;

    private GUI_10(String id, String passwordData, String email, String userName) {
        this.id = id;
        this.passwordData = passwordData;
        this.email = email;
        this.userName = userName;
    }

    public static GUI_10 parse(String line) {
        if (line == null) return null;
        int first = line.indexOf('/');
        int last = line.lastIndexOf('/');
        int previous = last > 0 ? line.lastIndexOf('/', last - 1) : -1;
        if (first <= 0 || previous <= first || last <= previous) return null;
        return new GUI_10(
                line.substring(0, first),
                line.substring(first + 1, previous),
                line.substring(previous + 1, last),
                line.substring(last + 1));
    }

    public String toLine() {
        return id + "/" + passwordData + "/" + email + "/" + userName;
    }
}
