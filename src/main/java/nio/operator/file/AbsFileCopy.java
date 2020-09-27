package nio.operator.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author wuenci
 * @date 2020/9/2
 * @since 2.0.0
 */
public abstract class AbsFileCopy {

    private final String name;

    public AbsFileCopy(String name) {
        this.name = name;
    }

    /**
     * 通过open方式获取FileChannel
     * @param file file
     * @param in 是否是输入
     * @return FileChannel
     * @throws FileNotFoundException 异常
     */
    FileChannel getChannel(String file, boolean in) throws IOException {
        return in ? FileChannel.open(Path.of(file), StandardOpenOption.READ) :
                FileChannel.open(Path.of(file), StandardOpenOption.WRITE);

//        return in ? new FileInputStream(file).getChannel() :
//                new FileOutputStream(file).getChannel();
    }

    /**
     * copy file
     * @param src src file
     * @param dest dest file
     * @throws IOException exception
     */
    long copy(String src, String dest) throws IOException {
        String destFile = dest.replace("{type}", getIdentifyName());
        File d = new File(destFile);
        if (!d.exists()) {
            d.createNewFile();
        }
        return doCopy(src, destFile);
    }

    /**
     * 耗时封装
     * @param copyCommand 具体业务
     * @return 时间戳
     * @throws IOException IO异常
     */
    long timeCost(CopyCommand copyCommand) throws IOException {
        Instant start = Instant.now();
        copyCommand.process();
        return ChronoUnit.MILLIS.between(start, Instant.now());
    }


    /**
     * copy file and return time mills
     * @param src src file
     * @param dest destination file
     * @return long 时间戳
     * @throws IOException 异常
     */
    abstract long doCopy(String src, String dest) throws IOException;

    /**
     * retrieve identifyName
     * @return string
     */
    String getIdentifyName() {
        return name;
    }

    interface CopyCommand {
        /**
         * 执行命令
         * @throws IOException 异常
         */
        void process() throws IOException;
    }
}
