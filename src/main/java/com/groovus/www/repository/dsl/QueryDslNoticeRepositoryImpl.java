package com.groovus.www.repository.dsl;

import com.groovus.www.dto.ProjectPageResponseDTO;
import com.groovus.www.dto.notice.NoticeResponseDTO;
import com.groovus.www.entity.Notice;
import com.groovus.www.entity.QNotice;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import groovy.util.logging.Log4j2;
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

    @Override
    public Page<NoticeResponseDTO> searchAll(String[] types, String keyword, Pageable pageable) {

        QNotice notice = QNotice.notice;

        JPQLQuery<Notice> query = from(notice);

        //검색 조건과 키워드가 있다면
        if ((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(notice.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(notice.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(notice.member.uname.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice> noticeList = query.fetch();

        List<NoticeResponseDTO> noticeDTOList = noticeList.stream().map(tmpNotice -> {

            NoticeResponseDTO dto = NoticeResponseDTO.builder()
                    .nid(tmpNotice.getNid())
                    .title(tmpNotice.getTitle())
                    .content(tmpNotice.getContent())
                    .uname(tmpNotice.getMember().getUname())
                    .regDate(tmpNotice.getRegDate())
                    .modDate(tmpNotice.getModDate())
                    .build();
            return dto;
        }).toList();

        long count = query.fetchCount();

        return new PageImpl<NoticeResponseDTO>(noticeDTOList, pageable, count);
    }
}
