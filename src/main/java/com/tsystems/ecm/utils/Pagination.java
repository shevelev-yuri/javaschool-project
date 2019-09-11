package com.tsystems.ecm.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for pagination.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Getter
@Setter
public class Pagination {

    /**
     * The list containing all page numbers.
     */
    private List<Integer> pages = new ArrayList<>();

    /**
     * The current page number.
     */
    private int currentPage;

    /**
     * The total number of pages.
     */
    private int totalPages;

    /**
     * Constructor with {@code currentPage} and {@code totalPages}.
     *
     * @param currentPage the current page number
     * @param totalPages  the total number of pages
     */
    public Pagination(int currentPage, int totalPages) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    /**
     * No args constructor.
     */
    public Pagination() {
    }

    /**
     * Calculates page navigation panel buttons.
     *
     * @param pageNumber the current page number
     * @param totalPages the total number of pages
     * @param navLinks   the desired size of panel in pages
     */
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