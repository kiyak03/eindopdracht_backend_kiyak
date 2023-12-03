package com.kiyak.eindopdracht_backend_kiyak.service;

import com.kiyak.eindopdracht_backend_kiyak.domain.Feedback;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface FeedbackService {

    List<Feedback> getAllFeedback();

    Feedback getFeedbackById(long id);

    void deleteFeedback(long id);

//    long saveFeedback(String feedback, Long demoId, Principal principal) throws NotFoundException;

    ResponseEntity<String> saveFeedback(Long demoId, String feedback, Principal principal);

    void updateFeedback(long id, String comment);
}
