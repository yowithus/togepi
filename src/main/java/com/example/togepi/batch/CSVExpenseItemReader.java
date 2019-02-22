package com.example.togepi.batch;

import com.example.togepi.model.CSVExpense;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

public class CSVExpenseItemReader extends FlatFileItemReader<CSVExpense> {

    private static final String CSV_INPUT_FILE_PATH = "./csv/input.csv";

    public CSVExpenseItemReader () {
        this.setResource(new FileSystemResource(CSV_INPUT_FILE_PATH));
        this.setLineMapper(new DefaultLineMapper<CSVExpense>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "name", "description", "amount" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<CSVExpense>() {{
                setTargetType(CSVExpense.class);
            }});
        }});
    }
}