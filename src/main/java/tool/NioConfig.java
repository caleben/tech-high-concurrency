package tool;

/**
 * @author wuenci
 * @date 2020/9/3
 * @since 2.0.0
 */
public class NioConfig extends ConfigProperties {

    public static NioConfig singleton = new NioConfig("config.properties");

    private NioConfig(String fileName) {
        super(fileName);
        load();
    }

    public static int BUFFER_SIZE = singleton.getAndComputeNumWithStart("buffer.size");
    public static int CHANNEL_SIZE = singleton.getAndComputeNumWithStart("channel.size");
    public static String FILE_RESOURCE_SRC_PATH = singleton.getValue("file.resource.src.path");
    public static String FILE_RESOURCE_DEST_PATH = singleton.getValue("file.resource.dest.path");
}
