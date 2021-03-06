package com.solt.jdc.boot.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.solt.jdc.boot.domains.Services;
import com.solt.jdc.boot.services.BusService;
import com.solt.jdc.boot.services.ServicesService;


@Controller
@RequestMapping("/admin")
public class ServiceController {

	@Autowired
	private ServicesService servicesService;
	
	@Autowired
	private BusService busService;
	
	@Autowired
	private MainController mainController;

	@RequestMapping(value="/services/add",method=RequestMethod.POST)
	public String addServicePOST(Model model,@ModelAttribute("service") @Valid Services service,BindingResult result) {
		if(result.hasErrors()) {
    		return "admin/services/addService";
    	}
		List<Services> servicesList=servicesService.getAllServices();
		if(servicesList.size()==0) {
			service.setId(1);
		}
		else {
			Services serviceForId=servicesList.get(servicesList.size()-1);
			service.setId(serviceForId.getId()+1);
		}
		mainController.disallowedFieldException(result);
		servicesService.addService(service);
		return "redirect:/admin/buses";
	}

	@RequestMapping(value="/service/delete/{serviceId}")
	public String deleteService(@PathVariable("serviceId")int serviceId,Model model) {
		servicesService.deleteService(serviceId);
		return "redirect:/admin/buses";
	}
	
	
	
	@RequestMapping(value="/service/update",method=RequestMethod.POST)
	public String updateServicePOST(@ModelAttribute("service") @Valid Services newService,BindingResult result) {
		if(result.hasErrors()) {
    		return "admin/bus/updateServiceForm";
    	}
		Services currentService=servicesService.findById(newService.getId());
		currentService.setServices(newService.getServices());
		currentService.setBus(newService.getBus());
		servicesService.updateService(currentService);
		return "redirect:/admin/buses";
	}
	
	@ResponseBody
	@RequestMapping(value="/service/loadEntity/{id}")
	public Services loadEntity(@PathVariable("id")int id) {
		return servicesService.findById(id);
	}
	
	@InitBinder
	public void intializeBinder(WebDataBinder binder) {
		binder.setAllowedFields("id","services","bus");
	}
}
