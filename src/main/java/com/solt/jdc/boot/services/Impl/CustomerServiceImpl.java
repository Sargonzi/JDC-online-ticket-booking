package com.solt.jdc.boot.services.Impl;


import com.solt.jdc.boot.domains.Customer;
import com.solt.jdc.boot.domains.Role;
import com.solt.jdc.boot.passwordforget.CustomerRegistrationDto;
import com.solt.jdc.boot.repositories.CustomerRepository;
import com.solt.jdc.boot.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.solt.jdc.boot.domains.Customer;
import com.solt.jdc.boot.domains.Role;
import com.solt.jdc.boot.domains.UserRole;
import com.solt.jdc.boot.repositories.CustomerRepository;
import com.solt.jdc.boot.services.CustomerService;
import com.solt.jdc.boot.services.UserRoleService;


@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public void getCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomer(int id) {
        return customerRepository.getOne(id);
    }

    @Override
    public void saveCustomer(Customer customer) {

        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customer.setEnabled(true);
        customer.setRole_user(getCustomerRole());
        customerRepository.saveAndFlush(customer);
    }

    //moe
    @Override
    public UserRole getCustomerRole() {
        UserRole role = userRoleService.findByRole("ROLE_CUSTOMER");
        if (role != null) {
            return role;
        } else {
            role = new UserRole();
            role.setRole("ROLE_CUSTOMER");
            userRoleService.saveRoleUser(role);
            return role;
        }
    }

    @Override
    public void deleteCustomer(int id) {
        customerRepository.delete(id);
    }

    @Override
    public Customer updateCustomer(Customer customer) {
        return customerRepository.saveAndFlush(customer);
    }

	@Override
	public Customer findByusername(String username) {
		return customerRepository.findByusername(username);
	}

	@Override
	public Customer findByEmail(String email) {
		
		return customerRepository.findByEmail(email);
	}

	@Override
	public Customer save(CustomerRegistrationDto registration) {
		 Customer customer = new Customer();
	        customer.setFirstName(registration.getFirstName());
	        customer.setLastName(registration.getLastName());
	        customer.setEmail(registration.getEmail());
	        customer.setPassword(bCryptPasswordEncoder.encode(registration.getPassword()));
	        customer.setRoles(Arrays.asList(new Role("ROLE_CUSTOMER")));
	        
	        return customerRepository.save(customer);

		
	}

	@Override
	public void updatePassword(String password, Integer customerId) {
		customerRepository.updatePassword(password, customerId);
		
	}

	
	 @Override
	    public UserDetails loadCustomerByUsername(String email) throws UsernameNotFoundException {
	        Customer customer = customerRepository.findByEmail(email);
	        if (customer == null){
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        return new org.springframework.security.core.userdetails.User(customer.getEmail(),
	                customer.getPassword(),
	                mapRolesToAuthorities(customer.getRoles()));
	    }
	 
	 //=======================================

	    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
	        return roles.stream()
	                			.map(role -> new SimpleGrantedAuthority(role.getName()))
	                			.collect(Collectors.toList());
	    }

		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			// TODO Auto-generated method stub
			return null;
		}
	
	//==========================	
}
    @Override
    public long getCustomerCount() {
        return customerRepository.count();
    }
}
