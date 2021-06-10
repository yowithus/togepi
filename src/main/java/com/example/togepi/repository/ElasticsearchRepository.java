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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ElasticsearchRepository<E extends BaseEntity> {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private MapperUtil mapperUtil;

    public SearchResponseDto<E> search(SearchRequestDto<E> searchRequestDto) {
        final SearchRequest searchRequest = buildSearchRequest(searchRequestDto);

        try {
            final SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            final SearchHit[] hits = searchResponse.getHits().getHits();
            final List<E> result = new ArrayList<>();

            for (SearchHit hit : hits) {
                final E entity = mapperUtil.convertSnakeCaseJsonToObject(hit.getSourceAsString(),
                        searchRequestDto.getEntity());

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

    public Optional<E> findById(UUID id, SearchRequestDto<E> searchRequestDto) {
        final GetRequest request = new GetRequest(searchRequestDto.getIndex(), id.toString());
        try {
            final GetResponse response = client.get(request, RequestOptions.DEFAULT);

            if (!response.isExists()) {
                return Optional.empty();
            }

            final E entity = mapperUtil.convertSnakeCaseJsonToObject(response.getSourceAsString(),
                    searchRequestDto.getEntity());

            final BaseEntity.Additional additional = new BaseEntity.Additional();
            additional.setPrimaryTerm(response.getPrimaryTerm());
            additional.setSeqNo(response.getSeqNo());

            return Optional.of(entity);
        } catch (IOException e) {
            throw new ServerException(ErrorType.ES_GENERAL_ERROR, e);
        }
    }

    public SearchRequest buildSearchRequest(SearchRequestDto<E> searchRequestDto) {
        final SearchSourceBuilder sourceBuilder = searchRequestDto.getSourceBuilder();
        sourceBuilder.timeout(searchRequestDto.getTimeout());
        sourceBuilder.size(searchRequestDto.getPageSize() + 1);

        if (searchRequestDto.getNextCursor() != null) {
            sourceBuilder.searchAfter(searchRequestDto.getNextCursor());
        }

        final SearchRequest request = new SearchRequest(searchRequestDto.getIndex());
        request.source(sourceBuilder);

        return request;
    }
}
