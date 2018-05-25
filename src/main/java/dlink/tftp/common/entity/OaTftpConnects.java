package dlink.tftp.common.entity;

import dlink.tftp.common.model.TftpConnects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "tftp_connects")
public class OaTftpConnects implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Long id;
	
	/**
	 * 连接ip
	 */
	private String connects_ip;

	/**
	 * 连接port
	 */
	private String connects_port;

	/**
	 * 目标路径
	 */
	private String remote_file;
	/**
	 * 本地路径
	 */
	private String local_file;

	/**
	 * block size
	 */
	private String block_size;

	/**
	 * 建立时间
	 */
	private Timestamp create_time;
	

	public OaTftpConnects() {
		super();
	}

	public OaTftpConnects(TftpConnects tftp) {
		this.connects_ip = tftp.getConnectsIp();
		this.connects_port = tftp.getConnectsPort();
		this.remote_file = tftp.getRemoteFile();
		this.local_file = tftp.getLocalFile();
		this.block_size = tftp.getBlockSize();
		this.create_time = new Timestamp(new Date().getTime());
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setConnects_ip(String connects_ip) {
		this.connects_ip = connects_ip;
	}
	@Column(name = "connects_ip", nullable = false, length = 100)
	public String getConnects_ip() {
		return connects_ip;
	}

	public void setConnects_port(String connects_port) {
		this.connects_port = connects_port;
	}
	@Column(name = "connects_port", nullable = false, length = 100)
	public String getConnects_port() {
		return connects_port;
	}

	public void setRemote_file(String remote_file) {
		this.remote_file = remote_file;
	}
	@Column(name = "remote_file", nullable = false, length = 100)
	public String getRemote_file() {
		return remote_file;
	}

	public void setLocal_file(String local_file) {
		this.local_file = local_file;
	}
	@Column(name = "local_file", nullable = false, length = 100)
	public String getLocal_file() {
		return local_file;
	}

	public void setBlock_size(String block_size) {
		this.block_size = block_size;
	}
	@Column(name = "block_size", nullable = false, length = 100)
	public String getBlock_size() {
		return block_size;
	}

	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	@Column(name = "create_time", nullable = false, length = 100)
	public Timestamp getCreate_time() {
		return create_time;
	}


	
}
