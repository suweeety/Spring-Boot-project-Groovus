package com.groovus.www.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.groovus.www.entity.Drive;
import com.groovus.www.entity.QDrive;

import java.util.List;

public class DriveSearchImpl extends QuerydslRepositorySupport implements DriveSearch {

    public DriveSearchImpl(){
        super(Drive.class);
    }
    @Override
    public Page<Drive> search1(Pageable pageable){

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

        if( (types != null && types.length > 0) && keyword != null ){ //검색 조건과 키워드가 있다면

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
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
}
