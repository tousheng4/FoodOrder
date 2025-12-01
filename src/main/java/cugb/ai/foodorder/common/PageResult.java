package cugb.ai.foodorder.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T> {

    private List<T> records;
    private Long total;
    private Integer page;
    private Integer size;

    public static <T> PageResult<T> of(List<T> records, Long total, Integer page, Integer size) {
        PageResult<T> pr = new PageResult<>();
        pr.setRecords(records);
        pr.setTotal(total);
        pr.setPage(page);
        pr.setSize(size);
        return pr;
    }
}
