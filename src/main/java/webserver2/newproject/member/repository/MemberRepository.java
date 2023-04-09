package webserver2.newproject.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver2.newproject.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
}
