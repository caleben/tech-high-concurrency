package nio.operator.file;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static tool.NioConfig.FILE_RESOURCE_DEST_PATH;
import static tool.NioConfig.FILE_RESOURCE_SRC_PATH;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public class TestFileCopy {
    public static void main(String[] args) throws IOException {

        List<IFileCopy> fileCopy = Arrays.asList(new FileBlockCopy("block-filestream"),
                new FileBlockBufferCopy("block-bufferedstream"),
                new FileNioCopy("nio"),
                new FileNioFastCopy("nio-transfer"));

        for (IFileCopy fc : fileCopy) {
            long t = fc.doCopy(FILE_RESOURCE_SRC_PATH, FILE_RESOURCE_DEST_PATH);
            System.out.println(fc.getIdentifyName() + " 复制文件完成耗时：" + t);
        }

    }
}
