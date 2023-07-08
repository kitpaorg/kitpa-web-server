package com.kitpa.kitpaserver.dto;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PagingResponse<T> extends Response<T>{
    private int currentPage = 0;
    private int totalPages = 0;
    private int numberOfElements = 0;
    private long totalElements = 0;
    private boolean isFirst = false;
    private boolean isLast = false;
    private boolean isEmpty = false;
    private long limit;
    private List<Integer> pageNumbers = new ArrayList<>();
    public PagingResponse(T data) {
        super(data);
    }

    public static <T>PagingResponse<List<T>> success(Page<T> data){
        PagingResponse<List<T>> res = new PagingResponse<>(data.getContent());
        res.currentPage = data.getNumber();
        res.numberOfElements = data.getNumberOfElements();
        res.totalElements = data.getTotalElements();
        res.totalPages = data.getTotalPages();
        res.limit = data.getSize();
        res.isFirst = data.isFirst();
        res.isLast = data.isLast();
        res.isEmpty = data.isEmpty();
        res.setBizCode(Response.GENERAL_SUCCESS);

        int totalPages = data.getTotalPages();
        if (totalPages > 0) {
            res.pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
        }

        return res;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public List<Integer> getPageNumbers() {
        return pageNumbers;
    }

    public void setPageNumbers(List<Integer> pageNumbers) {
        this.pageNumbers = pageNumbers;
    }
}
