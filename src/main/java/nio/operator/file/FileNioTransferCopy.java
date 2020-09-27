package nio.operator.file;

import java.io.IOException;
import java.nio.channels.FileChannel;

import static tool.NioConfig.CHANNEL_SIZE;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class FileNioTransferCopy extends AbsFileCopy {

    public FileNioTransferCopy(String name) {
        super(name);
    }

    @Override
    public long doCopy(String src, String dest) throws IOException {
        return timeCost(() -> {
            int cap = Math.min(CHANNEL_SIZE, Integer.MAX_VALUE - 1);
            try (FileChannel inChannel = getChannel(src, true);
                 FileChannel outChannel = getChannel(dest, false)) {
                long size = inChannel.size();
                long pos = inChannel.position();
                long count;
                while (pos < size) {
                    count = (size - pos) - cap > 0 ? cap : size - pos;
                    // l 与 count 是相等的
                    long l = outChannel.transferFrom(inChannel, pos, count);
                    pos += count;
                }
                outChannel.force(true);
            }
        });
    }

}
