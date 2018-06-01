package dlink.tftp.common.repository;

import dlink.tftp.common.entity.OaTftpConnects;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Chenlv on 2018.5.25.
 */
public interface TftpConnectsRepository extends JpaRepository<OaTftpConnects, Integer> {
	
}
