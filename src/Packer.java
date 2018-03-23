import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Packer {
    private static final int BUFFER_SIZE = 1024;
    private final List<String> files;
    private String srcDir;
    public Packer() {
        files = new ArrayList();
    }

    public void dirToZip(String sourceDir) {
        File f = new File(sourceDir);
        srcDir = f.getAbsolutePath();
        if (!f.isDirectory()) {
            System.out.println(sourceDir + " is not a directory");
            return;
        }
        generateFilesList(new File(srcDir));
        zipFiles(srcDir + "_" + getCurrDateTime() + ".zip");
    }

    private void generateFilesList(File file) {
        if (file.isFile()) {
            files.add(generateZipEntry(file.getAbsolutePath()));
        }
        if (file.isDirectory()) {
            //String dir = file.getAbsoluteFile().toString();
            String dir = file.getAbsolutePath();
            if(!dir.equalsIgnoreCase(srcDir)) {
                files.add(dir.substring(srcDir.length() + 1) + File.separator);
            }
            for (String nextFile : file.list()) {
                generateFilesList(new File(file, nextFile));
            }
        }
    }

    private void zipFiles(final String zipFile) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            final FileOutputStream fos = new FileOutputStream(zipFile);
            final ZipOutputStream zos = new ZipOutputStream(fos);
            System.out.println("Write to " + zipFile);
            for (final String nextFile : files) {
                //System.out.println("\t" + nextFile);
                ZipEntry ze = new ZipEntry(nextFile);
                zos.putNextEntry(ze);
                if ((nextFile.substring(nextFile.length() - 1))
                        .equals(File.separator)) {
                    continue;
                }
                try (FileInputStream fis = new FileInputStream(
                        srcDir + File.separator + nextFile)) {
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
            }
            zos.closeEntry();
            zos.close();
            System.out.println("\tAll files added");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generateZipEntry(final String filename) {
        return filename.substring(srcDir.length() + 1);
    }

    private String getCurrDateTime() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH) + "-"
            + c.get(Calendar.DAY_OF_MONTH) + "_" + c.get(Calendar.HOUR_OF_DAY) + "-"
            + c.get(Calendar.MINUTE) + "-" + c.get(Calendar.SECOND);
    }
}
