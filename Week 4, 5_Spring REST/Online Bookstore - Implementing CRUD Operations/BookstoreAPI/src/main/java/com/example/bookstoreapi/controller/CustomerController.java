package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.exception.CustomerNotFoundException;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CustomHeaderValue");
        return new ResponseEntity<>(customerDTOs, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "AnotherCustomValue");
            return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer createdCustomer = customerService.saveCustomer(customer);
        CustomerDTO createdCustomerDTO = customerMapper.customerToCustomerDTO(createdCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CreationHeaderValue");
        return new ResponseEntity<>(createdCustomerDTO, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer == null) {
            throw new CustomerNotFoundException(id);
        }
        CustomerDTO updatedCustomerDTO = customerMapper.customerToCustomerDTO(updatedCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "UpdateHeaderValue");
        return new ResponseEntity<>(updatedCustomerDTO, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "DeletionHeaderValue");
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
    }
}
