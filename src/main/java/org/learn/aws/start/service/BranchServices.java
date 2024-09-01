package org.learn.aws.start.service;

import org.learn.aws.start.model.Branch;
import org.learn.aws.start.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServices {

    @Autowired
    public BranchRepository branchRepository;

    public List<Branch> getAllBranch() {
        return branchRepository.findAll();
    }
}
