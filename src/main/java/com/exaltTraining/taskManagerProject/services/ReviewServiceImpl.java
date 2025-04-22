package com.exaltTraining.taskManagerProject.services;

import com.exaltTraining.taskManagerProject.dao.ReviewRepository;
import com.exaltTraining.taskManagerProject.dao.TaskRepository;
import com.exaltTraining.taskManagerProject.entities.Review;
import com.exaltTraining.taskManagerProject.entities.Task;
import com.exaltTraining.taskManagerProject.entities.User;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewRepository reviewRepository;
    private TaskRepository taskRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, TaskRepository taskRepository) {
        this.reviewRepository = reviewRepository;
        this.taskRepository = taskRepository;
    }

    //Add a review to a task
    //The task status must be DONE, the reviewer department must be of type QA
    //The task department must be SOFTWARE type
    @Override
    public String addReview(Review review, int taskId, User reviewer) {

        try{
            Task task = taskRepository.findById(taskId).get();
            if(task.getStatus() != Task.Status.DONE) {
                return "The task status do not allow adding reviews right now";

            }
            else {
                task.setStatus(Task.Status.REVIEWED);
                review.setTask(task);
                review.setReviewer(reviewer);
                reviewRepository.save(review);
                return "Review added";
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return "Task Not Found";
        }
    }

    //Mark a task as checked by QA employee
    //The task status must be done
    @Override
    public String markATaskAsChecked(int taskId, User reviewer) {
        Task task = taskRepository.findById(taskId).get();
        if(task.getStatus() != Task.Status.DONE) {
            return "The task status do not allow marking it as checked right now";
        }
        task.setStatus(Task.Status.CHECKED);
        taskRepository.save(task);
        return "Task Marked";
    }
}
