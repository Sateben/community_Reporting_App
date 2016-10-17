package com.krrishdholakia.community_reporting.application.Problem_Voting;

/**
 * Created by community_reporting on 14/10/16.
 */
public class Numerical_Data_Input {

    Integer Vote;

    public Numerical_Data_Input(){

    }

    public Numerical_Data_Input(Integer vote) {
        this.Vote = vote;
    }

    public Integer getVote() {

        return Vote;
    }

    public void setVote(Integer vote) {

        this.Vote = vote;
    }
}
