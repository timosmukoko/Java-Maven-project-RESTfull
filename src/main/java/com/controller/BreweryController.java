/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.Breweries;
import com.entities.BreweriesGeocode;
import com.services.BreweryService;
//import SD4.dao.BreweryFormValidator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author MTimos
 */
@Controller
@RequestMapping("/brewery")
@SessionAttributes("brewery")
public class BreweryController {
    
    @Autowired
    BreweryService service;
    
//    @Autowired
//    BreweryFormValidator breweryFormValidator;
    
//   // Set a form validator
//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//	binder.setValidator(breweryFormValidator);
//    }
//        
    @GetMapping("")
    public ModelAndView getBreweries(){
       return new ModelAndView("/allBreweries", "breweriesList", service.getAllBreweries());
    }
     
    @GetMapping("/add")
    public ModelAndView displayAgentAddForm(ModelAndView model) {
    return new ModelAndView("/addBrewery", "brewery", new Breweries());
    }

    @PostMapping("/addBrewery")
    public ModelAndView addBrewery( ModelMap model, @Valid @ModelAttribute("brewery")Breweries brewery, 
           BreweriesGeocode g, BindingResult result) throws ParseException{  
        
        brewery.setId(brewery.getId());
        brewery.setName(brewery.getName());
        brewery.setAddress1(brewery.getAddress1());
        brewery.setAddress2(brewery.getAddress2());
        brewery.setCity(brewery.getCity());
        brewery.setCity(brewery.getCity());
        brewery.setCode(brewery.getCode());
        brewery.setCountry(brewery.getCountry());
        brewery.setPhone(brewery.getPhone());
        brewery.setWebsite(brewery.getWebsite());
        brewery.setImage(brewery.getImage());
        brewery.setDescription(brewery.getDescription());    
        brewery.setAddUser(brewery.getAddUser());
        brewery.setLastMod(brewery.getLastMod());
        brewery.setCreditLimit(brewery.getCreditLimit());
        brewery.setEmail(brewery.getEmail());
        g.setLatitude(g.getLatitude());
        g.setLongitude(g.getLongitude());
        
        if (result.hasErrors()) {
          // breweryFormValidator.validate(brewery, result);
           return new ModelAndView("/addBrewery");
                //display error message
        }
      
//      List<ObjectError> errors = result.getAllErrors();
//        
//        for (ObjectError error : errors) {
//            System.out.println(error);
//        }
      service.addBrewery(brewery, g);
      return new ModelAndView("redirect:/brewery"); 
      //return new ModelAndView("/addBrewery", "brewery", new Breweries());
        
    }
    
    @GetMapping("/edit")
    public ModelAndView displayBreweryEditForm(@QueryParam("id") int id, ModelAndView model) {
        
       Breweries b = service.getBreweriesByID(id);
       model.setViewName("/editBrewery");
       model.addObject("brewery", b);
       return model;
    }
    
    @PostMapping("/editBrewery")
    public ModelAndView editBrewery(@ModelAttribute("brewery") Breweries brewery,             
           BreweriesGeocode g, BindingResult result, SessionStatus status){
        
         if (result.hasErrors()){
            return new ModelAndView("/error");
        }
//        List<ObjectError> errors = result.getAllErrors();    
//        for (ObjectError error : errors) {
//            System.out.println(error);
//        } 
        service.editBrewery(brewery, g);
        status.setComplete();
        return new ModelAndView("redirect:/brewery");      
    }
        
    @GetMapping("/delete")
    public ModelAndView deleteBrewery(@QueryParam("id") int id) {
        Breweries brewery = service.getBreweriesByID(id);
        BreweriesGeocode bg = service.getBreweriesGeocodeByID(id);
        service.deleteBrewery(brewery);
        service.deleteBreweryGeocode(bg);
        return new ModelAndView("redirect:/brewery");
    }
    
//     @ExceptionHandler(NullPointerException.class)
//    public ModelAndView handleNullPointerException(NullPointerException ex) {
//        return new ModelAndView("/error", "message", "Null value");
//    }
//    
//    @ExceptionHandler (NumberFormatException.class)
//    public ModelAndView handleNFError(){
//        return new ModelAndView("/error", "message", "number format");
//    }
//    
    @ExceptionHandler (Exception.class)
    public ModelAndView handleError(){
        return new ModelAndView("/error", "message", "An");
    }
        
}
