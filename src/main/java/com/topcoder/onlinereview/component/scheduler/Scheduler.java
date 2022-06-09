package com.topcoder.onlinereview.component.scheduler;


import com.topcoder.onlinereview.component.executable.AsynchronousExecutorHandle;
import com.topcoder.onlinereview.component.executable.Exec;
import com.topcoder.onlinereview.component.executable.ExecutionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(Scheduler.class);
    public static final int JOB_TYPE_EXTERNAL = 0;
    public static final int JOB_TYPE_JAVA_CLASS = 1;
    private List schedule;
    private List execution;
    private boolean isRunning = false;
    private String file;
    private Timer timer;
    private final int timerInterval = 1000;
    private int scheduleIdx;
    private int executionIdx;
    private boolean updateConfig = true;
    private boolean checkSchedule;
    private static String YEAR = "YEAR";
    private static String MONTH = "MONTH";
    private static String DATE = "DATE";
    private static String WEEK_OF_YEAR = "WEEK_OF_YEAR";
    private static String HOUR = "HOUR";
    private static String MINUTE = "MINUTE";
    private static String SECOND = "SECOND";
    private static String START_DTG = "StartDTG";
    private static String END_DTG = "EndDTG";
    private static String INTERVAL_VALUE = "IntervalValue";
    private static String INTERVAL_UNIT = "IntervalUnit";
    private static String JOB_TYPE = "JobType";
    private static String JOB_CMD = "JobCmd";
    private static String INTERNAL_JOB = "JOB_TYPE_JAVA_CLASS";
    private static String EXTERNAL_JOB = "JOB_TYPE_EXTERNAL";

    public Scheduler(String file) {
        this.file = file;
        this.schedule = new ArrayList();
        this.execution = new ArrayList();
    }

    public void start() {
        if (!this.isRunning) {
            this.isRunning = true;
            this.schedule.clear();
            try {
                this.initSchedule();
            } catch (Exception var2) {
                this.dumpError(var2);
            } catch (JobActionException var3) {
            }

            this.scheduleIdx = 0;
            this.executionIdx = 0;
            this.checkSchedule = true;
            this.timer = new Timer();
            this.timer.schedule(new Scheduler.ScheduleTask(), 0L, 1000L);
        }

    }

    public void stop() {
        if (this.isRunning) {
            this.timer.cancel();
            this.isRunning = false;
            ListIterator iter = this.execution.listIterator();

            while(iter.hasNext()) {
                Scheduler.ExecutingJob execJob = (Scheduler.ExecutingJob)iter.next();
                this.logJob(execJob, (String)null);
                execJob.stop();
            }

            this.schedule.clear();
            this.execution.clear();
        }

    }

    public void shutdown() {
        this.stop();
    }

    public void addJob(Job job) throws JobActionException {
        if (job != null && this.isValidJob(job)) {
            if (this.schedule.contains(job)) {
                throw new JobActionException("The same job name exists.");
            } else {
                if (job.getStop() == null || job.getStop().after(new GregorianCalendar())) {
                    this.computeNextRun(job);
                    job.setStatus("Idle");
                    this.schedule.add(new Job(job));
                }

                this.updateConfigFile((Job)null, job);
            }
        } else {
            throw new JobActionException("Invalid job.");
        }
    }

    public void replaceJob(Job oldJob, Job newJob) throws JobActionException {
        if (newJob != null && this.isValidJob(newJob)) {
            if (!this.schedule.contains(oldJob)) {
                throw new JobActionException("The old job can not be found.");
            } else if (this.schedule.contains(newJob) && !oldJob.equals(newJob)) {
                throw new JobActionException("The same job name exists.");
            } else {
                this.schedule.remove(oldJob);
                if (newJob.getStop() == null || newJob.getStop().after(new GregorianCalendar())) {
                    this.computeNextRun(newJob);
                    this.schedule.add(new Job(newJob));
                }

                this.updateConfigFile(oldJob, newJob);
            }
        } else {
            throw new JobActionException("Invalid job.");
        }
    }

    public void deleteJob(Job job) throws JobActionException {
        if (this.schedule.contains(job)) {
            this.schedule.remove(job);
            this.updateConfigFile(job, (Job)null);
        } else {
            throw new JobActionException("The job can not be found");
        }
    }

    public List getJobList() {
        return this.schedule;
    }

    public List getJobExecutionList() {
        return this.execution;
    }

    public boolean isSchedulerRunning() {
        return this.isRunning;
    }

    public void stopJob(Job job) {
        int idx = this.execution.indexOf(job);
        if (idx >= 0) {
            Scheduler.ExecutingJob execJob = (Scheduler.ExecutingJob)this.execution.get(idx);
            int pos = this.schedule.indexOf(execJob);
            if (pos != -1) {
                ((Job)this.schedule.get(pos)).setStatus("Idle");
            }

            this.logJob(execJob, (String)null);
            execJob.stop();
            this.execution.remove(execJob);
        }

    }

    private void dumpError(Exception e) {
        try {
            PrintWriter out = new PrintWriter(new FileWriter("error.txt", true));
            out.println(e.toString());
            out.close();
        } catch (IOException var3) {
            System.out.println("Can't write to error.txt!");
        }

    }

    private void updateConfigFile(Job oldJob, Job newJob) {
//        if (this.updateConfig) {
//            try {
//                ConfigManager cm = (ConfigManager)this.cmHandle;
//                Enumeration enumProp = cm.getPropertyNames(this.namespace);
//                DateFormat df = DateFormat.getDateTimeInstance(1, 2, Locale.US);
//                cm.createTemporaryProperties(this.namespace);
//
//                while(true) {
//                    String key;
//                    while(enumProp.hasMoreElements()) {
//                        key = (String)enumProp.nextElement();
//                        String[] values = cm.getStringArray(this.namespace, key);
//                        if (oldJob != null && oldJob.getName().equals(key)) {
//                            cm.setProperty(this.namespace, key, "");
//                        } else {
//                            cm.setProperty(this.namespace, key, values);
//                        }
//                    }
//
//                    if (newJob != null) {
//                        key = newJob.getName();
//                        String jobType = newJob.getJobType() == 0 ? EXTERNAL_JOB : INTERNAL_JOB;
//                        String intervalUnit = this.unitToString(newJob.getIntervalUnit());
//                        String endDTG = newJob.getStop() == null ? "" : df.format(newJob.getStop().getTime());
//                        String[] values = new String[]{START_DTG + ":" + df.format(newJob.getStart().getTime()), END_DTG + ":" + endDTG, INTERVAL_VALUE + ":" + newJob.getIntervalValue(), INTERVAL_UNIT + ":" + intervalUnit, JOB_TYPE + ":" + jobType, JOB_CMD + ":" + newJob.getRunCommand()};
//                        cm.setProperty(this.namespace, key, values);
//                    }
//
//                    cm.commit(this.namespace, "com.topcoder.util.scheduler");
//                    return;
//                }
//            } catch (Exception var11) {
//                throw new RuntimeException(var11.getMessage());
//            }
//        }
    }

    private int stringToUnit(String s) {
        if (s.equalsIgnoreCase(YEAR)) {
            return 1;
        } else if (s.equalsIgnoreCase(MONTH)) {
            return 2;
        } else if (s.equalsIgnoreCase(DATE)) {
            return 5;
        } else if (s.equalsIgnoreCase(WEEK_OF_YEAR)) {
            return 3;
        } else if (s.equalsIgnoreCase(HOUR)) {
            return 10;
        } else if (s.equalsIgnoreCase(MINUTE)) {
            return 12;
        } else {
            return s.equalsIgnoreCase(SECOND) ? 13 : -1;
        }
    }

    private String unitToString(int unit) {
        switch(unit) {
            case 1:
                return YEAR;
            case 2:
                return MONTH;
            case 3:
                return WEEK_OF_YEAR;
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 11:
            default:
                return null;
            case 5:
                return DATE;
            case 10:
                return HOUR;
            case 12:
                return MINUTE;
            case 13:
                return SECOND;
        }
    }

    private void computeNextRun(Job job) {
        GregorianCalendar rightNow = new GregorianCalendar();
        GregorianCalendar nextRun = null;
        if (job.getNextRun() == null) {
            nextRun = (GregorianCalendar)job.getStart().clone();
            if (nextRun.before(rightNow) && (job.getStop() == null || nextRun.before(job.getStop()))) {
                long now = rightNow.getTime().getTime();
                long start = nextRun.getTime().getTime();
                long interval = 0L;
                nextRun.add(job.getIntervalUnit(), job.getIntervalValue());
                interval = nextRun.getTime().getTime() - start;
                nextRun.add(job.getIntervalUnit(), -job.getIntervalValue());
                int num = (int)((now - start) / interval);
                nextRun.add(job.getIntervalUnit(), num * job.getIntervalValue());
            }
        } else {
            nextRun = job.getNextRun();
        }

        while(nextRun.before(rightNow) && (job.getStop() == null || nextRun.before(job.getStop()))) {
            nextRun.add(job.getIntervalUnit(), job.getIntervalValue());
        }

        job.setNextRun(nextRun);
    }

    private void initSchedule() throws JobActionException {
        DateFormat df = DateFormat.getDateTimeInstance(1, 2, Locale.US);
        this.updateConfig = false;
            try {
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(file));
                doc.getDocumentElement().normalize();
                NodeList nodes = doc.getDocumentElement().getChildNodes();
                for (int i = 0; i < nodes.getLength(); i++) {
                    if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                        GregorianCalendar startDTG = new GregorianCalendar();
                        GregorianCalendar endDTG = new GregorianCalendar();
                        int intervalValue = -1;
                        int intervalUnit = -1;
                        int jobType = -1;
                        String jobCmd = null;
                        Element element = (Element) nodes.item(i);
                        String key = element.getAttribute("name");
                        NodeList values = element.getChildNodes();
                        for(int j = 0; j < values.getLength(); j++) {
                            if (values.item(j).getNodeType() == Node.ELEMENT_NODE) {
                                String[] p = values.item(j).getFirstChild().getNodeValue().split(":", 2);
                                String name = p[0];
                                String val = p[1];
                                try {
                                    if (name.equalsIgnoreCase(START_DTG)) {
                                        startDTG.setTime(df.parse(val));
                                    } else if (name.equalsIgnoreCase(END_DTG)) {
                                        if ("".equals(val)) {
                                            endDTG = null;
                                        } else {
                                            endDTG.setTime(df.parse(val));
                                        }
                                    } else if (name.equalsIgnoreCase(INTERVAL_VALUE)) {
                                        intervalValue = Integer.parseInt(val);
                                    } else if (name.equalsIgnoreCase(INTERVAL_UNIT)) {
                                        intervalUnit = this.stringToUnit(val);
                                    } else if (name.equalsIgnoreCase(JOB_TYPE)) {
                                        if (val.equalsIgnoreCase(INTERNAL_JOB)) {
                                            jobType = 1;
                                        } else {
                                            jobType = 0;
                                        }
                                    } else if (name.equalsIgnoreCase(JOB_CMD)) {
                                        jobCmd = val;
                                    }
                                } catch (ParseException var16) {
                                    throw new JobActionException("Invalid job.");
                                }
                            }
                        }
                        this.addJob(new Job(key, startDTG, endDTG, intervalValue, intervalUnit, jobType, jobCmd));
                    }
                }
            } catch (Exception e) {
                this.dumpError(e);
            }
        this.updateConfig = true;
    }

    private void logJob(Scheduler.ExecutingJob execJob, String launch) {
        GregorianCalendar rightNow = new GregorianCalendar();
        if (launch != null) {
            log.info(execJob.getName() + ": " + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": " + launch + execJob.getStatus());
        } else if (execJob.isDone()) {
            log.info(execJob.getName() + ": " + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": " + "Terminated normally: " + execJob.getStatus());
        } else {
            log.info(execJob.getName() + ": " + execJob.getLastRun().getTime() + ": " + rightNow.getTime() + ": " + "Terminated abnormally: " + execJob.getStatus());
        }
    }

    private boolean isValidIntervalUnit(int intervalUnit) {
        return intervalUnit == 1 || intervalUnit == 2 || intervalUnit == 5 || intervalUnit == 3 || intervalUnit == 10 || intervalUnit == 12 || intervalUnit == 13;
    }

    private boolean isValidJob(Job job) {
        return job.getStart() != null && !job.getStart().after(job.getStop()) && job.getName() != null && !job.getName().equals("") && job.getIntervalValue() > 0 && this.isValidIntervalUnit(job.getIntervalUnit()) && (job.getJobType() == 0 || job.getJobType() == 1);
    }

    private class ScheduleTask extends TimerTask {
        private ScheduleTask() {
        }

        public void run() {
            if (Scheduler.this.checkSchedule) {
                if (Scheduler.this.scheduleIdx >= Scheduler.this.schedule.size()) {
                    Scheduler.this.scheduleIdx = 0;
                    Scheduler.this.checkSchedule = false;
                } else {
                    GregorianCalendar rightNow = new GregorianCalendar();
                    Job job = (Job)Scheduler.this.schedule.get(Scheduler.this.scheduleIdx);
                    if ((job.getStop() == null || job.getStop().after(rightNow)) && job.getNextRun().before(rightNow)) {
                        job.setLastRun(rightNow);
                        Scheduler.this.computeNextRun(job);
                        Object execJobx;
                        if (job.getJobType() == 1) {
                            execJobx = Scheduler.this.new InternalJob(job);
                        } else {
                            execJobx = Scheduler.this.new ExternalJob(job);
                        }

                        try {
                            ((Scheduler.ExecutingJob)execJobx).start();
                            Scheduler.this.execution.add(execJobx);
                            job.setStatus("Running");
                        } catch (Exception var5) {
                            Scheduler.this.logJob((Scheduler.ExecutingJob)execJobx, "Launch failed");
                        }
                    }

                    Scheduler.this.scheduleIdx++;
                }
            } else if (Scheduler.this.executionIdx >= Scheduler.this.execution.size()) {
                Scheduler.this.executionIdx = 0;
                Scheduler.this.checkSchedule = true;
            } else {
                Scheduler.ExecutingJob execJob = (Scheduler.ExecutingJob)Scheduler.this.execution.get(Scheduler.this.executionIdx);
                if (execJob.isDone()) {
                    int idx = Scheduler.this.schedule.indexOf(execJob);
                    if (idx != -1) {
                        Job jobx = (Job)Scheduler.this.schedule.get(idx);
                        jobx.setStatus("Idle");
                    }

                    Scheduler.this.execution.remove(Scheduler.this.executionIdx);
                } else {
                    Scheduler.this.executionIdx++;
                }
            }

        }
    }

    protected abstract class ExecutingJob extends Job {
        public abstract String getStatus();

        public abstract boolean isDone();

        public abstract void stop();

        public abstract void start();

        protected ExecutingJob(Job job) {
            super(job);
        }
    }

    protected class InternalJob extends Scheduler.ExecutingJob {
        private Object job = null;

        protected InternalJob(Job job) {
            super(job);
        }

        public String getStatus() {
            return this.job != null ? ((Schedulable)this.job).getStatus() : null;
        }

        public boolean isDone() {
            return ((Schedulable)this.job).isDone();
        }

        public void stop() {
            ((Schedulable)this.job).close();
        }

        public void start() {
            try {
                Class classDefinition = Class.forName(this.getRunCommand());
                this.job = classDefinition.newInstance();
                (new Thread((Runnable)this.job)).start();
            } catch (Exception var2) {
                throw new RuntimeException(var2.getMessage());
            }
        }
    }

    protected class ExternalJob extends Scheduler.ExecutingJob {
        private AsynchronousExecutorHandle job = null;

        protected ExternalJob(Job job) {
            super(job);
        }

        public String getStatus() {
            if (this.job == null) {
                return null;
            } else if (this.isDone()) {
                try {
                    ExecutionResult result = this.job.getExecutionResult();
                    return result.getExitStatus() != 0 ? "Complete with error" : "Complete without error";
                } catch (IllegalStateException var2) {
                    return "Complete with error";
                }
            } else {
                return "Not finished";
            }
        }

        public boolean isDone() {
            return this.job.isDone();
        }

        public void stop() {
            this.job.halt();
        }

        public void start() {
            try {
                this.job = Exec.executeAsynchronously(new String[]{this.getRunCommand()});
            } catch (Exception var2) {
                throw new RuntimeException(var2.getMessage());
            }
        }
    }
}
