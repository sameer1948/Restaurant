package org.learn.aws.start.service;

import org.learn.aws.start.model.*;
import org.learn.aws.start.repository.AccountRepository;
import org.learn.aws.start.repository.AccountTypeRepository;
import org.learn.aws.start.repository.BranchRepository;
import org.learn.aws.start.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServices {

    @Autowired
    public BranchRepository branchRepository;

    @Autowired
    public AccountTypeRepository accountTypeRepository;

    @Autowired
    public UserDetailsRepository userDetailsRepository;

    @Autowired
    public AccountRepository accountRepository;

    public Account createAccount(int branchId, int accountTypeId) {

        Branch branch = getBranchInfo(branchId);
        AccountType accountType = getAccountTypeInfo(accountTypeId);

        //Branch branch = new Branch(469, "Sample Branch", "Sample Location");

        //Interest interest = new Interest(1, "Normal", 5);
        //AccountType accountType = new AccountType(10, "SAVINGS", interest.getInterestId());

        //UserDetails userDetails = new UserDetails(10000002L, "Sameer", "Sheik", "Sameer@gmail.com", "7894561230", "Sample Address", null);

        UserDetails userDetails = createNewUser(UserDetails.builder()
                .firstName("Sameer6")
                .middleName("")
                .lastName("Sheik")
                .email("Sameer6@gmail.com")
                .phone("7894561234")
                .address("Sample Address 7")
                .securityNumber("ABCD7842E")
                .build());

        if (userDetails != null) {

            Account account = createNewAccount(Account.builder()
                    .userId(userDetails.getUserId())
                    .accountTypeId(accountType.getAccountTypeId())
                    .accountHolderName(getAccHolderName(userDetails.getFirstName(), userDetails.getMiddleName(), userDetails.getLastName()))
                    .branchId(branch.getBranchId())
                    .status("ACTIVE")
                    .balance(5000.00)
                    .build());
            /*Account account = new Account();
            account.setUserId(userDetails.getUserId());
            account.setAccountNumber(Long.valueOf(genAccountNumber(branch.getBranchId())));
            account.setAccountTypeId(accountType.getAccountTypeId());
            account.setBranchId(branch.getBranchId());
            account.setAccountHolderName(getAccHolderName(userDetails.getFirstName(), userDetails.getMiddleName(), userDetails.getLastName()));
            account.setBalance(500.00);
            account.setStatus("ACTIVE");*/

            return account;
        } else {
            return null;
        }




    }

    public String genAccountNumber(int id) {
         return id + "0000000002";
    }

    public String getAccHolderName(String ...names) {
        return names[0] + names[1] + names[2];
    }

    private Branch getBranchInfo(Integer branchId) {
        return branchRepository.findById(branchId).get();//orElseThrow(()-> new RuntimeException("Branch Info Not Found"));
    }

    private AccountType getAccountTypeInfo(Integer accountTypeId) {
        return accountTypeRepository.findById(accountTypeId).get();//orElseThrow(()-> new RuntimeException("Branch Info Not Found"));
    }

    private UserDetails createNewUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    private Account createNewAccount(Account account) {
        return accountRepository.save(account);
    }

}
