package com.rustudor.Services;

import com.rustudor.Dto.*;
import com.rustudor.Util.Session;
import com.rustudor.Util.SessionManager;
import com.rustudor.entity.*;
import com.rustudor.repository.AccountRepository;
import com.rustudor.repository.LoginRepository;
import com.rustudor.repository.TransferRepository;
import com.rustudor.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    LoginRepository loginRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransferRepository transferRepository;


    @Transactional
    public int register(FullUserDto fullUserDto) {
        User user = new User();
        Login login = new Login();

        Login login2 = loginRepository.findByUsername(fullUserDto.getUsername());
        if (login2 != null)
            return -1;//duplicate username
        user.setName(fullUserDto.getName());
        user.setPhoneNumber(fullUserDto.getPhoneNumber());
        user.setEmail(fullUserDto.getEmail());
        user.setAddress(fullUserDto.getAddress());
        user.setCnp(fullUserDto.getCnp());
        login.setRole(Role.USER);
        login.setUsername(fullUserDto.getUsername());
        login.setUserFK(user);
        login.setPassword(fullUserDto.getPassword());
        user.setLogin(login);

        loginRepository.save(login);
        usersRepository.save(user);
        return 0;
    }

    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    public List<User> getAll() {
        return usersRepository.findAll();
    }

    public TokenDto login(LoginDto loginDto) {
        Login login = loginRepository.findByUsername(loginDto.getUsername());
        if (login != null) {
            if (login.getPassword().equals(loginDto.getPassword())) {
                //SUCCESS
                Session session = new Session(loginDto.getUsername(), Instant.now(), Session.EXPIRATION_TIME, login.getRole());
                String token = SessionManager.add(session);
                //System.out.println(SessionManager.getSessionMap().keySet());
                //SessionManager.printMap();
                return new TokenDto(token, "TOKEN OK");
            } else {
                return null;
            }
        } else {
            //ERROR
            return null;
        }
    }

    @Transactional
    public void update(UserDto userDto, Session session) {
        User user = usersRepository.findByUsername(session.getUsername());
        if (userDto.getName() != null)
            user.setName(userDto.getName());
        if (userDto.getCnp() != null)
            user.setCnp(userDto.getCnp());
        if (userDto.getAddress() != null)
            user.setAddress(userDto.getAddress());
        if (userDto.getEmail() != null)
            user.setEmail(userDto.getEmail());
        if (userDto.getPhoneNumber() != null)
            user.setPhoneNumber(userDto.getPhoneNumber());
    }

    @Transactional
    public int addAccount(String currencyType, Session session) {
        User user = usersRepository.findByUsername(session.getUsername());

        for (Account a : user.getAccounts()) {
            if (a.getCurrency().equals(CurrencyType.EUR) && currencyType.equals("EUR") ||
                    a.getCurrency().equals(CurrencyType.RON) && currencyType.equals("RON") ||
                    a.getCurrency().equals(CurrencyType.USD) && currencyType.equals("USD"))
                return -1;//already existing currency account
        }
        Account account = new Account();
        account.setBalance(0.0);
        if (currencyType.equals("EUR"))
            account.setCurrency(CurrencyType.EUR);
        if (currencyType.equals("RON"))
            account.setCurrency(CurrencyType.RON);
        if (currencyType.equals("USD"))
            account.setCurrency(CurrencyType.USD);
        account.setDateOpened(new Date(System.currentTimeMillis()));
        account.setUserFK(user);
        user.getAccounts().add(account);

        accountRepository.save(account);
        return 0;//ok
    }

    public ArrayList<AccountDto> getAccounts(Session session) {
        ArrayList<AccountDto> accountDtos = new ArrayList<>();

        User user = usersRepository.findByUsername(session.getUsername());
        for (Account a : user.getAccounts()) {
            accountDtos.add(new AccountDto(a.getId(), a.getDateOpened(), a.getBalance(), a.getCurrency()));
        }
        return accountDtos;
    }

    @Transactional
    public int addTransfer(MakeTransferDto makeTransferDto, Session session) {
        User userFrom = usersRepository.findByUsername(session.getUsername());
        Optional<Account> ofa = accountRepository.findById(makeTransferDto.getFromId());
        Optional<Account> ota = accountRepository.findById(makeTransferDto.getToId());
        if (makeTransferDto.getAmount() > 1000000)
            return -3;//amount too big
        if (!ofa.isPresent())
            return -2;// From acc not found
        if (!ota.isPresent())
            return -3;// To acc not found
        Account from = ofa.get();
        Account to = ota.get();
        boolean ok = false;
        for (Account a : userFrom.getAccounts()) {
            if (makeTransferDto.getFromId() == a.getId())
                ok = true;
        }
        if (!ok)
            return -1;//not an existing user acc
        Transfer transfer = new Transfer();
        transfer.setAmount(makeTransferDto.getAmount());
        transfer.setTime(new Timestamp(System.currentTimeMillis()));
        transfer.setFrom(from);
        transfer.setTo(to);
        to.getReceived().add(transfer);
        from.getSent().add(transfer);
        from.setBalance(from.getBalance() - makeTransferDto.getAmount());
        to.setBalance(to.getBalance() + makeTransferDto.getAmount());
        transferRepository.save(transfer);

        return 0;
    }

    public ArrayList<TransferDto> getTransfers(Session session) {
        ArrayList<TransferDto> transferDtos = new ArrayList<TransferDto>();
        List<Transfer> transfersSent;
        List<Transfer> transfersReceived;

        User user = usersRepository.findByUsername(session.getUsername());

        for (Account a : user.getAccounts()) {
            transfersSent = transferRepository.findAllByFrom(a);
            transfersReceived = transferRepository.findAllByTo(a);
            for (Transfer t : transfersSent) {
                transferDtos.add(new TransferDto(t.getTime(), "SENT", t.getAmount(), t.getFrom().getId(), t.getTo().getId()));
            }
            for (Transfer t : transfersReceived) {
                transferDtos.add(new TransferDto(t.getTime(), "RECEIVED", t.getAmount(), t.getFrom().getId(), t.getTo().getId()));
            }
        }

        return transferDtos;
    }

    public void logout(String token) {
        SessionManager.getSessionMap().remove(token);
    }
}
