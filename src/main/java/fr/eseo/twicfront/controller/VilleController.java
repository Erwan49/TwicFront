package fr.eseo.twicfront.controller;

import fr.eseo.twicfront.dto.FormDistanceVille;
import fr.eseo.twicfront.model.Ville;
import fr.eseo.twicfront.service.VilleService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@Data
public class VilleController {

    private final VilleService villeService;

    private String lastDistance = "";

    public VilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping()
    public String villes(Model model) throws URISyntaxException, IOException, InterruptedException {
        List<Ville> villes = villeService.getVilles();
        model.addAttribute("villes", villes);
        return "villesPage";
    }

    @GetMapping("distanceVilles")
    public String getDistanceVille(Model model) throws URISyntaxException, IOException, InterruptedException {
        List<Ville> villes = villeService.getVilles();
        FormDistanceVille form = new FormDistanceVille("", "");
        model.addAttribute("form", form);
        model.addAttribute("villes", villes);
        model.addAttribute("distance", lastDistance);
        return "distanceVille";
    }

    @PostMapping("test")
    public String test(@ModelAttribute("form") FormDistanceVille form, Model model) throws URISyntaxException, IOException,
            InterruptedException {
        Ville ville1 = villeService.getVilleByName(form.getNomVille1());
        Ville ville2 = villeService.getVilleByName(form.getNomVille2());
        int distance = (int) villeService.getDistanceVilles(ville1.getLongitude(), ville1.getLatitude(), ville2.getLongitude(),
                ville2.getLatitude());
        lastDistance = String.valueOf(distance);
        return "redirect:/distanceVilles";
    }

}
