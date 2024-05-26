package com.groovus.www.repository;


import com.groovus.www.entity.Member;
import com.groovus.www.entity.Message;
import com.groovus.www.repository.dsl.QueryDslMessageRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message,Long> , QueryDslMessageRepository {

    //받은 쪽지 리스트 구하기
    @EntityGraph(attributePaths = {"receiver" , "sender" ,"project"})
    @Query("select m from Message m where m.receiver.mid =:receiverMid and m.del = false and m.project.pid=:pid")
    List<Message> getMessageListByMid(Long receiverMid , Long pid);

    //보낸 쪽지 리스트 구하기
    @EntityGraph(attributePaths = {"receiver","sender","project"})
    @Query("select m from Message m where m.sender.mid=:senderMid and m.del = false and m.project.pid=:pid")
    List<Message> getSendMessageByMid(Long senderMid , Long pid);

    //쪽지 번호로 읽기
    @EntityGraph(attributePaths = {"receiver","sender","project"})
    @Query("select m from Message m where m.lid=:lid")
    Optional<Message> getMessagesByLid(Long lid);

    //나의 쪽지함 쪽지 개수 구하기
    @EntityGraph(attributePaths = {"receiver","sender","project"})
    @Query("select count(m) from Message m where m.project.pid=:pid and m.receiver.mid=:mid and m.del=false")
    Long countMessageBy(Long pid , Long mid);
}
