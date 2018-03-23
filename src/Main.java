import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new BackupTask(),1000, 20000);
        try {
            Thread.sleep(75000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.cancel();
    }
}
