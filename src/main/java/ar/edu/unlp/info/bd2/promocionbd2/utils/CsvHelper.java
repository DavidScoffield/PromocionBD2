package ar.edu.unlp.info.bd2.promocionbd2.utils;

import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import ar.edu.unlp.info.bd2.promocionbd2.beans.PruebaBean;

import com.opencsv.CSVReader;

import ar.edu.unlp.info.bd2.promocionbd2.pojo.CsvTransfer;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;

// @SuppressWarnings("rawtypes", "unchecked")
public class CsvHelper {

    public static List<CsvBean> beanBuilderExample(Path path, Class<?> clazz) {
        CsvTransfer csvTransfer = new CsvTransfer();
        try {
            Reader reader = Files.newBufferedReader(path);
            CsvToBean cb = new CsvToBeanBuilder(reader).withType(clazz).build();

            csvTransfer.setCsvList(cb.parse());

            reader.close();

        } catch (Exception ex) {
            System.out.println("ERROR - " + ex);
        }

        return csvTransfer.getCsvList();
    }

    public static List<String[]> readAll(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        CSVReader csvReader = new CSVReader(reader);

        List<String[]> list = new ArrayList<>();

        list = csvReader.readAll();

        reader.close();
        csvReader.close();
        return list;
    }

}
