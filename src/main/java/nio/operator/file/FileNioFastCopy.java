package nio.operator.file;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static tool.NioConfig.CAP;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class FileNioFastCopy extends AbsFileCopy {

    public FileNioFastCopy(String name) {
        super(name);
    }

    @Override
    public long copy(String src, String dest) throws IOException {

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

            return ChronoUnit.MILLIS.between(start, Instant.now());
        }
    }

}
