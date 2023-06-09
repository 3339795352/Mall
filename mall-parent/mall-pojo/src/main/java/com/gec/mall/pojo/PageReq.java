package com.gec.mall.pojo;

public class PageReq {

    //    @NotNull(message = "页码不能为空")
    private int page;
    //    @NotNull(message = "每页条目数不能为空")
//    @Max(value = 1000,message = "每页条目数不能超过1000条")
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
