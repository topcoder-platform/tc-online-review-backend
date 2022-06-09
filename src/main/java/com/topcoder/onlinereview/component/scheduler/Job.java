package com.topcoder.onlinereview.component.scheduler;


import java.util.GregorianCalendar;

public class Job {
    private GregorianCalendar start;
    private String name;
    private GregorianCalendar stop;
    private int intervalValue;
    private int intervalUnit;
    private int jobType;
    private String runCommand;
    private GregorianCalendar lastRun;
    private GregorianCalendar nextRun;
    private String status;

    public GregorianCalendar getStart() {
        return this.start;
    }

    protected void setStart(GregorianCalendar start) {
        this.start = start;
    }

    public String getName() {
        return this.name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public GregorianCalendar getStop() {
        return this.stop;
    }

    protected void setStop(GregorianCalendar stop) {
        this.stop = stop;
    }

    public int getIntervalValue() {
        return this.intervalValue;
    }

    protected void setIntervalValue(int intervalValue) {
        this.intervalValue = intervalValue;
    }

    public int getIntervalUnit() {
        return this.intervalUnit;
    }

    protected void setIntervalUnit(int intervalUnit) {
        this.intervalUnit = intervalUnit;
    }

    public int getJobType() {
        return this.jobType;
    }

    protected void setJobType(int jobType) {
        this.jobType = jobType;
    }

    public String getRunCommand() {
        return this.runCommand;
    }

    protected void setRunCommand(String runCommand) {
        this.runCommand = runCommand;
    }

    public GregorianCalendar getLastRun() {
        return this.lastRun;
    }

    protected void setLastRun(GregorianCalendar lastRun) {
        this.lastRun = lastRun;
    }

    public GregorianCalendar getNextRun() {
        return this.nextRun;
    }

    protected void setNextRun(GregorianCalendar nextRun) {
        this.nextRun = nextRun;
    }

    public String getStatus() {
        return this.status;
    }

    protected void setStatus(String status) {
        this.status = status;
    }

    public Job(Job job) {
        this.setName(job.getName());
        this.setStart(job.getStart());
        this.setStop(job.getStop());
        this.setIntervalValue(job.getIntervalValue());
        this.setIntervalUnit(job.getIntervalUnit());
        this.setJobType(job.getJobType());
        this.setRunCommand(job.getRunCommand());
        this.setNextRun(job.getNextRun());
        this.setLastRun(job.getLastRun());
        this.setStatus(job.getStatus());
    }

    public Job(String jobName, GregorianCalendar start, GregorianCalendar stop, int intervalValue, int intervalUnit, int jobType, String runCommand) {
        this.setName(jobName);
        this.setStart(start);
        this.setStop(stop);
        this.setIntervalValue(intervalValue);
        this.setIntervalUnit(intervalUnit);
        this.setJobType(jobType);
        this.setRunCommand(runCommand);
        this.setNextRun((GregorianCalendar)null);
        this.setLastRun((GregorianCalendar)null);
        this.setStatus((String)null);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            return obj instanceof Job ? ((Job)obj).getName().equals(this.getName()) : false;
        }
    }

    public int hashCode() {
        return this.getName().hashCode();
    }
}
