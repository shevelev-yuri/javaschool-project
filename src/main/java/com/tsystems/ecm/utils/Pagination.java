package com.tsystems.ecm.utils;

import java.util.ArrayList;
import java.util.List;

public class Pagination {

    private List<Integer> pages = new ArrayList<>();

    private int currentPage;

    private int totalPages;

    public Pagination(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    public Pagination() {

    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
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

    public void calcNavPages(int pageNumber, int totalPages, int navLinks) {
        this.currentPage = pageNumber > totalPages ? totalPages : pageNumber;
        this.totalPages = totalPages;

        int begin = currentPage - navLinks / 2;
        int end = currentPage + navLinks / 2;

        //The first page
        pages.add(1);

        if (begin > 2) {
            // Used for "..."
            pages.add(-1);
        }
        for (int i = begin; i <= end; i++) {
            if (i > 1 && i < totalPages) {
                pages.add(i);
            }
        }

        if (end < totalPages - 1) {
            pages.add(-1);
        }

        // The last page.
        pages.add(totalPages);
    }
}