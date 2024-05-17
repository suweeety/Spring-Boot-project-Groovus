package com.groovus.www.repository.search;

import com.groovus.www.dto.DriveAllDTO;
import com.groovus.www.dto.DriveImageDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.groovus.www.entity.Drive;
import com.groovus.www.entity.QDrive;

import java.util.List;
import java.util.stream.Collectors;

public class DriveSearchImpl extends QuerydslRepositorySupport implements DriveSearch {

    public DriveSearchImpl() {
        super(Drive.class);
    }

    @Override
    public Page<Drive> search1(Pageable pageable) {

        QDrive drive = QDrive.drive;

        JPQLQuery<Drive> query = from(drive);

        query.where(drive.title.contains("1"));

        // 페이징
        this.getQuerydsl().applyPagination(pageable, query);

        List<Drive> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Drive> searchAll(String[] types, String keyword, Pageable pageable) {

        QDrive drive = QDrive.drive;
        JPQLQuery<Drive> query = from(drive);

        if ((types != null && types.length > 0) && keyword != null) { //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for (String type : types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(drive.title.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(drive.nickname.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno > 0
        query.where(drive.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Drive> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }

//    @Override // 635 변경 BoardListAllDTO
//    public Page<DriveAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable) {
//
//        QDrive drive = QDrive.drive;
//
//
//        JPQLQuery<Drive> driveJPQLQuery = from(drive);
//
//        if ((types != null && types.length > 0) && keyword != null) {  // 638 추가 (검색조건추가)
//
//            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (
//
//            for (String type : types) {
//
//                switch (type) {
//                    case "t":
//                        booleanBuilder.or(drive.title.contains(keyword));
//                        break;
//                    case "w":
//                        booleanBuilder.or(drive.nickname.contains(keyword));
//                        break;
//                }
//            }//end for
//            driveJPQLQuery.where(booleanBuilder);
//        }
//
//        driveJPQLQuery.groupBy(drive);
//
//        getQuerydsl().applyPagination(pageable, driveJPQLQuery); //paging
//
//        JPQLQuery<Tuple> tupleJPQLQuery = driveJPQLQuery.select(drive);
//
//        List<Tuple> tupleList = tupleJPQLQuery.fetch();
//
//        List<DriveAllDTO> dtoList = tupleList.stream().map(tuple -> {
//
//            Drive drive1 = (Drive) tuple.get(drive);
//
//            DriveAllDTO dto = DriveAllDTO.builder()
//                    .bno(drive1.getBno())
//                    .title(drive1.getTitle())
//                    .nickname(drive1.getNickname())
//                    .regdate(drive1.getRegDate())
//                    .build();
//
//            //BoardImage를 BoardImageDTO 처리할 부분 637 추가
//            List<DriveImageDTO> imageDTOS = drive1.getImageSet().stream().sorted()
//                    .map(boardImage -> DriveImageDTO.builder()
//                            .uuid(boardImage.getUuid())
//                            .fileName(boardImage.getFileName())
//                            .ord(boardImage.getOrd())
//                            .build()
//                    ).collect(Collectors.toList());
//
//            dto.setDriveImages(imageDTOS);
//
//            return dto;
//        }).collect(Collectors.toList());
//
//        long totalCount = driveJPQLQuery.fetchCount();
//
//
//        return new PageImpl<>(dtoList, pageable, totalCount);
//
//    }
}