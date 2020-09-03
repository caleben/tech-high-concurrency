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
public interface IFileCopy {

    /**
     * 通过文件流获取FileChannel
     * @param file file
     * @param in 是否是输入
     * @return FileChannel
     * @throws FileNotFoundException 异常
     */
    default FileChannel getChannel(String file, boolean in) throws FileNotFoundException {

        return in ? new FileInputStream(file).getChannel() :
                new FileOutputStream(file).getChannel();
    }

    /**
     * copy file
     * @param src src file
     * @param dest dest file
     * @throws IOException exception
     */
    default void doCopy(String src, String dest) throws IOException {
        String destFile = dest.replace("{type}", getIdentifyName());
        File d = new File(destFile);
        if (!d.exists()) {
            d.createNewFile();
        }
        copy(src, destFile);
    }

    /**
     * copy file and return time mills
     * @param src src file
     * @param dest destination file
     * @throws IOException 异常
     */
    void copy(String src, String dest) throws IOException;

    /**
     * retrieve identifyName
     * @return string
     */
    String getIdentifyName();
}
