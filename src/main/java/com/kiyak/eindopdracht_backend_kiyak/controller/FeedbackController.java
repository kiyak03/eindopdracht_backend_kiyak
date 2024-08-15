package com.kiyak.eindopdracht_backend_kiyak.controller;

import com.kiyak.eindopdracht_backend_kiyak.domain.Feedback;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import com.kiyak.eindopdracht_backend_kiyak.service.FeedbackService;
import com.kiyak.eindopdracht_backend_kiyak.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;
    DemoService demoService;



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<Object> getAllFeedback() {
        List<Feedback> feedbacks = feedbackService.getAllFeedback();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/demo/{demoId}")
    public ResponseEntity<Object> getFeedbackByDemoId(@PathVariable("demoId") long demoId) {
        try {
            List<Feedback> feedbackList = feedbackService.getFeedbackByDemoId(demoId);
            return new ResponseEntity<>(feedbackList, HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>("Feedback not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while fetching feedback", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @PreAuthorize("hasRole('USER')or hasRole('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getFeedbackById(@PathVariable("id") long id) {
        try {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return new ResponseEntity<>(feedback, HttpStatus.OK);
        }catch (NotFoundException e) {
            return new ResponseEntity<>("Feedback not found", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error occurred while fetching feedback", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteFeedback(@PathVariable("id") long id) {
        feedbackService.deleteFeedback(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<Object> saveFeedback(
            @RequestParam("comment") String comment,
            @RequestParam("feedback") String feedback,
            @RequestParam("demoId") Long demoId,
            Principal principal) {
        try {
            ResponseEntity<String> newId = feedbackService.saveFeedback(demoId, comment, feedback, principal);
            System.out.println("feedback: " + newId + principal + feedback + comment);
            return new ResponseEntity<>(newId, HttpStatus.CREATED);
        } catch (NotFoundException e){
            return new ResponseEntity<>("Demo not found with ID: ", HttpStatus.NOT_FOUND);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateFeedback(@PathVariable("id") long id, @RequestParam("feedback") String comment)  {
        feedbackService.updateFeedback(id, comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
