package cn.iotlearn.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious = false;
    private boolean showFirst = false;
    private boolean showNext = false;
    private boolean showEndPage = false;
    private Integer page;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPageination(Integer totalCount, String page, String size) {
        int totalPage = (int) Math.ceil((double)totalCount/Integer.parseInt(size));
        this.totalPage = totalPage;
        this.page = Integer.parseInt(page);
        pages.add(Integer.parseInt(page));
        for (int i = 1; i <= 3; i++) {
            if ((Integer.parseInt(page)-i) > 0){
                pages.add(0,Integer.parseInt(page)-i);
            }
            if ((Integer.parseInt(page) + i) <= totalPage){
                pages.add(Integer.parseInt(page) + i);
            }
        }
        showPrevious = Integer.parseInt(page) != 1;
        showNext = Integer.parseInt(page) != totalPage;
        showFirst = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
    }
}
