/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.entities.Breweries;
import com.entities.BreweriesGeocode;
import com.services.BreweryService;
import java.util.List;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import org.springframework.hateoas.Resource;
//import org.springframework.hateoas.Resources;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MTimos
 */
@RestController
@RequestMapping("/breweries")
public class BreweryRestController {
    
    @Autowired
    BreweryService service;
    
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable("id") int id, @RequestBody Breweries b, BreweriesGeocode g) {
        service.edit_Brewery(id, b, g);
    }
    
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public boolean create(@RequestBody Breweries b, BreweriesGeocode g) {
//        return service.add_Brewery(b,g);
//    }
    
    
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") int id) {
        Breweries brewery = service.getBreweriesByID(id);
        BreweriesGeocode bg = service.getBreweriesGeocodeByID(id);
        service.deleteBrewery(brewery);
        service.deleteBreweryGeocode(bg);
    }  
    
    @GetMapping("/{id}")
    public Breweries getBreweryByID(@PathVariable("id") int id) {
        return service.getBreweriesByID(id);
    }
    
    @GetMapping(value = "/hateoas", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Breweries> getBreweryHATEOAS(){
        
      List<Breweries> allBreweries = service.getAllBreweries();

        for (Breweries b : allBreweries) {

            int breweryId = b.getId();//getBreweryByID();
            Link selfLink = linkTo(this.getClass()).slash(breweryId).withSelfRel();
            //b.add(selfLink);
            linkTo(methodOn(this.getClass()).getBreweryByID(breweryId));
        }
        Link link = linkTo(this.getClass()).withSelfRel();
        Resources<Breweries> result = new Resources<Breweries>(allBreweries, link);
        return result;

       // return service.getAllBreweries();
    }
    
    @GetMapping (value="/hateoas/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public Resource<Breweries> retrieveBrewery(@PathVariable("id") int id) {
        
        Resource<Breweries> resource = new Resource<Breweries>(service.getBreweriesByID(id));

        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getBreweries());

        resource.add(linkTo.withRel("all-breweries"));

        return resource;
    }
    
    @GetMapping
    public List<Breweries> getBreweries() {
        return service.getAllBreweries();
    }   
}
