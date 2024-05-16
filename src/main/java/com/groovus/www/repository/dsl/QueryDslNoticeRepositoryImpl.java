package com.groovus.www.repository.dsl;

import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.entity.Notice;
import com.groovus.www.entity.QNotice;
import com.groovus.www.entity.QProject;
import com.groovus.www.repository.MemberRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import groovy.util.logging.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
public class QueryDslNoticeRepositoryImpl extends QuerydslRepositorySupport implements QueryDslNoticeRepository {

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     *
     */
    public QueryDslNoticeRepositoryImpl() {
        super(Notice.class);
    }

    @Autowired
    MemberRepository memberRepository;

    @Override
    public Page<NoticeResponseDTO> searchAll(String[] types, String keyword, Pageable pageable, Long pid) {

        QNotice notice = QNotice.notice;
        QProject project = QProject.project;

        JPQLQuery<Notice> query = from(notice);
        query.where(notice.project.pid.eq(pid));

        //검색 조건과 키워드가 있다면
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(notice.title.contains(keyword).and(notice.del.isFalse()));
                        break;
                    case "c":
                        booleanBuilder.or(notice.content.contains(keyword).and(notice.del.isFalse()));
                        break;
                    case "w":
                        booleanBuilder.or(notice.member.uname.contains(keyword).and(notice.del.isFalse()));
                        break;
                    case "d":
                        booleanBuilder.or(notice.del.isTrue());
                }
            }
            query.where(booleanBuilder);
        } else {
            query.where(notice.del.isFalse());
        }

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice> noticeList = query.fetch();

        List<NoticeResponseDTO> noticeDTOList = noticeList.stream().map(tmpNotice -> {

            NoticeResponseDTO dto = NoticeResponseDTO.builder()
                    .nid(tmpNotice.getNid())
                    .pid(tmpNotice.getProject().getPid().toString())
                    .title(tmpNotice.getTitle())
                    .noticeWriter(tmpNotice.getNoticeWriter())
                    .content(tmpNotice.getContent())
                    .uname(tmpNotice.getMember().getUname())
                    .regDate(tmpNotice.getRegDate())
                    .modDate(tmpNotice.getModDate())
                    .build();
            return dto;
        }).collect(Collectors.toList());

        long count = query.fetchCount();

        return new PageImpl<NoticeResponseDTO>(noticeDTOList, pageable, count);
    }
}
