package ar.edu.unlp.info.bd2.promocionbd2.services;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.info.bd2.promocionbd2.beans.AccidentBean;
import ar.edu.unlp.info.bd2.promocionbd2.beans.CsvBean;
import ar.edu.unlp.info.bd2.promocionbd2.utils.CsvHelper;

@Service
public class AccidentService {

  // @Autowired
  // private CsvHelper csvHelper;

  public String uploadCSV(String url) throws Exception {
    Path path = Paths.get(url);
    List<CsvBean> dataList = CsvHelper.beanBuilderExample(path, AccidentBean.class);

    // TODO: Save data in both databases

    return "EXITO";
  }

}
