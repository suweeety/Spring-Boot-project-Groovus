package com.groovus.www.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ProjectPageResponseDTO<E> {


    private int page; //현재페이지
    private int size; //한페이지당 목록수
    private int total; //전체 데이터개수

    //시작페이지번호
    private int start; //시작번호(페이지마다 다름)
    //끝페이지번호
    private int end; //끝번호 (페이지마다 다름)

    //이전페이지 존재 여부
    private boolean prev;

    //다음페이지 존재여부
    private boolean next;

    private List<E> dtoList; //데이터리스트

   private int lastPage=1;

    //페이지 번호 목록
    private List<Integer> pageList;


    @Builder(builderMethodName = "withAll")
    public ProjectPageResponseDTO(ProjectPageRequestDTO pageRequestDTO,List<E> dtoList , int total){

        if(total<=0){
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.total=total; //전체 데이터 수
        this.dtoList = dtoList;

        int tempEnd= page * size; // 화면에서의 마지막번호(임시)

        this.start = tempEnd-9; //화면에서의 시작번호

        this.lastPage =(int)(Math.ceil((total/(double)size))); //데이터의 개수를 계산한 마지막 페이지 번호

        if(lastPage<=1){

            this.lastPage = 1;
        }

        this.end = this.total > tempEnd ? tempEnd : this.total;

        this.prev = this.start > 1;
        this.next = total > this.end;

    }



}
