import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TimerTask;
import java.util.zip.ZipOutputStream;

public class BackupTask extends TimerTask {
    private static final String BKP_DIR = "backup";
    private File dest;

    @Override
    public void run() {
        copyFiles("data");
        //packUp();
        //System.out.println("Timer test");
    }

    private void copyFiles(String source) {
        File src = new File(source);
        dest = new File(BKP_DIR + "/" + source);
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

    private void packUp() throws FileNotFoundException {
        File bkpData = new File(BKP_DIR);
        //File[] files = bkpData.listFiles();
        for(File f: bkpData.listFiles()) {
            if (f.isDirectory()) {
                //TODO: zip
                /*ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f.getName() + "_copy.zip"));
                File file = new File("folder");*/
            }
        }
    }
}
