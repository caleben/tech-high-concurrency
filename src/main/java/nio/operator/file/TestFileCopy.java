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

        List<AbsFileCopy> fileCopy = Arrays.asList(
                new FileBlockBufferCopy("block-buffer"),
                new FileNioTransferCopy("nio-transfer"),
                new FileNioMapCopy("nio-map"));

        for (AbsFileCopy fc : fileCopy) {
            long t = fc.copy(FILE_RESOURCE_SRC_PATH, FILE_RESOURCE_DEST_PATH);
            System.out.println(fc.getIdentifyName() + " 复制文件完成耗时：" + t);
        }
        /**
         * result:
         * file_size=917M  block.buf.size=10M  nio.channel.buf.size=500M
         * block-buffer    nio-transfer   nio-map
         * 905             3452           765
         * 821             2775           745
         * 898             2726           729
         * 873             2710           765
         * block.buf.size=1M
         * 754             2734           785
         * 740             2703           768
         * 746             2739           784
         * 对于nio.channel.buf.size大小从512Kb到500M速度无明显变化，而block.buf.size大小10M以下速度更快一点
         */

    }
}
