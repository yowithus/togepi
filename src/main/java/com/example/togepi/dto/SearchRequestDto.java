package com.example.togepi.dto;

import com.example.togepi.entity.BaseEntity;
import com.example.togepi.util.CursorUtil;
import lombok.Data;
import lombok.Setter;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.TimeUnit;

@Data
public class SearchRequestDto<E extends BaseEntity> {

    private static final int DEFAULT_TIMEOUT_SECONDS = 5;
    private static final int DEFAULT_PAGE_SIZE = 10;

    private SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
    private TimeValue timeout = new TimeValue(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);

    @Setter
    private PaginationDto pagination = new PaginationDto("", DEFAULT_PAGE_SIZE);

    public Object[] getNextCursor() {
        if (ObjectUtils.isEmpty(pagination.getNextCursor())) {
            return null;
        }

        return CursorUtil.decode(pagination.getNextCursor());
    }

    public Integer getPageSize() {
        return pagination.getPageSize();
    }
}
