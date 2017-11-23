package joiner;

public class JoinableString implements Joinable {
    String value;

    public static JoinableString of(String value) {
        JoinableString joinableString = new JoinableString();
        joinableString.value = value;
        return joinableString;
    }
    
    @Override
    public String getJoinKey() {
        return value;
    }
}
