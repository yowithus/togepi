package com.example.togepi.batch;

import com.example.togepi.model.CSVExpense;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.core.io.FileSystemResource;

import java.util.List;

public class CSVExpenseItemWriter implements ItemWriter<CSVExpense> {

    private static final String CSV_FILE_PATH = "./csv/output.csv";

    @Override
    public void write(final List<? extends CSVExpense> items) throws Exception {
        final FlatFileItemWriter<CSVExpense> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(CSV_FILE_PATH));
        writer.setAppendAllowed(true);
        writer.setLineAggregator(new DelimitedLineAggregator<CSVExpense>() {{
            setDelimiter(",");
            setFieldExtractor(new BeanWrapperFieldExtractor<CSVExpense>() {{
                setNames(new String[]{"name", "description", "amount"});
            }});
        }});
        writer.open(new ExecutionContext());
        writer.write(items);
        writer.close();
    }
}
