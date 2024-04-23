package com.groovus.www.repository.search;

import com.groovus.www.entity.DriveBoard;
import com.groovus.www.entity.QDriveBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class DriveBoardSearchImpl extends QuerydslRepositorySupport implements DriveBoardSearch {

    public DriveBoardSearchImpl(){
        super(DriveBoard.class);
    }

    @Override
    public Page<DriveBoard> search1(Pageable pageable){

        QDriveBoard driveBoard = QDriveBoard.driveBoard;

        JPQLQuery<DriveBoard> query = from(driveBoard);

        query.where(driveBoard.title.contains("1"));

        List<DriveBoard> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<DriveBoard> searchAll(String[] types, String keyword, Pageable pageable) {

        QDriveBoard driveBoard = QDriveBoard.driveBoard;
        JPQLQuery<DriveBoard> query = from(driveBoard);

        if((types != null && types.length > 0) && keyword != null ) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(driveBoard.title.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(driveBoard.nickname.contains(keyword));
                        break;
                }
            } // end for
            query.where(booleanBuilder);
        } // end if

        // bno > 0
        query.where(driveBoard.bno.gt(0L));

        // paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<DriveBoard> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }
}
