package com.example.togepi.service.impl;

import com.example.togepi.dao.OrderDao;
import com.example.togepi.entity.Order;
import com.example.togepi.exception.ResourceNotFoundException;
import com.example.togepi.mapper.MainMapper;
import com.example.togepi.dto.csv.OrderCsvDto;
import com.example.togepi.service.OpenCsvService;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultOpenCsvService implements OpenCsvService {

    private static final String CSV_FILE_PATH = "./csv/order.csv";

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MainMapper mapper;

    @Override
    public List<String[]> read() {
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
        } catch(NoSuchFileException e) {
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<String[]> write() {
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
        } catch(NoSuchFileException e) {
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> readToObject() {
        try {
            final Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
            final CsvToBean<OrderCsvDto> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(OrderCsvDto.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            final List<OrderCsvDto> orderCsvDtos = csvToBean.parse();
            final List<Order> orders = orderCsvDtos.stream()
                    .map(csvOrder -> mapper.map(csvOrder, Order.class))
                    .collect(Collectors.toList());

            reader.close();
            return orders;
        } catch(NoSuchFileException e) {
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Order> writeFromObject() {
        try {
            final Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH));
            final StatefulBeanToCsv<OrderCsvDto> beanToCsv = new StatefulBeanToCsvBuilder(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();
    
            final List<Order> orders = orderDao.findAll();
            final List<OrderCsvDto> orderCsvDtos = orders.stream()
                .map(order -> mapper.map(order, OrderCsvDto.class))
                .collect(Collectors.toList());
    
            beanToCsv.write(orderCsvDtos);
    
            writer.close();
            return orders;
        } catch(NoSuchFileException e) {
            throw new ResourceNotFoundException("File " + CSV_FILE_PATH + " not found");
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
