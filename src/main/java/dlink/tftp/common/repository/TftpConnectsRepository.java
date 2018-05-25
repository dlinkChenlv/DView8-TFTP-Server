package dlink.tftp.common.repository;


import dlink.tftp.common.entity.OaTftpConnects;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * 邮件管理
 * 创建者 小柒2012
 * 创建时间	2017年9月9日
 */

/**
 * JPA是Java Persistence API的简称，中文名Java持久层API，是JDK 5.0注解或XML描述对象－关系表的映射关系，并将运行期的实体对象持久化到数据库中。
 *
 * JpaRepository 是继承自 PagingAndSortingRepository
 * 的针对 JPA 技术提供的接口，它在父接口的基础上，提供
 * 了其他一些方法，比如 flush()，saveAndFlush()，deleteInBatch()
 * 等。如果有这样的需求，则可以继承该接口。
 *Spring Data JPA
 * 可以点开JpaRepository 会发现里面有findAll等方法
 *这样写代码非常的简介，一行代码就实现了增删改查
 */

public interface TftpConnectsRepository extends JpaRepository<OaTftpConnects, Integer> {
	
}
