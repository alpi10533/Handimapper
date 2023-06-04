package com.isep.handimapper.web;

import com.isep.handimapper.business.*;
import com.isep.handimapper.service.*;
import com.isep.handimapper.util.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Controller
public class MainController {

    private final UserService userService;
    private final PlaceService placeService;
    private final NoteService noteService;
    private final ReviewService reviewService;
    private final EquipmentService equipmentService;

    @Autowired
    public MainController(UserService userService, PlaceService placeService, NoteService noteService, ReviewService reviewService, EquipmentService equipmentService) {
        this.userService = userService;
        this.placeService = placeService;
        this.noteService = noteService;
        this.reviewService = reviewService;
        this.equipmentService = equipmentService;
    }

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String processRegistration(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model) {
        UserEntity existingUser = userService.findUserByEmail(userDto.getEmail());
        if (existingUser != null) {
            result.rejectValue("email", null,"L'adresse e-mail saies est déjà associée à un compte !");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/registration";
        }
        userService.saveUser(userDto);
        return "redirect:/registration?success";
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        UserEntity user = userService.findUserByEmail(authentication.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/note-place/{id}")
    public String notePlace(@PathVariable("id") String id, Model model, HttpSession session, Authentication authentication) {
        UserEntity userEntity = userService.findUserByEmail(authentication.getName());
        PlaceEntity placeEntity = placeService.findPlaceById(id);
        if (placeEntity == null) {
            placeEntity = placeService.savePlace(id);
        }
        NoteEntity noteEntity = noteService.findNoteByUserAndPlace(userEntity, placeEntity);
        NoteDto noteDto = new NoteDto();
        if (noteEntity != null) {
            noteDto.setNote(String.valueOf(noteEntity.getNote()));
        }
        session.setAttribute("placeEntity", placeEntity);
        session.setAttribute("noteEntity", noteEntity);
        model.addAttribute("noteDto", noteDto);
        return "note-place";
    }

    @PostMapping("/note-place")
    public String processNotePlace(@Valid @ModelAttribute("noteDto") NoteDto noteDto, BindingResult result, Model model, HttpSession session, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("noteDto", noteDto);
            return "note-place";
        }
        NoteEntity noteEntity = (NoteEntity) session.getAttribute("noteEntity");
        PlaceEntity placeEntity = (PlaceEntity) session.getAttribute("placeEntity");
        if (noteEntity != null) {
            noteService.updateNote(noteEntity, noteDto);
        } else {
            UserEntity userEntity = userService.findUserByEmail(authentication.getName());
            noteService.saveNote(noteDto, userEntity, placeEntity);
        }
        return "redirect:/";
    }

    @GetMapping("/review-place/{id}")
    public String reviewPlace(@PathVariable("id") String id, Model model, HttpSession session, Authentication authentication) {
        UserEntity userEntity = userService.findUserByEmail(authentication.getName());
        PlaceEntity placeEntity = placeService.findPlaceById(id);
        if (placeEntity == null) {
            placeEntity = placeService.savePlace(id);
        }
        ReviewEntity reviewEntity = reviewService.findReviewByUserAndPlace(userEntity, placeEntity);
        ReviewDto reviewDto = new ReviewDto();
        if (reviewEntity != null) {
            reviewDto.setReview(reviewEntity.getReview());
        }
        session.setAttribute("placeEntity", placeEntity);
        session.setAttribute("reviewEntity", reviewEntity);
        model.addAttribute("reviewDto", reviewDto);
        return "review-place";
    }

    @PostMapping("/review-place")
    public String processReviewPlace(@Valid @ModelAttribute("reviewDto") ReviewDto reviewDto, BindingResult result, Model model, HttpSession session, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("reviewDto", reviewDto);
            return "review-place";
        }
        ReviewEntity reviewEntity = (ReviewEntity) session.getAttribute("reviewEntity");
        PlaceEntity placeEntity = (PlaceEntity) session.getAttribute("placeEntity");
        if (reviewEntity != null) {
            reviewService.updateReview(reviewEntity, reviewDto);
            placeEntity = (PlaceEntity) session.getAttribute("placeEntity");
        } else {
            UserEntity userEntity = userService.findUserByEmail(authentication.getName());
            reviewService.saveReview(reviewDto, userEntity, placeEntity);
        }
        return "redirect:/";
    }

    @GetMapping("/place-details")
    public ResponseEntity<?> getPlaceDetails(@RequestParam String placeId) {
        PlaceEntity placeEntity = placeService.findPlaceById(placeId);
        String starMean = noteService.calculateStarMean(placeEntity);
        List<ReviewEntity> reviews = reviewService.findAllReviewsByPlace(placeEntity);
        List<EquipmentEntity> equipments = equipmentService.findAllEquipmentsByPlace(placeEntity);
        String apiKey = "AIzaSyDW2AIOAR3VKJrNnOYt_IKGVk2aVg_uld4";
        String url = "https://maps.googleapis.com/maps/api/place/details/json"
                + "?place_id=" + placeId
                + "&key=" + apiKey;
        WebClient webClient = WebClient.create();
        String responseBody = webClient.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        PlaceDto placeDto = new PlaceDto();
        placeDto.setStarMean(starMean);
        List<ReviewDto> reviewDtoS = new ArrayList<>();
        for (ReviewEntity reviewEntity : reviews) {
            ReviewDto reviewDto = new ReviewDto();
            reviewDto.setReview(reviewEntity.getReview());
            reviewDto.setName(reviewEntity.getUserEntity().getEmail());
            reviewDtoS.add(reviewDto);
        }
        placeDto.setReviews(reviewDtoS);
        List<EquipmentDto> equipmentDtoS = new ArrayList<>();
        for (EquipmentEntity equipmentEntity : equipments) {
            EquipmentDto equipmentDto = new EquipmentDto();
            equipmentDto.setType(equipmentEntity.getType());
            equipmentDto.setStatus(equipmentEntity.getStatus());
            equipmentDtoS.add(equipmentDto);
        }
        placeDto.setEquipments(equipmentDtoS);
        placeDto.setGoogleDetails(responseBody);
        return ResponseEntity.ok(placeDto);
    }

    @GetMapping("/add-equipment/{id}")
    public String addEquipment(@PathVariable("id") String id, Model model, HttpSession session) {
        PlaceEntity placeEntity = placeService.findPlaceById(id);
        if (placeEntity == null) {
            placeEntity = placeService.savePlace(id);
        }
        EquipmentDto equipmentDto = new EquipmentDto();
        session.setAttribute("placeEntity", placeEntity);
        model.addAttribute("equipmentDto", equipmentDto);
        return "add-equipment";
    }

    @PostMapping("/add-equipment")
    public String processAddEquipment(@Valid @ModelAttribute("equipmentDto") EquipmentDto equipmentDto, BindingResult result, Model model, HttpSession session, Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("equipmentDto", equipmentDto);
            return "add-equipment";
        }
        PlaceEntity placeEntity = (PlaceEntity) session.getAttribute("placeEntity");
        UserEntity userEntity = userService.findUserByEmail(authentication.getName());
        equipmentService.savEquipment(equipmentDto, userEntity, placeEntity);
        return "redirect:/";
    }

}
