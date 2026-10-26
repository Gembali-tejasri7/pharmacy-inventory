
package pharmacy_inventory;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
public class MedicineController {

    private final MedicineRepository medicineRepository;

    public MedicineController(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("medicine", new Medicine());
        model.addAttribute("medicines", medicineRepository.findAll());
        model.addAttribute("today", LocalDate.now());
        model.addAttribute("warningDate", LocalDate.now().plusDays(30));
        return "index";
    }

    @PostMapping("/medicines/save")
    public String saveMedicine(
            @Valid @ModelAttribute("medicine") Medicine medicine,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.addAttribute("medicines", medicineRepository.findAll());
            return "index";
        }

        medicineRepository.save(medicine);
        redirectAttributes.addFlashAttribute(
                "message", "Medicine saved successfully!");

        return "redirect:/";
    }

    @GetMapping("/medicines/edit/{id}")
    public String editMedicine(@PathVariable Long id, Model model) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid medicine ID: " + id));

        model.addAttribute("medicine", medicine);
        model.addAttribute("medicines", medicineRepository.findAll());
        model.addAttribute("today", java.time.LocalDate.now());
        model.addAttribute("warningDate",
            java.time.LocalDate.now().plusDays(30));

        return "index";
    }

    @GetMapping("/medicines/delete/{id}")
    public String deleteMedicine(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        medicineRepository.deleteById(id);
        redirectAttributes.addFlashAttribute(
                "message", "Medicine deleted successfully!");

        return "redirect:/";
    }
}
