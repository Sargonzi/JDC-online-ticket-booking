package com.solt.jdc.boot.controllers;

import com.solt.jdc.boot.domains.Trip;
import com.solt.jdc.boot.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TripController {

    private TripService tripService;

    @Autowired
    public void getTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @RequestMapping("/trips")
    public String getAllTrip(Model model) {
        model.addAttribute("trips", tripService.getAllTrips());
        return "admin/trip/index";
    }

    @RequestMapping("/trips/{id}")
    public String getTrip(Model model, @PathVariable("id") int tripId) {
        model.addAttribute("trip", tripService.getTrip(tripId));
        return "admin/trip/index";
    }

    @RequestMapping(value = "/trips/add", method = RequestMethod.GET)
    public String addTrip(Model model) {
        Trip trip = new Trip();
        model.addAttribute("newTrip", trip);
        return "admin/trip/addNew";
    }

    @RequestMapping(value = "/trips/add", method = RequestMethod.POST)
    public String processAddTrip(@ModelAttribute("trip") Trip trip) {
        tripService.saveTrip(trip);
        return "redirect:/trips";
    }

    @RequestMapping(value = "/trips/update/{id}", method = RequestMethod.GET)
    public String updateTrip(@PathVariable("id") int tripId, Model model) {
        model.addAttribute("trip", tripService.getTrip(tripId));
        return "admin/trip/update";
    }

    @RequestMapping(value = "/trips/update/{id}", method = RequestMethod.POST)
    public String processUpdateTrip(@ModelAttribute("trip") Trip trip, @PathVariable("id") int tripId){
        return "redirect:/trips";
    }

    @RequestMapping("/trip/delete/{id}")
    public String deleteTrip(@PathVariable ("id") int tripId) {
        tripService.deleteTrip(tripId);
        return "redirect:/trips";
    }
}
