package com.banking.service;

import com.banking.model.CustomerDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public interface Bankservice {

     CustomerDetails saveDetails(String value1, String value2, String value3, long value4, long value5, String value6);

     List<CustomerDetails> getAccountNo(CustomerDetails userData);

     boolean hasXlsxFormat(MultipartFile file);

     List<CustomerDetails> excelToUser(InputStream is);

     ResponseEntity<byte[]> excelDownload(List<CustomerDetails> userDataList, HttpServletResponse response) throws IOException, InterruptedException;

     @Transactional
     List<CustomerDetails> amountIncremenet(double value1, double value2);

     @Transactional
     List<CustomerDetails> getMobile(long value);
}
