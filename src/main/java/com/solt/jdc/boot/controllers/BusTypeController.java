package com.solt.jdc.boot.controllers;

import java.util.ArrayList;
import java.util.List;

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

import com.solt.jdc.boot.domains.Bus;
import com.solt.jdc.boot.domains.BusType;
import com.solt.jdc.boot.services.BusTypeService;

@Controller
@RequestMapping("/admin")
public class BusTypeController {
	
	@Autowired
	private BusTypeService busTypeService;
	
	@Autowired
	private MainController mainController;

<<<<<<< HEAD
	@RequestMapping(value = "/bustypes/add", method = RequestMethod.GET)
	public String addBusTypeGet(Model model) {
		BusType busType = new BusType();
		model.addAttribute("busType", busType);
		return "admin/bustypes/addBusType";
	}
	
	@RequestMapping(value="/bustypes/add",method=RequestMethod.POST)
	public String addBusTypePost(@ModelAttribute("busType")BusType busType) {
		busTypeService.addBusType(busType);
		return "redirect:/admin/bustypes";
=======
	
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String addBusTypePost(@ModelAttribute("busType") @Valid BusType busType,BindingResult result) {
		if(result.hasErrors()) {
    		return "admin/bustypes/addBusType";
    	}
		List<BusType> busTypeList=busTypeService.getAllBusTypes();
		if(busTypeList.size()==0) {
			busType.setId(1);
		}
		else {
			BusType busTypeForId=busTypeList.get(busTypeList.size()-1);
			busType.setId(busTypeForId.getId()+1);
		}
		mainController.disallowedFieldException(result);
		busTypeService.addBusType(busType);
		return "redirect:/bus/buses";
>>>>>>> feature/trip&cities_Binding
	}
	
	@RequestMapping(value="/bustypes")
	public String getAllBusTypes(Model model) {
		model.addAttribute("bustypes",busTypeService.getAllBusTypes());
		return "admin/bustypes/index";
	}
	
<<<<<<< HEAD
	@RequestMapping(value="/bustype/delete/{busTypeId}")
	public String deleteBus(Model model,@PathVariable("busTypeId")int busTypeId) {
		
		busTypeService.deleteBusType(busTypeService.findById(busTypeId));
		return "redirect:/admin/bustypes";
	}
	
	@RequestMapping(value="/bustype/update/{busTypeId}",method=RequestMethod.GET)
	public String updateBusGet(Model model,@PathVariable("busTypeId")int busTypeId) {
		model.addAttribute("bustype", busTypeService.findById(busTypeId));
		return "admin/bustypes/updateform";
	}
	
	@RequestMapping(value="/bustype/update/{busTypeId}",method=RequestMethod.POST)
	public String updateBusPOST(@ModelAttribute("bustype")BusType newbusType,@PathVariable("busTypeId")int id) {
		BusType currentBusType=busTypeService.findById(id);
		currentBusType.setType(newbusType.getType());
		busTypeService.updateBusType(currentBusType);
		return "redirect:/admin/bustypes";
=======
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("busType")@Valid BusType newBusType) {
		
		
		BusType currentBusType=busTypeService.findById(newBusType.getId());
		currentBusType.setType(newBusType.getType());
		busTypeService.updateBusType(currentBusType);
		return "redirect:/bus/buses";
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/loadEntity/{id}", method = RequestMethod.GET)
	public BusType loadEntity(@PathVariable("id") Integer id) {
		return busTypeService.findById(id);
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteBusType(@PathVariable("id")int id) {
		busTypeService.deleteBusType(busTypeService.findById(id));
		return "redirect:/bus/buses";
	}
	
	@InitBinder
	public void initializeBinder(WebDataBinder binder) {
		binder.setAllowedFields("id","type");
>>>>>>> feature/trip&cities_Binding
	}
	

}
