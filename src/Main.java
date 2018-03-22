import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        new Timer().schedule(new BackupTask(),3000, 5000);
    }
}
