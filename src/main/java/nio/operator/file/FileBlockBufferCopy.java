package nio.operator.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
public class FileBlockBufferCopy extends AbsFileCopy {

    public FileBlockBufferCopy(String name) {
        super(name);
    }

    @Override
    public long copy(String src, String dest) throws IOException {

        Instant start = Instant.now();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] bytes = new byte[CAP];
            int len;
            while ((len = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            bos.flush();

            return ChronoUnit.MILLIS.between(start, Instant.now());
        }
    }

}
