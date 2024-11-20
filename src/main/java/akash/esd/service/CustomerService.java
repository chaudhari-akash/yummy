package akash.esd.service;

import akash.esd.dto.CustomerRequest;
import akash.esd.dto.CustomerResponse;
import akash.esd.dto.LoginRequest;
import akash.esd.entity.Customer;
import akash.esd.exception.CustomerNotFoundException;
import akash.esd.helper.EncryptionService;
import akash.esd.helper.JWTHelper;
import akash.esd.mapper.CustomerMapper;
import akash.esd.repo.CustomerRepo;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;

    public String createCustomer(CustomerRequest request){
        Customer customer = mapper.toEntity(request);
        customer.setPassword(encryptionService.encode(customer.getPassword()));
        repo.save(customer);
        return "Customer Created";
    }

    public Customer getCustomer(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update Customer:: No customer found with the provided ID:: %s", email)
                ));
    }

    public CustomerResponse retrieveCustomer(String email) {
        Customer customer = getCustomer(email);
        return mapper.toCustomerResponse(customer);
    }

    public String login(LoginRequest request) {
        Customer customer = getCustomer(request.email());
        if(!encryptionService.validates(request.password(), customer.getPassword())) {
            return "Wrong Password or Email";
        }

        return jwtHelper.generateToken(request.email());
    }

    public Customer updateCustomer(@Valid CustomerRequest request) {
        Customer customer = getCustomer(request.email());
//        customer.setCity(request.city());
//        customer.setAddress(request.address());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
//        customer.setPincode(request.pinCode());
        repo.save(customer);
        return customer;
    }

    @Transactional
    public String deleteCustomer(@Valid CustomerRequest request) {
        repo.deleteByEmail(request.email());
        return "Deleted customer with name "+request.email()+".";
    }
}
