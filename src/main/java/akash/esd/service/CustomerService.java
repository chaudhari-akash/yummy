package akash.esd.service;

import akash.esd.dto.CustomerRequest;
import akash.esd.entity.Customer;
import akash.esd.mapper.CustomerMapper;
import akash.esd.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request){
        Customer customer = mapper.toEntity(request);
        repo.save(customer);
        return "Created";
    }

}
