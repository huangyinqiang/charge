package net.inconnection.charge.weixin.bean.resp;

import java.util.Arrays;
import java.util.List;

public class PageResponse {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private int totalCouts;
    private List<Object> list;
    private int nextPage;
    private int beforePage;
    private int[] pageBar;

    public PageResponse() {
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        this.totalPages = (this.totalCouts + this.pageSize - 1) / this.pageSize;
        return this.totalPages;
    }

    public int getTotalCouts() {
        return this.totalCouts;
    }

    public void setTotalCouts(int totalCouts) {
        this.totalCouts = totalCouts;
    }

    public List<Object> getList() {
        return this.list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public int getNextPage() {
        this.nextPage = this.pageNumber + 1;
        if (this.nextPage >= this.totalPages) {
            this.nextPage = this.totalPages;
        }

        return this.nextPage;
    }

    public int getBeforePage() {
        this.beforePage = this.pageNumber - 1;
        if (this.beforePage <= 0) {
            this.beforePage = 1;
        }

        return this.beforePage;
    }

    public int[] getPageBar() {
        int beginPage;
        int endPage;
        if (this.totalPages <= 10) {
            beginPage = 1;
            endPage = this.totalPages;
        } else {
            beginPage = this.pageNumber - 5;
            endPage = this.pageNumber + 4;
            if (beginPage <= 0) {
                beginPage = 1;
                endPage = beginPage + 9;
            }

            if (endPage > this.totalPages) {
                beginPage = this.totalPages - 9;
                endPage = this.totalPages;
            }
        }

        this.pageBar = new int[endPage - beginPage + 1];
        int index = 0;

        for(int i = beginPage; i <= endPage; this.pageBar[index++] = i++) {
            ;
        }

        return this.pageBar;
    }

    public String toString() {
        return "PageResponse [pageNumber=" + this.pageNumber + ", pageSize=" + this.pageSize + ", totalPages=" + this.totalPages + ", totalCouts=" + this.totalCouts + ", list=" + this.list + ", nextPage=" + this.nextPage + ", beforePage=" + this.beforePage + ", pageBar=" + Arrays.toString(this.pageBar) + "]";
    }
}
