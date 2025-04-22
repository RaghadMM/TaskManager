package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.entities.Review;
import com.exaltTraining.taskManagerProject.entities.User;

public interface ReviewService {
    String addReview(Review review, int taskId, User reviewer);
    String markATaskAsChecked( int taskId, User reviewer);

}
