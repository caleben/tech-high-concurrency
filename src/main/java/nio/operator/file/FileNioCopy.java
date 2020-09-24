package nio.operator.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static tool.NioConfig.CAP;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class FileNioCopy extends AbsFileCopy {

    public FileNioCopy(String name) {
        super(name);
    }

    @Override
    public long copy(String src, String dest) throws IOException {

        Instant start = Instant.now();
        try (FileChannel inChannel = getChannel(src, true);
             FileChannel outChannel = getChannel(dest, false)) {
            int inLen = -1, outLen;
            ByteBuffer buf = ByteBuffer.allocateDirect(CAP);
            while ((inLen = inChannel.read(buf)) != -1) {
                //对buf来说目前刚由inChannel写进，buf是写状态，需要调用flip()切换到读状态
                buf.flip();

                outChannel.write(buf);

                //此时buf是读状态，需要调用clear()切换至写状态
                buf.clear();
            }
            //循环写完后，刷新下outChannel中的数据
            outChannel.force(true);

            return ChronoUnit.MILLIS.between(start, Instant.now());
        }
    }

}
