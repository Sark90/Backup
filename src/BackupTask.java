import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TimerTask;

public class BackupTask extends TimerTask {
    private static final String BKP_DIR = "backup/";
    
    @Override
    public void run() {
        //copyFiles("data");
        //packUp();
        System.out.println("Timer test");
    }

    public static void copyFiles(String source) {
        File src = new File(source);
        File dest = new File(BKP_DIR + source);
        if(src.isDirectory()) {
            dest.mkdirs();
            File[] files = src.listFiles();
            for(File f: files) {
                copyFiles(f.getPath());
            }
        } else {
            try (FileOutputStream fos = new FileOutputStream(dest)) {
                byte[] bytes = Files.readAllBytes(Paths.get(src.getPath()));
                fos.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void packUp() {  // -> private
        File bkpData = new File(BKP_DIR);
        //File[] files = bkpData.listFiles();
        for(File f: bkpData.listFiles()) {
            if (f.isDirectory()) {
                //TODO: zip
            }
        }
    }
}
