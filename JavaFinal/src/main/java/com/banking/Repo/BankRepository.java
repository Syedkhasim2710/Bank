package com.banking.Repo;


import com.banking.model.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BankRepository extends CrudRepository<CustomerDetails, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO customer_details (full_name, dob, acc_type, mobile, acc_no, balance, createdate) VALUES(?1, ?2, ?3, ?4, ?5, ?6, ?7)", nativeQuery = true)
    List<CustomerDetails> createAccount(String value1, String value2, String value3, long value4, long value5, double value6, String value7) ;

    @Query(value = "UPDATE customerDetails set bal=bal+?1 where acc_No=?2", nativeQuery = true)
    List<CustomerDetails> setBalance(double value1, double value2) ;

    @Query(value = "select acc_No from customerDetails", nativeQuery = true)
    List<CustomerDetails> getAccountNo();

    @Query(value = "select * from customerDetails where mobile=?1", nativeQuery = true)
    List<CustomerDetails> getData(long value);

}
