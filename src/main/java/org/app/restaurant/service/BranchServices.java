package org.app.restaurant.service;

import org.app.restaurant.repository.BranchRepository;
import org.app.restaurant.model.Branch;
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
