package ar.edu.unlp.info.bd2.promocionbd2.utils;

import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import ar.edu.unlp.info.bd2.promocionbd2.pojo.CsvTransfer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

public class CsvHelper {

    public static List<CsvBean> beanBuilder(Path path, Class<?> clazz) throws IOException {
        CsvTransfer csvTransfer = new CsvTransfer();

        Reader reader = Files.newBufferedReader(path);
        CsvToBean cb = new CsvToBeanBuilder(reader)
                .withType(clazz)
                .withOrderedResults(false)
                .build();

        csvTransfer.setCsvList(cb.parse());

        reader.close();

        return csvTransfer.getCsvList();
    }

    /* TODO delete this */
   /* public static List<String[]> readAll(Path path) throws Exception {
        Reader reader = Files.newBufferedReader(path);
        CSVReader csvReader = new CSVReader(reader);

        List<String[]> list = new ArrayList<>();

        list = csvReader.readAll();

        reader.close();
        csvReader.close();
        return list;
    }*/

}
