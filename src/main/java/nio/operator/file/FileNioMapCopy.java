package nio.operator.file;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import static tool.NioConfig.CHANNEL_SIZE;

/**
 * 内存映射
 * @author wuenci
 * @date 2020/9/25
 * @since 2.0.0
 */
public class FileNioMapCopy extends AbsFileCopy {

    public FileNioMapCopy(String name) {
        super(name);
    }

    @Override
    long doCopy(String src, String dest) throws IOException {

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
                    MappedByteBuffer map = inChannel.map(FileChannel.MapMode.READ_ONLY, pos, count);
                    outChannel.write(map);
                    pos += count;
                }
            }
        });

    }

}
