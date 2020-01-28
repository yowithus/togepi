package com.example.togepi.batch;

import com.example.togepi.dto.csv.OrderCsvDto;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

public class OrderCsvItemReader extends FlatFileItemReader<OrderCsvDto> {

    private static final String CSV_INPUT_FILE_PATH = "./csvs/input.csvs";

    public OrderCsvItemReader() {
        this.setResource(new FileSystemResource(CSV_INPUT_FILE_PATH));
        this.setLineMapper(new DefaultLineMapper<OrderCsvDto>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name", "description", "amount" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<OrderCsvDto>() {{
                setTargetType(OrderCsvDto.class);
            }});
        }});
    }
}