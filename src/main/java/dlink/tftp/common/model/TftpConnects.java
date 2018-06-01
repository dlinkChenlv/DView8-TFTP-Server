package dlink.tftp.common.model;

import java.sql.Timestamp;

/**
 * Created by Chenlv on 2018.5.25.
 */
public class TftpConnects {
    /**
     * 连接ip
     */
    private String connectsIp;

    /**
     * 连接port
     */
    private String connectsPort;

    /**
     * 目标路径
     */
    private String remoteFile;
    /**
     * 本地路径
     */
    private String localFile;

    /**
     * block size
     */
    private String blockSize;

    /**
     * 建立时间
     */
    private Timestamp createTime;

    public void setConnectsIp(String connectsIp) {
        this.connectsIp = connectsIp;
    }

    public void setConnectsPort(String connectsPort) {
        this.connectsPort = connectsPort;
    }

    public void setRemoteFile(String remoteFile) {
        this.remoteFile = remoteFile;
    }

    public void setLocalFile(String localFile) {
        this.localFile = localFile;
    }

    public void setBlockSize(String blockSize) {
        this.blockSize = blockSize;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getConnectsIp() {
        return connectsIp;
    }

    public String getConnectsPort() {
        return connectsPort;
    }

    public String getRemoteFile() {
        return remoteFile;
    }

    public String getLocalFile() {
        return localFile;
    }

    public String getBlockSize() {
        return blockSize;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
}
