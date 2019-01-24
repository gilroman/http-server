package gil.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


public class FileIO implements IOProvider{
    private final File file;

    public FileIO(String filePath) {
        this.file = new File(filePath);
    }

    public void initFile() throws IOException {
        if(this.file.exists()) {
            this.file.delete();
        }

        this.file.createNewFile();
    }

    public InputStream getInputStream() throws IOException {
        if(!this.file.exists()) {
            this.file.createNewFile();
        }

        return new FileInputStream(this.file);
    }

    public OutputStream getOutputStream() throws IOException {
        if(!this.file.exists()) {
            this.file.createNewFile();
        }

        return new FileOutputStream(this.file);
    }
}
