package com.krrishdholakia.community_reporting.application.Problem_Voting;

/**
 * Created by community_reporting on 14/10/16.
 */
public class God_Data {

    String problem;

    Integer vote;

    public God_Data(){

    }

    public God_Data(String problem, Integer vote) {
        this.problem = problem;
        this.vote = vote;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Integer getVote() {
        return vote;
    }

    public void setVote(Integer Vote) {
        this.vote = vote;
    }
}
