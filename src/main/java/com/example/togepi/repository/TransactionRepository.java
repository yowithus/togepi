package com.example.togepi.repository;

import com.example.togepi.constant.ElasticsearchConstant;
import com.example.togepi.constant.OrderIndexConstant;
import com.example.togepi.dto.PaginationDto;
import com.example.togepi.dto.SearchRequestDto;
import com.example.togepi.dto.SearchResponseDto;
import com.example.togepi.dto.SearchTransactionRequestDto;
import com.example.togepi.entity.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository extends BaseRepository<Transaction> {

    public TransactionRepository() {
        super(ElasticsearchConstant.TRANSACTION_INDEX, Transaction.class);
    }

    public SearchResponseDto<Transaction> search(PaginationDto paginationDto, SearchTransactionRequestDto requestDto) {
        final BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder()
                .must(new TermQueryBuilder(OrderIndexConstant.USER_ID, requestDto.getUserId()));

        if (StringUtils.isNotEmpty(requestDto.getReceiptNo())) {
            boolQueryBuilder.must(new MatchQueryBuilder(OrderIndexConstant.RECEIPT_NO_NGRAM, requestDto.getReceiptNo()));
        }

        final SearchRequestDto searchRequestDto = new SearchRequestDto();
        searchRequestDto.setPagination(paginationDto);
        searchRequestDto.getSourceBuilder()
                .sort(new FieldSortBuilder(OrderIndexConstant.CREATED_AT).order(SortOrder.DESC))
                .query(boolQueryBuilder);

        return search(searchRequestDto);
    }
}
