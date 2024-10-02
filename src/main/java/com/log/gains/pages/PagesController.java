package com.log.gains.pages;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.log.gains.pages.PageResponses.ComparePageResponse;
import com.log.gains.pages.PageResponses.HomePageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v1/pages")
@RequiredArgsConstructor
public class PagesController {
    
    private final PagesService pagesService;

    @GetMapping("/homePage")
    public HomePageResponse homePageResponse () {
       return pagesService.construcHomePageResponse(); 
    } 
    @GetMapping("/comparePage")
    public ComparePageResponse comparePageResponse(@RequestParam String date1, @RequestParam String date2) {

        return new ComparePageResponse(null, null, date1, null, null, date2);
    }
}
