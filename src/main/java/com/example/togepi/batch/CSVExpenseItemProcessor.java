package com.example.togepi.batch;

import com.example.togepi.model.CSVExpense;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class CSVExpenseItemProcessor implements ItemProcessor<CSVExpense, CSVExpense> {

    @Override
    public CSVExpense process(final CSVExpense csvExpense) throws Exception {
        final CSVExpense csvExpense1 = new CSVExpense();
        csvExpense1.setName(csvExpense.getName());
        csvExpense1.setDescription(csvExpense.getDescription());
        csvExpense1.setAmount(csvExpense.getAmount().add(new BigDecimal(22)));

        return csvExpense1;
    }
}
