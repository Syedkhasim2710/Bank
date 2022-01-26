package com.banking.service;

import com.banking.Repo.BankRepository;
import com.banking.model.CustomerDetails;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

@Service
public class BankImpl {

//    @Autowired
//    CustomerDetails customerDetails;

    @Autowired
    BankRepository bankRepository;
    private CustomerDetails customerDetails;

    public List<CustomerDetails> saveDetails(String value1, String value2, String value3, long value4, long value5, double value6, String value7) {
        System.out.println("user kdhdhhhhhhhhhhhhhhhhhhhhhhhhhh is saved");
        List<CustomerDetails> customerDetails=bankRepository.createAccount(value1, value2, value3, value4, value5, value6, value7);
        System.out.println(customerDetails);
       return customerDetails;

//        return CustomerDetails;

    }

    public List<CustomerDetails> getAccountNo(CustomerDetails userData){
        bankRepository.getAccountNo();
        return Collections.singletonList(userData);
    }

    public boolean hasXlsxFormat(MultipartFile file) {
        String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        String[] HEADERs = {"Name", "Account No", "Balance", "Mobile", "AccountType"};

        if (!TYPE.equals(file.getContentType())) {
            System.out.println("File is not Xlsx file------------------>");
            return false;
        }
        System.out.println(file.toString()+" is Xlsx file------------------>");
        return true;
    }

    public List<CustomerDetails> excelToUser(InputStream is)  {
        List<CustomerDetails> userDataList = new ArrayList<CustomerDetails>();
        try {
            HashMap<Integer, CustomerDetails> mp = new HashMap<Integer, CustomerDetails>();
            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(is);

            //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);

//            Iterator<Row> rows = sheet.iterator();
            Iterator<Row> rowIterator = sheet.iterator();


            //Iterate through each rows one by one
            int rowNumber = 0;
            while (rowIterator.hasNext()) {
                if (rowNumber == 0) {
                    rowNumber++;
                    rowIterator.next();
                    continue;
                }
                Row currentRow = rowIterator.next();
                Iterator<Cell> cellsInRow = currentRow.iterator();
                if (!cellsInRow.hasNext())
                    break;
                CustomerDetails userData = new CustomerDetails(cellsInRow);
                userDataList.toString();
                userDataList.add(userData);
            }
            workbook.close();
            System.out.println("Data is fetched from excel file------------------>");
            if (!userDataList.equals(getAccountNo(customerDetails))) {
                System.out.println("New Account Number found :)");
                return userDataList;
            } else {
                System.out.println("Account Number already exists :(");
                return userDataList=null;
            }
        }
        catch(IOException e){
            throw new RuntimeException("fail to parse Excel file: ");
        }
    }

    public ResponseEntity<byte[]> excelDownload(List<CustomerDetails> userDataList, HttpServletResponse response) throws IOException, InterruptedException {
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    String currentDateTime = dateFormatter.format(new Date());
        String fileName = "statement_"+currentDateTime ;
       System.out.println("Inside download ---------->    fileName:"+fileName);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(fileName);
        Row headerRow = sheet.createRow(0);

        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Name");

        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("DOB");

        headerCell = headerRow.createCell(2);
        headerCell.setCellValue("Account Type");

        headerCell = headerRow.createCell(3);
        headerCell.setCellValue("Mobile");

        headerCell = headerRow.createCell(4);
        headerCell.setCellValue("Account Number");

        headerCell = headerRow.createCell(5);
        headerCell.setCellValue("Balance Amount");


        System.out.println("Headers Created---------->");
        Row row;
//        System.out.println(cybersourceDetailsList.size());
        for (int i = 0; i < userDataList.size(); i++) {
            row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(userDataList.get(i).getName());
            row.createCell(1).setCellValue(userDataList.get(i).getDob());
            row.createCell(2).setCellValue(userDataList.get(i).getAccType());
            row.createCell(3).setCellValue(userDataList.get(i).getMobile());
            row.createCell(4).setCellValue(userDataList.get(i).getAccNo());
            row.createCell(5).setCellValue(userDataList.get(i).getBal());

        }

//        System.out.println(sheet.getRow(1).getCell(1).getStringCellValue() + "::::" + sheet.getRow(1).getCell(2).getStringCellValue());
        ServletOutputStream filOutputStream = response.getOutputStream();
        workbook.write(filOutputStream);
        File file = new File(fileName);
        file.createNewFile();
        byte[] content = FileUtil.readAsByteArray(file);
        workbook.close();
       System.out.println("Data Added to Excel File---------->");
        filOutputStream.close();
        System.out.println("File has been created Successfully------->");
        System.out.println("Download the file------->");
        return ResponseEntity.ok()
                .contentLength(content.length)
                .header(HttpHeaders.CONTENT_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .body(content);
    }


    @Transactional
    public List<CustomerDetails> amountIncremenet(double value1, double value2) {
        return bankRepository.setBalance(value1, value2);
    }

    @Transactional
    public List<CustomerDetails> getMobile(long value) {
        return bankRepository.getData(value);
    }
}
