package com.groovus.www.repository;

import com.groovus.www.entity.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.uid=:uid")
    Optional<Member> getWithRoles(String uid);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<Member> findByUid(String uid);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<Member> findByEmail(String email);

    @EntityGraph(attributePaths = {"roleSet"})
    Optional<Member> findByMid(Long mid);


    //비밀번호 변경
    //@Query는 주로 select 할때 사용하지만 @Modifying과 같이 사용하면 처리 가능
    @Transactional
    @Modifying
    @Query("update Member m set m.upw =:newPw where m.mid=:mid")
    int updatePassword(@Param("newPw")String newPw , @Param("mid") Long mid);


    @EntityGraph(attributePaths = {"roleSet"})
    @Query("select m from Member m where m.mid=:mid")
    Optional<Member> getMemberByMid(Long mid);

}
