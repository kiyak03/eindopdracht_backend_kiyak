package com.kiyak.eindopdracht_backend_kiyak.service;


import com.kiyak.eindopdracht_backend_kiyak.domain.Feedback;
import com.kiyak.eindopdracht_backend_kiyak.domain.Demo;
import com.kiyak.eindopdracht_backend_kiyak.domain.User;
import com.kiyak.eindopdracht_backend_kiyak.exception.NotFoundException;
import com.kiyak.eindopdracht_backend_kiyak.repository.FeedbackRepository;
import com.kiyak.eindopdracht_backend_kiyak.repository.DemoRepository;
import com.kiyak.eindopdracht_backend_kiyak.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    private DemoRepository demoRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @Override
    public List<Feedback> getFeedbackByDemoId(long demoId) {
        return feedbackRepository.findByDemoId(demoId);
    }

    @Override
    public Feedback getFeedbackById(long id) {
        if (feedbackRepository.existsById(id)) {
            return feedbackRepository.findById(id).orElse(null);
        } else {
            throw new NotFoundException();
        }
    }

    @Override
    public void deleteFeedback(long id) {
            if(feedbackRepository.existsById(id)){
                feedbackRepository.deleteById(id);
            }
            else {
                throw new NotFoundException();
            }
        }

//    @Override
//    public ResponseEntity<String> saveFeedback(String feedback, Principal principal) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        Long currentUserId = userDetails.getId();
//
//        Optional<Demo> optionalDemo = demoRepository.findByUserId(currentUserId);
//
//        if (optionalDemo.isPresent()) {
//            Demo demo = optionalDemo.get();
//
//            long currentUserId = ((UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal()).getId();
//            Optional<User> optionalUser = userRepository.findById(currentUserId);
//
//            Feedback newFeedback = new Feedback();
//            newFeedback.setComment(feedback);
//            newFeedback.setUser(optionalUser.get());
//
//            // Associate the feedback with the demo
//            demo.addFeedback(newFeedback);
//
//            // Save the updated demo with feedback
//            demoRepository.save(demo);
//
//            return ResponseEntity.ok("Feedback submitted successfully!");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demo not found with ID: ");
//        }
//    }

//    @Override
//    public ResponseEntity<String> saveFeedback(String feedback, Principal principal) {
//        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
//        Long currentUserId = userDetails.getId();
//
//        Optional<Demo> optionalDemo = demoRepository.findById(currentUserId);
//
//        if (optionalDemo.isPresent()) {
//            Demo demo = optionalDemo.get();
//
//            Optional<User> optionalUser = userRepository.findById(currentUserId);
//
//            Feedback newFeedback = new Feedback();
//            newFeedback.setComment(feedback);
//            newFeedback.setUser(optionalUser.get());
//
//            // Associate the feedback with the demo
//            demo.addFeedback(newFeedback);
//
//            // Save the updated demo with feedback
//            demoRepository.save(demo);
//
//            return ResponseEntity.ok("Feedback submitted successfully!");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demo not found for the current user");
//        }
//    }

    public ResponseEntity<String> saveFeedback(Long demoId, String feedback, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        Long currentUserId = userDetails.getId();

        Optional<Demo> optionalDemo = demoRepository.findById(demoId);

        if (optionalDemo.isPresent()) {
            Demo demo = optionalDemo.get();

            Optional<User> optionalUser = userRepository.findById(currentUserId);

            Feedback newFeedback = new Feedback();
            newFeedback.setComment(feedback);
            newFeedback.setUser(optionalUser.get());

            // Associate the feedback with the demo
            demo.addFeedback(newFeedback);

            // Save the updated demo with feedback
            demoRepository.save(demo);

            return ResponseEntity.ok("Feedback submitted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Demo not found for the given demoId");
        }
    }



//
//    @Override
//    public long saveFeedback(String feedback,Long demoId) {
//        Demo demo = demoRepository.findById(demoId)
//                .orElseThrow(() -> new NotFoundException());
//        Feedback newFeedback = new Feedback();
//        newFeedback.setFeedback(feedback); // Assuming there's a setter for feedback in your Feedback class
//        newFeedback.setDemo(demo);
////        Feedback savedFeedback = commentRepository.save(newFeedback);
//        Feedback savedFeedback = feedbackRepository.save(newFeedback);
//        return savedFeedback.getId();
//    }

//    @Override
//    public long saveFeedback(String feedback) {
//        Feedback newFeedback = commentRepository.save(comment);
//        return newFeedback.getId();
//    }


    @Override
    public void updateFeedback(long id, String feedback) {
        Optional<Feedback> commentOptional = feedbackRepository.findById(id);

        if (commentOptional.isPresent()) {
            Feedback comment = commentOptional.get();
            comment.setFeedback(feedback);
            feedbackRepository.save(comment);
        } else {
            throw new NotFoundException();
        }
    }

//    @Override
//    public List<Feedback> getAllFeedbackForDemoId(long demoId) {
//        return feedbackRepository.findByDemoId(demoId);
//    }


//    @Override
//    public void updateFeedback(long id, Feedback comment) {
//        if (commentRepository.existsById(id)) {
//            try {
//                Feedback existingFeedback = commentRepository.findById(id).orElse(null);
//                existingFeedback.setFeedback(comment.getFeedback());
//                commentRepository.save(existingFeedback);
//            }
//            catch (Exception ex) {
//                throw new DemoStorageException();
//            }
//        }
//        else {
//            throw new NotFoundException();
//        }
//    }
}
