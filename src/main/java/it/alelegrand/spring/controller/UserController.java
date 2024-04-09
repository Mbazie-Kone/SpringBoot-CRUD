package it.alelegrand.spring.controller;

import java.io.InputStream;
import java.nio.file.*;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.alelegrand.spring.domain.User;
import it.alelegrand.spring.domain.UserDto;
import it.alelegrand.spring.repository.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping({"", "/"})
	public String menu(Model model) {
		List<User> users = (List<User>) userRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
		model.addAttribute("users", users);
		
		return "users/menu";
		
	}
	
	@GetMapping("/add")
	public String showAddUser(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("userDto", userDto);
		
		return "users/addUser";
	}
	
	@PostMapping("/add")
	public String addUser(@Valid @ModelAttribute UserDto userDto, BindingResult result) {
		
		if(userDto.getImageFile().isEmpty()) {
			result.addError(new FieldError("userDto", "imageFile", "The image file is required"));
		}
		
		if(result.hasErrors()) {
			
			return "users/addUser";
		}
		
		// save image file
		MultipartFile image = userDto.getImageFile();
		Date createdAt = new Date();
		String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
		
		try {
			String uploadDir = "public/images/";
			java.nio.file.Path uploadPath = Paths.get(uploadDir);
			
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			try(InputStream inpustream = image.getInputStream()) {
				Files.copy(inpustream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		User user = new User();
		user.setName(userDto.getName());
		user.setSurname(userDto.getSurname());
		user.setAddress(userDto.getAddress());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setEmail(userDto.getEmail());
		user.setImageFileName(storageFileName);
		
		userRepo.save(user);
		
		return "redirect:/users";
	}
	
	@GetMapping("/update")
	public String showUpdatePage(Model model, @RequestParam Long id) {
		
		try {
			
			User user = userRepo.findById(id).get();
			model.addAttribute("user", user);
			
			UserDto userDto = new UserDto();
			userDto.setName(user.getName());
			userDto.setSurname(user.getSurname());
			userDto.setAddress(user.getAddress());
			userDto.setPhoneNumber(user.getPhoneNumber());
			userDto.setEmail(user.getEmail());
			model.addAttribute("userDto", userDto);
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			
			return "redirect:/users";
		}
		
		return "users/updateUser";
	}
	
	@PostMapping("/update")
	public String updateUser(Model model, @RequestParam Long id, @Valid @ModelAttribute UserDto userDto, BindingResult result) {
		
		try {
			User user = userRepo.findById(id).get();
			model.addAttribute("user", user);
			
			if(result.hasErrors()) {
				
				return "users/updateUser";
			}
			
			if(!userDto.getImageFile().isEmpty()) {
				// delete old image
				String uploadDir = "public/images/";
				Path oldImagepath = Paths.get(uploadDir + user.getImageFileName());
				
				try {
					Files.delete(oldImagepath);
				}
				catch (Exception ex) {
					System.out.println("Exception: " + ex.getMessage());
				}
				
				// save new image file
				MultipartFile image = userDto.getImageFile();
				Date createdAt = new Date();
				String storageFileName = createdAt.getTime() + "_" + image.getOriginalFilename();
				
				try(InputStream inpustream = image.getInputStream()) {
					Files.copy(inpustream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
				}
				
				user.setImageFileName(storageFileName);
			}
			
			user.setName(userDto.getName());
			user.setSurname(userDto.getSurname());
			user.setAddress(userDto.getAddress());
			user.setPhoneNumber(userDto.getPhoneNumber());
			user.setEmail(userDto.getEmail());
			
			userRepo.save(user);
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/users";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam Long id) {
		
		try {
			
			User user = userRepo.findById(id).get();
			
			// delete user image
			Path imagePath = Paths.get("public/images/" + user.getImageFileName());
			
			try {
				
				Files.delete(imagePath);
				
			} catch (Exception ex) {
				System.out.println("Exception: " + ex.getMessage());
			}
			
			// delete the user
			userRepo.delete(user);
			
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
		}
		
		return "redirect:/users";
	}
}