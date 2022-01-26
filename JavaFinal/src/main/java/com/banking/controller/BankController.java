package com.banking.controller;


import com.banking.model.ResponseMessage;
import com.banking.model.CustomerDetails;
import com.banking.service.BankImpl;
import com.banking.service.Bankservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

//    @Autowired
//    Bankservice bankservice;

    @Autowired
    BankImpl  bankservice;

    @RequestMapping(value = "/addCustomer", method = RequestMethod.POST)
    public ResponseEntity<List<CustomerDetails>> saveDetails(@RequestParam String name, @RequestParam String dob, @RequestParam String accountType, @RequestParam long mobile) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        double bal=0.00;
        long accountno= (long) (Math.random()*100000000);
        System.out.println(accountno+"       ddfbjdhfdjbfjhdbfjbdjbdbdbjd");
        System.out.println(name+" "+dob+" "+accountType+" "+mobile+" "+ accountno+" "+ bal+" "+currentDateTime +"-------------------------------");
        List<CustomerDetails> customerDetails =bankservice.saveDetails(name, dob, accountType, mobile, accountno,   bal ,currentDateTime);
        System.out.println(customerDetails);
        return new ResponseEntity<>(customerDetails, HttpStatus.OK);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> uploadCyberFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                System.out.println(file.getOriginalFilename());
                if (bankservice.hasXlsxFormat(file)) {
                    try {
                        bankservice.excelToUser(file.getInputStream());
                        System.out.println("File is uploaded successfully------->");
                        message = "Uploaded the file successfully: " + file.getOriginalFilename();
                        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
                    } catch (Exception e) {
                        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                        System.out.println("Could not upload the file ------->");
                        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
                    }
                }
            }
        } catch (Exception e) {
            message = "not a file!";
            System.out.println("Invalid file! ------->");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }


    @RequestMapping(value = "/Download", method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<byte[]> excelDownload(@RequestParam long value, HttpServletResponse response) throws IOException, InterruptedException {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename= statement_"+currentDateTime+".xlsx";
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        System.out.println("Searching for Data------->");

        List<CustomerDetails> userDataList = bankservice.getMobile(value);
        return bankservice.excelDownload(userDataList, response);
    }

}
