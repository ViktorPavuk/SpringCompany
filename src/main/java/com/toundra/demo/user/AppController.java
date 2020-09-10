package com.toundra.demo.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.Vector;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.toundra.demo.user.validation.ClientNameValidator;





@Controller
public class AppController {
	
	@Autowired
	private ClientNameValidator clientNameValidator;

	@Autowired
	ProductRepository productService;
	
	@Autowired
	EmployeeRepository employeeService;
	
	@Autowired
	ClientRepository clientService;
	
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	

	@RequestMapping("/")
	public String randomFact(Model model) {

		try {
			String link = "https://uselessfacts.jsph.pl/random.json?language=en";
			Gson gson = new Gson();
			StringBuilder apiResult = new StringBuilder();
			URLConnection conn = new URL(link).openConnection(); 
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) 
				apiResult.append(line);
			rd.close();
			Map respMap = gson.fromJson(apiResult.toString(), HashMap.class);
			String fact = respMap.get("text").toString();
			model.addAttribute("uselessFact", fact);
			return "index";
		}
		catch(Exception e) {
			System.out.println("Couldn't access the fact API");
			return "redirect:/";
		}
	}
	
	
	@RequestMapping("/about")
	public String aboutRedirect() {
		return "redirect:/";
	}
	
	@RequestMapping("/employees")
	public String viewEmployees(Model model) {
		model.addAttribute("listEmployees", employeeService.findAll());
		return "employees";
	}
	
	@RequestMapping("/employees/new")
	public String newEmployee(Employee employee) {
	    return "new_employee";
	}
	
	@RequestMapping("/employees/save")
	public String addEmployee(@Valid Employee employee, BindingResult bindingResult) {
		
		if (employee.getId() == 0 && employeeService.existsEmployeeByEmail(employee.getEmail()))
			return "redirect:/employees/new?exists";
		if (bindingResult.hasErrors()) {
			return "new_employee";
		}
		employeeService.save(employee);
	    return "redirect:/employees";
	}
	
	
	@RequestMapping("/employees/edit/{id}")
	public ModelAndView editEmployee(@PathVariable(name = "id") int id) {
//		ModelAndView mav = new ModelAndView("edit_employee");	
//		return new ModelAndView("edit_employee").addObject("employee", employeeService.findById(id).get());	
		return employeeService.findById(id).isPresent()?new ModelAndView("edit_employee").addObject("employee", employeeService.findById(id).get()):null;
	}
	
	@RequestMapping("/employees/delete/{id}")
	public String deleteEmployee(@PathVariable(name = "id") int id) {
		employeeService.deleteById(id);
		return "redirect:/employees";
	}
	
	@RequestMapping("/employees/{id}")
	@ResponseBody
	public Optional<Employee> getEmployeeJSON(@PathVariable("id")int id) {
		return employeeService.findById(id);
	}
	
	@RequestMapping("/employees/list")
	@ResponseBody
	public List<Employee> getEmployeesJSON() { 
		return employeeService.findAll();
	}
	
	
	@RequestMapping("/employees/{id}/clients")
	public String viewEmployeeClients(@PathVariable("id")int id, Model model) {
		model.addAttribute("name", employeeService.findById(id));
		model.addAttribute("listClients", clientService.findByEmployeeId(id));
		return "employeeClients";
	}
	
	
	
	
	
	@RequestMapping("/clients")
	public String viewClients(Model model) {
		model.addAttribute("listClients", clientService.findAll());
		return "clients";
	}
	
	@RequestMapping("/clients/new")
	public String newEmployee(Client client, Model model) {
		model.addAttribute("listEmployees", employeeService.findAll());
	    return "new_client";
	}
	
	@RequestMapping("/clients/save")
	public String addClient(@Valid Client client, BindingResult bindingResult, Model model) {
		
		//In Method Validation
//		ClientNameValidator clientNameValidator = new ClientNameValidator();
//		(new ClientNameValidator()).validate(client, bindingResult);
		clientNameValidator.validate(client, bindingResult);
		
		
		model.addAttribute("listEmployees", employeeService.findAll());
		if (bindingResult.hasErrors()) {
			return "new_client";
		}
		clientService.save(client);
	    return "redirect:/clients";
	}
	
	
	@RequestMapping("/clients/edit/{id}")
	public ModelAndView editClient(@PathVariable(name = "id") int id, Model model) {
//		ModelAndView mav = new ModelAndView("edit_employee");	
//		return new ModelAndView("edit_employee").addObject("employee", employeeService.findById(id).get());	
		model.addAttribute("listEmployees", employeeService.findAll());
		return clientService.findById(id).isPresent()?new ModelAndView("edit_client").addObject("client", clientService.findById(id).get()):null;
	}
	
	@RequestMapping("/clients/delete/{id}")
	public String deleteClient(@PathVariable(name = "id") int id) {
		clientService.deleteById(id);
		return "redirect:/clients";
	}
	
	@RequestMapping("/clients/{id}")
	@ResponseBody
	public Optional<Client> getClientJSON(@PathVariable("id")int id) {
		return clientService.findById(id);
	}
	
	@RequestMapping("/clients/list")
	@ResponseBody
	public List<Client> getClientsJSON() { 
		return clientService.findAll();
	}
	
	@RequestMapping("/products")
	public String viewProducts(Model model) {
		//List<Product> listProducts = productService.findAll();
		model.addAttribute("listProducts", productService.findAll());
		return "products";
	}
	
	@RequestMapping("/products/new")
	public String showNewProductPage(Product product) {
	    return "new_product";
	}
	
	
	@RequestMapping("/products/save")
	public String addProduct(@Valid Product product, BindingResult bindingResult) {
		//System.out.println(bindingResult.hasErrors());
		if (bindingResult.hasErrors()) {
			//System.out.println("Error in result");
			return "new_product";
		}
		productService.save(product);
		//System.out.println("Result successfull");
	    return "redirect:/products";
	}
	
	
	// Weather Stuff
	@RequestMapping("/weather")
	@ResponseBody
	public String checkWeather(Model model) {
		//WeatherAPI
		String link ="http://www.7timer.info/bin/civil.php?lon=73.6&lat=45.5&ac=0&unit=metric&output=json";
		
		try {
			System.out.println("Accessing weather services...");
			//Build Path -> Add External Archives -> gson-2.6.2.jar
			Gson gson = new Gson();
			
			StringBuilder apiResult = new StringBuilder();
			URLConnection conn = new URL(link).openConnection(); 
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) 
				apiResult.append(line);
			rd.close();
			
			//First HashMap
			HashMap respMap = gson.fromJson(apiResult.toString(), HashMap.class);
			
			//Second HashMap containing only current information
			HashMap finalMap = gson.fromJson(((ArrayList) respMap.get("dataseries")).get(0).toString(), HashMap.class);
			String weather = "Current temperature in Montreal, Canada: " + finalMap.get("temp2m").toString() + "°C";
			
			return weather;
		}
		catch(Exception e) {
			System.out.println("Couldn't access the weather");
			return "redirect:/";
		}
	}
	

	
	@RequestMapping("/products/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {
//		ModelAndView mav = new ModelAndView("edit_product");
//		Product product = productService.findById(id).get();
//		mav.addObject("product", productService.findById(id).get());
//		return mav.addObject("product", productService.findById(id).get());	
		return productService.findById(id).isPresent()?new ModelAndView("edit_product").addObject("product", productService.findById(id).get()):null;		
	}
	
	@RequestMapping("/products/{id}")
	@ResponseBody
	public Optional<Product> getProductJSON(@PathVariable("id")int id) {
		//return repo.findById(aid).toString();
		return productService.findById(id);
	}
	
	@RequestMapping("/products/list")
	@ResponseBody
	public List<Product> getProcutsJSON() { 
		//return repo.findById(aid).toString();
		return productService.findAll();
	}
	
	@RequestMapping("/products/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {
		productService.deleteById(id);
		return "redirect:/products";
	}
	
	//For later use if needed
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		//Removed the field from being binded
//		binder.setDisallowedFields("clientName");
		
//		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(false);
//		binder.registerCustomEditor(String.class, "clientName", stringTrimmerEditor);
		
//		System.out.println("Inside init binder");
		
		//Register a custom editor for the money field
//		NumberFormat numberFormat = new DecimalFormat("##,###");
//		CustomNumberEditor numberEditor = new CustomNumberEditor(Integer.class, numberFormat, true);
//		binder.registerCustomEditor(Integer.class, "sales", numberEditor);
		
		//Init binder Spring validator
//		binder.addValidators(new ClientNameValidator());
		
	}
	
	
	
	
	
}