package sg.edu.nus.iss.day22giphy.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.day22giphy.services.GiphyService;

@Controller
@RequestMapping("/giphy")
public class GiphyController {

    @Autowired
    public GiphyService gSvc;

    @GetMapping("/search")
    public String getGiphy(@RequestParam MultiValueMap<String, String> form, Model model) throws IOException{
        String q = form.getFirst("phrase");
        int limit = Integer.parseInt(form.getFirst("limit"));
        String rating = form.getFirst("rating");

        model.addAttribute("q", q);
        model.addAttribute("limit", limit);
        model.addAttribute("rating", rating);

        List<String> imgUrls = gSvc.getSearchResults(q, limit, rating);

        model.addAttribute("imgUrls", imgUrls); 
        
        return "searchresults";
    }
}
