import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.TimerTask;

public class BackupTask extends TimerTask {
    private static final String BKP_DIR = "backup/";
    private static final String SRC_DIR = "data";
    private File dest;

    @Override
    public void run() {
        copyFiles(SRC_DIR);
        packUp();
    }

    private void copyFiles(String source) {
        File src = new File(source);
        dest = new File(BKP_DIR + source);
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

    private void packUp() {
        new Packer().dirToZip(BKP_DIR + SRC_DIR);
    }
}
