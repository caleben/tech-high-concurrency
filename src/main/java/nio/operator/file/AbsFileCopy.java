package nio.operator.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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
     * 通过文件流获取FileChannel
     * @param file file
     * @param in 是否是输入
     * @return FileChannel
     * @throws FileNotFoundException 异常
     */
    FileChannel getChannel(String file, boolean in) throws FileNotFoundException {

        return in ? new FileInputStream(file).getChannel() :
                new FileOutputStream(file).getChannel();
    }

    /**
     * copy file
     * @param src src file
     * @param dest dest file
     * @throws IOException exception
     */
    long doCopy(String src, String dest) throws IOException {
        String destFile = dest.replace("{type}", getIdentifyName());
        File d = new File(destFile);
        if (!d.exists()) {
            d.createNewFile();
        }
        return copy(src, destFile);
    }

    /**
     * copy file and return time mills
     * @param src src file
     * @param dest destination file
     * @throws IOException 异常
     */
    long copy(String src, String dest) throws IOException {
        return 0;
    }

    /**
     * retrieve identifyName
     * @return string
     */
    String getIdentifyName() {
        return name;
    }
}
