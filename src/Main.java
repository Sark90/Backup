import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File dir = new File("data");
        dir.mkdir();
        File file = new File(dir.getName() + "/test.dat");
        FileOutputStream fos = new FileOutputStream(file);
    }
}
