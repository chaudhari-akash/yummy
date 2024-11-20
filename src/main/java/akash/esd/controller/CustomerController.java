package akash.esd.controller;

import akash.esd.dto.CustomerRequest;
import akash.esd.entity.Customer;
import akash.esd.exception.CustomerNotFoundException;
import akash.esd.helper.JWTHelper;
import akash.esd.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final JWTHelper jwtHelper;

    @PostMapping()
    public ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request){
        return ResponseEntity.ok(customerService.createCustomer(request));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> detailsCustomer(@RequestHeader(value="Authorization") String authHeader, @PathVariable("email") String email) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if(jwtHelper.validateToken(jwtToken, email))
                return ResponseEntity.ok(customerService.retrieveCustomer(email));
            return ResponseEntity.ok("Invalid Token");
        }
        return ResponseEntity.ok("Token not found.");
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestHeader(value="Authorization") String authHeader, @RequestBody @Valid CustomerRequest request) {
        try{
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwtToken = authHeader.substring(7);
                if(jwtHelper.validateToken(jwtToken, request.email())) {
                    Customer customer = customerService.updateCustomer(request);
                    return ResponseEntity.ok(customer);
                }
                return ResponseEntity.ok("Invalid Token");
            }
        }catch (CustomerNotFoundException e){
            return ResponseEntity.ok("Specified element not found");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCustomer(@RequestHeader(value="Authorization") String authHeader, @RequestBody @Valid CustomerRequest request) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            if(jwtHelper.validateToken(jwtToken, request.email()))
                return ResponseEntity.ok(customerService.deleteCustomer(request));
            return ResponseEntity.ok("Invalid Token");
        }
        return ResponseEntity.ok("Token not found.");
    }
}
