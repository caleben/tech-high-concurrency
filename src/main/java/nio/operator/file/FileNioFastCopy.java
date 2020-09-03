package nio.operator.file;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static nio.NioDemoConfig.CAP;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class FileNioFastCopy implements IFileCopy {
    private String name;

    public FileNioFastCopy(String name) {
        this.name = name;
    }

    @Override
    public void copy(String src, String dest) throws IOException {

        Instant start = Instant.now();
        try (FileChannel inChannel = getChannel(src, true);
             FileChannel outChannel = getChannel(dest, false)) {
            long size = inChannel.size();
            long pos = inChannel.position();
            long count;
            while (pos < size) {
                count = size - CAP > 0 ? CAP : size - pos;
                pos += outChannel.transferFrom(inChannel, pos, count);
            }
            outChannel.force(true);
            System.out.println(getIdentifyName() + " 复制文件完成耗时：" + ChronoUnit.MILLIS.between(start, Instant.now()));
        }
    }

    @Override
    public String getIdentifyName() {
        return name;
    }

}
