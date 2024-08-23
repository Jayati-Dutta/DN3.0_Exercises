package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.dto.CustomerDTO;
import com.example.bookstoreapi.mapper.CustomerMapper;
import com.example.bookstoreapi.model.Customer;
import com.example.bookstoreapi.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDTOToCustomer(customerDTO);
        Customer createdCustomer = customerService.saveCustomer(customer);
        CustomerDTO createdCustomerDTO = customerMapper.customerToCustomerDTO(createdCustomer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Custom-Header", "CreationHeaderValue");
        return new ResponseEntity<>(createdCustomerDTO, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isPresent()) {
            CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer.get());
            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Custom-Header", "AnotherCustomValue");
            return new ResponseEntity<>(customerDTO, headers, HttpStatus.OK);
        }
