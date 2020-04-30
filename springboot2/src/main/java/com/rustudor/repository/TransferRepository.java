package com.rustudor.repository;


import com.rustudor.entity.Account;
import com.rustudor.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer,Integer> {

    List<Transfer> findAllByFrom(Account account);
    List<Transfer> findAllByTo(Account account);

}
