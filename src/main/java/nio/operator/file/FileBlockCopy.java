package nio.operator.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static tool.NioConfig.CAP;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class FileBlockCopy extends IFileCopy {

    public FileBlockCopy(String name) {
        super(name);
    }

    @Override
    public long copy(String src, String dest) throws IOException {

        Instant start = Instant.now();
        try (FileInputStream in = new FileInputStream(src);
             FileOutputStream out = new FileOutputStream(dest)) {
            byte[] buf = new byte[CAP];
            int byteRead;
            while ((byteRead = in.read(buf)) != -1) {
                out.write(buf, 0, byteRead);
            }
            out.flush();

            return ChronoUnit.MILLIS.between(start, Instant.now());
        }
    }

}
