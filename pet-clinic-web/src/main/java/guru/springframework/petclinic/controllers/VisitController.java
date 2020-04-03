package guru.springframework.petclinic.controllers;

import guru.springframework.petclinic.model.Visit;
import guru.springframework.petclinic.services.PetService;
import guru.springframework.petclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.beans.PropertyEditorSupport;
import java.time.LocalDate;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void dataBinder(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");

        dataBinder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException{
                setValue(LocalDate.parse(text));
            }
        });
    }

    @GetMapping("/new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Model model) {
        model.addAttribute("visit", new Visit());
        model.addAttribute("pet", petService.findById(petId));
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/new")
    public String processNewVisitForm(@Valid Visit visit, @PathVariable("petId") Long petId, BindingResult result) {
        if (result.hasErrors()) {
            return "pets/createOrUpdateVisitForm";
        } else {
            visit.setPet(petService.findById(petId));
            visitService.save(visit);

            return "redirect:/owners/{ownerId}";
        }
    }
}
