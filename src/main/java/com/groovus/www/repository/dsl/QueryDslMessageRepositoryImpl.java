package com.groovus.www.repository.dsl;

import com.groovus.www.dto.MessageDTO;
import com.groovus.www.entity.Member;
import com.groovus.www.entity.Message;
import com.groovus.www.entity.QMember;
import com.groovus.www.entity.QMessage;
import com.groovus.www.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class QueryDslMessageRepositoryImpl extends QuerydslRepositorySupport implements QueryDslMessageRepository{
    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public QueryDslMessageRepositoryImpl() {super(Message.class);}

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Page<MessageDTO> searchAll(String[] types,String keyword, Pageable pageable, Long pid , Long mid) {

        QMessage message = QMessage.message;

        JPQLQuery<Message> query = from(message);
        query.where(message.project.pid.eq(pid));

        if(types != null && types.length>0 && keyword != null){
            //검색 조건 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types){

                switch (type){
                    case "r" : //받는이
                        booleanBuilder.or(message.receiver.mid.eq(Long.parseLong(keyword)).and(message.del.isFalse()));
                        break;
                    case "s": //보낸이
                        booleanBuilder.or(message.sender.mid.eq(Long.parseLong(keyword)).and(message.del.isFalse()));
                        break;
                    case "d": //삭제된
                        booleanBuilder.or(message.sender.mid.eq(Long.parseLong(keyword)).or(message.receiver.mid.eq(Long.parseLong(keyword))).and(message.del.isTrue()));
                }

            }
            query.where(booleanBuilder);
        }else{
            query.where(message.del.isFalse());
            query.where(message.receiver.mid.eq(mid));
        }
        this.getQuerydsl().applyPagination(pageable,query);
        List<Message> messageList = query.fetch();

        List<MessageDTO> messageDTOList = messageList.stream().map(message1 ->{

            Optional<Member> receiverResult = memberRepository.findByMid(message1.getReceiver().getMid());
            Optional<Member> senderResult = memberRepository.findByMid(message1.getSender().getMid());

            Member receiver = receiverResult.get();
            Member sender = senderResult.get();



            MessageDTO dto = MessageDTO.builder()
                    .lid(message1.getLid().toString())
                    .receiver(receiver.getUid())
                    .sender(sender.getUid())
                    .content(message1.getContent())
                    .title(message1.getTitle())
                    .messageStatus(message1.getMessageStatus())
                    .sendDate(message1.getSendDate())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        long totalCount = query.fetchCount();

        return new PageImpl<MessageDTO>(messageDTOList,pageable,totalCount);
    }
}
