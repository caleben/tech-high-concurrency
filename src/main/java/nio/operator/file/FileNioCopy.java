package nio.operator.file;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static tool.NioConfig.CHANNEL_SIZE;

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
    public long doCopy(String src, String dest) throws IOException {

        return timeCost(() -> {
            int cap = Math.min(CHANNEL_SIZE, Integer.MAX_VALUE - 1);
            try (FileChannel inChannel = getChannel(src, true);
                 FileChannel outChannel = getChannel(dest, false)) {
                ByteBuffer buf = ByteBuffer.allocateDirect(cap);
                while ((inChannel.read(buf)) != -1) {
                    //对buf来说目前刚由inChannel写进，buf是写状态，需要调用flip()切换到读状态
                    buf.flip();

                    outChannel.write(buf);

                    //此时buf是读状态，需要调用clear()切换至写状态
                    buf.clear();
                }
                //循环写完后，刷新下outChannel中的数据
                outChannel.force(true);
            }
        });

    }

}
