package com.easyflight.flight.entity.query;

/**
 * Created by johnson on 7/10/17.
 */
public class ResultPage {
    private Integer PageNumber;
    private Integer PageSize;

    public ResultPage(Integer pageNumber, Integer pageSize) {
        PageNumber = pageNumber;
        PageSize = pageSize;
    }

    public Integer getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        PageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return PageSize;
    }

    public void setPageSize(Integer pageSize) {
        PageSize = pageSize;
    }
}
