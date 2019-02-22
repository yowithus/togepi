package com.example.togepi.service.impl;

import com.example.togepi.model.CSVExpense;
import com.example.togepi.model.Expense;
import com.example.togepi.service.OpenCSVService;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.repository.ExpenseRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpenCSVServiceImpl implements OpenCSVService {

    private static final String CSV_FILE_PATH = "./csv/expense.csv";

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public List<String[]> read() throws ResourceNotFoundException {
        try {
            final Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            final CSVReader csvReader = new CSVReader(reader);
            final List<String[]> list = new ArrayList<>();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                list.add(line);
            }

            // read all lines at once
            // final List<String[]> list = csvReader.readAll();

            reader.close();
            csvReader.close();
            return list;
        } catch(Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        }
    }

    @Override
    public List<Expense> readToObject() throws ResourceNotFoundException {
        try {
            final Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            final CsvToBean<CSVExpense> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(CSVExpense.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            final List<CSVExpense> csvExpenses = csvToBean.parse();
            final List<Expense> expenses = csvExpenses.stream().map(csvExpense -> mapCSVExpenseToExpense(csvExpense)).collect(Collectors.toList());

            reader.close();
            return expenses;
        } catch(Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        }
    }

    @Override
    public List<String[]> write() throws ResourceNotFoundException {
        try {
            final Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));
            final CSVWriter csvWriter = new CSVWriter(writer);

            final List<String[]> list = new ArrayList<>();
            list.add(new String[]{"Name", "Email", "Phone", "Country"});
            list.add(new String[]{"Yonatan", "yonatan@hotmail.com", "081932058111", "Indonesia"});
            list.add(new String[]{"Yesika", "yesika@hotmail.com", "081932058222", "Indonesia"});

            list.forEach((String[] line) -> {
                csvWriter.writeNext(line);
            });

            // write all lines at once
            // csvWriter.writeAll(list);

            writer.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        }
    }

    @Override
    public List<Expense> writeFromObject() throws ResourceNotFoundException {
        try {
            final Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));
            final StatefulBeanToCsv<CSVExpense> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            final List<Expense> expenses = expenseRepository.findAll();
            final List<CSVExpense> csvExpenses = expenses.stream().map(expense -> mapExpenseToCSVExpense(expense)).collect(Collectors.toList());

            beanToCsv.write(csvExpenses);

            writer.close();
            return expenses;
        } catch(Exception e) {
            e.printStackTrace();
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        }
    }

    private CSVExpense mapExpenseToCSVExpense(Expense expense) {
        final CSVExpense csvExpense = new CSVExpense();
        csvExpense.setName(expense.getName());
        csvExpense.setDescription(expense.getDescription());
        csvExpense.setAmount(expense.getAmount());

        return csvExpense;
    }

    private Expense mapCSVExpenseToExpense(CSVExpense csvExpense) {
        final Expense expense = new Expense();
        expense.setName(csvExpense.getName());
        expense.setDescription(csvExpense.getDescription());
        expense.setAmount(csvExpense.getAmount());

        return expense;
    }
}
