package com.pago.core.quotes.api.dto;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedList;
import com.pago.core.quotes.api.util.DozerMapperService;
import lombok.Data;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Page<T> {
    private List<T> contents;
    private int total;
    private int page_number;
    private int page_size;

    public Page(
            List<T> contents,
            int total,
            int page_number,
            int page_size
    ) {
        this.contents = contents;
        this.total = total;
        this.page_number = page_number;
        this.page_size = page_size;
    }

    public Page(
            Iterator<T> contentsIterator,
            int page_number,
            int page_size
    ) {
        this.page_number = page_number;
        this.page_size = page_size != 0 ? page_size: 10;
        this.buildContents(contentsIterator);
    }

    public Page(
            PaginatedList<T> contentsPaginatedList,
            int page_number,
            int page_size
    ) {
        this(contentsPaginatedList.iterator(), page_number, page_size);
    }

    private void buildContents(Iterator<T> contentsIterator) {
        int minResult = this.page_number * this.page_size;
        int maxResult = (page_number + 1) * this.page_size;
        contents = new ArrayList<T>(page_size);
        while (contentsIterator.hasNext()) {
            T currentElement = contentsIterator.next();
            if (total >= minResult && total < maxResult) {
                contents.add(currentElement);
            }
            total++;
        }
    }

    public <V> Page<V> map(DozerMapperService mapper, Class<V> clazz) {
        List<V> mappedContents = mapper.mapList(contents, clazz);
        return new Page<V>(mappedContents, total, page_number, page_size);
    }
}
