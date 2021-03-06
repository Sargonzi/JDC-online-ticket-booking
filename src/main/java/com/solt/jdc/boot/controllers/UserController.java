package com.solt.jdc.boot.controllers;

import com.solt.jdc.boot.domains.Role;
import com.solt.jdc.boot.domains.User;
import com.solt.jdc.boot.services.RoleService;
import com.solt.jdc.boot.services.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private MainController mainController;

    
    @Autowired
	private RoleService roleService;
    
    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public String findAllUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/user/index";
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public String add(Model model) {
    		
    	
    	List<Role> allRoles=roleService.getAllRoles();	
    	model.addAttribute("allRoles",allRoles			
							.stream()//change into  an sequence of cities  ->stream 
							.map(e -> e.getName())  
							.collect(Collectors.toList()));
    	    	
    	model.addAttribute("user", new User());
        return "admin/user/userAdd";
    
    }

    @RequestMapping(value = "/users/add", method = RequestMethod.POST)
    public String save(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/user/userAdd";
        } 
        mainController.disallowedFieldException(result);
        userService.save(user);
        return "redirect:/admin/users";
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.find(id));
        return "admin/user/userDetails";
    }

    @RequestMapping(value = "/user/update/{id}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("user") @Valid User user,@PathVariable("id") int userId, BindingResult result) {
        /*if (result.hasErrors()) {
            return "admin/user/userEdit";
        }*/
        User currentUser = userService.find(userId);
        currentUser.setUserName(user.getUserName());
        currentUser.setFirstName(user.getFirstName());
        currentUser.setLastName(user.getLastName());
        currentUser.setPhone(user.getPhone());
        currentUser.setEmail(user.getEmail());
        currentUser.setRole(user.getRole());
        currentUser.setStatus(user.isStatus());
        if (currentUser.getPassword().isEmpty()) {
            currentUser.setPassword(user.getPassword());
        }else {
        	currentUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userService.update(currentUser);
        return "redirect:/admin/user/update/"+userId;
    }

    @RequestMapping(value = "/user/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(userService.find(id));
        return "redirect:/admin/users";
    }


    @InitBinder
    public void initializeBinder(WebDataBinder binder) {
        binder.setAllowedFields("userName", "firstName", "lastName", "phone", "email", "role", "status", "password");
    }
    
    
    //==============================================
   
    
    //===============================================
    
    
}