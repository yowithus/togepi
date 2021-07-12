package com.example.togepi.repository;

import com.example.togepi.dto.SearchRequestDto;
import com.example.togepi.dto.SearchResponseDto;
import com.example.togepi.entity.BaseEntity;
import com.example.togepi.exception.ServerException;
import com.example.togepi.type.ErrorType;
import com.example.togepi.util.CursorUtil;
import com.example.togepi.util.MapperUtil;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
public abstract class BaseRepository<E extends BaseEntity> {

    private final String index;
    private final Class<E> c;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private MapperUtil mapperUtil;

    protected BaseRepository(String index, Class<E> c) {
        this.index = index;
        this.c = c;
    }

    public void upsert(E entity) {
        setEntityDefaultValue(entity);

        final IndexRequest request = new IndexRequest(index)
                .id(entity.getId().toString())
                .source(mapperUtil.toJson(entity), XContentType.JSON);

        if (Objects.nonNull(entity.getAdditional().getSeqNo())) {
            request.setIfSeqNo(entity.getAdditional().getSeqNo());
            request.setIfPrimaryTerm(entity.getAdditional().getPrimaryTerm());
        }

        try {
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new ServerException(ErrorType.ES_GENERAL_ERROR, e);
        }
    }

    public SearchResponseDto<E> search(SearchRequestDto<E> searchRequestDto) {
        final SearchRequest searchRequest = buildSearchRequest(searchRequestDto);

        try {
            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            final SearchHit[] hits = searchResponse.getHits().getHits();
            final List<E> result = new ArrayList<>();

            for (SearchHit hit : hits) {
                final E entity = mapperUtil.fromJson(hit.getSourceAsString(), c);

                final BaseEntity.Additional additional = new BaseEntity.Additional();
                additional.setPrimaryTerm(hit.getPrimaryTerm());
                additional.setSeqNo(hit.getSeqNo());

                result.add(entity);
            }

            final SearchResponseDto<E> responseDto = new SearchResponseDto<>();

            if (result.size() > searchRequestDto.getPageSize()) {
                result.remove(result.size() - 1);
                responseDto.setNextCursor(CursorUtil.encode(hits[result.size() - 1].getSortValues()));
            }

            responseDto.setResults(result);

            return responseDto;
        } catch (IOException e) {
            throw new ServerException(ErrorType.ES_GENERAL_ERROR, e);
        }
    }

    public Optional<E> findById(UUID id) {
        final GetRequest request = new GetRequest(index, id.toString());

        try {
            final GetResponse response = client.get(request, RequestOptions.DEFAULT);

            if (!response.isExists()) {
                return Optional.empty();
            }

            final E entity = mapperUtil.fromJson(response.getSourceAsString(), c);

            final BaseEntity.Additional additional = new BaseEntity.Additional();
            additional.setPrimaryTerm(response.getPrimaryTerm());
            additional.setSeqNo(response.getSeqNo());

            return Optional.of(entity);
        } catch (IOException e) {
            throw new ServerException(ErrorType.ES_GENERAL_ERROR, e);
        }
    }

    private SearchRequest buildSearchRequest(SearchRequestDto<E> searchRequestDto) {
        final SearchSourceBuilder sourceBuilder = searchRequestDto.getSourceBuilder();
        sourceBuilder.timeout(searchRequestDto.getTimeout());
        sourceBuilder.size(searchRequestDto.getPageSize() + 1);

        if (searchRequestDto.getNextCursor() != null) {
            sourceBuilder.searchAfter(searchRequestDto.getNextCursor());
        }

        final SearchRequest request = new SearchRequest(index);
        request.source(sourceBuilder);

        return request;
    }

    private void setEntityDefaultValue(E entity) {
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID());
        }
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(ZonedDateTime.now());
        }
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(ZonedDateTime.now());
        }
    }
}
