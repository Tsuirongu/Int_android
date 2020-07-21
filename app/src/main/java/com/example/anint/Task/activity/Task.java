package com.example.anint.Task.activity;

public class Task {
    private String taskName;
    private String tasktype;
    private int imageId;
    private int money;
    private String taskInfo;


    public Task(String taskName,String tasktype,int imageAddress,int money){
        this.imageId=imageAddress;
        this.taskName=taskName;
        this.tasktype=tasktype;
        this.money=money;
    }

    public Task(String taskName,String tasktype,int imageAddress,int money,String taskInfo){
        this.imageId=imageAddress;
        this.taskName=taskName;
        this.tasktype=tasktype;
        this.money=money;
        this.taskInfo=taskInfo;
    }

    String getTaskName(){
        return taskName;
    }
    public String getTasktype()
    {
        return tasktype;
    }

    public void setTaskName(String taskName){
        this.taskName=taskName;
    }

    public void setImageAddress(int imageAddress){
        this.imageId=imageAddress;
    }

    public void setTasktype(String tasktype){
        this.tasktype=tasktype;
    }

    public int getImageId() {
        return imageId;
    }

    public int getmoney(){
        return money;
    }

    String getTaskInfo(){return taskInfo;}
}
