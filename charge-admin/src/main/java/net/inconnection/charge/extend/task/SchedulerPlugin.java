package net.inconnection.charge.extend.task;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.IPlugin;
import it.sauronsoftware.cron4j.Scheduler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SchedulerPlugin implements IPlugin {
    private static Log LOG = Log.getLog("SchedulerPlugin");
    private final Scheduler cronScheduler;
    private final ScheduledThreadPoolExecutor taskScheduler;
    private final String jobConfigFile;

    public SchedulerPlugin() {
        this(getBestPoolSize(), (String)null);
    }

    public SchedulerPlugin(int scheduledThreadPoolSize) {
        this(scheduledThreadPoolSize, (String)null);
    }

    public SchedulerPlugin(String jobConfigFile) {
        this(getBestPoolSize(), jobConfigFile);
    }

    public SchedulerPlugin(int scheduledThreadPoolSize, String jobConfigFile) {
        this.cronScheduler = new Scheduler();
        this.jobConfigFile = jobConfigFile;
        this.taskScheduler = new ScheduledThreadPoolExecutor(scheduledThreadPoolSize);
    }

    public void cronSchedule(Runnable task, String cronExpression) {
        this.cronScheduler.schedule(cronExpression, task);
    }

    public ScheduledFuture<?> fixedRateSchedule(Runnable task, int periodSeconds) {
        return this.taskScheduler.scheduleAtFixedRate(task, 0L, (long)periodSeconds, TimeUnit.SECONDS);
    }

    public ScheduledFuture<?> fixedDelaySchedule(Runnable task, int periodSeconds) {
        return this.taskScheduler.scheduleWithFixedDelay(task, 0L, (long)periodSeconds, TimeUnit.SECONDS);
    }

    public boolean start() {
        if (this.jobConfigFile != null) {
            this.loadJobsFromConfigFile();
        }

        this.cronScheduler.setDaemon(true);
        this.cronScheduler.start();
        LOG.info("SchedulerPlugin is started");
        return true;
    }

    public boolean stop() {
        this.cronScheduler.stop();
        this.taskScheduler.shutdown();
        LOG.info("SchedulerPlugin is stopped");
        return true;
    }

    private void loadJobsFromConfigFile() {
        Prop jobProp = PropKit.use(this.jobConfigFile);
        Set<String> jobNames = this.getJobNamesFromProp(jobProp);
        Iterator var4 = jobNames.iterator();

        while(var4.hasNext()) {
            String jobName = (String)var4.next();
            this.loadJob(jobProp, jobName);
        }

    }

    private void loadJob(Prop jobProp, String jobName) {
        Boolean enable = jobProp.getBoolean(jobName + ".enable", Boolean.TRUE);
        if (enable) {
            Runnable task = this.createTask(jobName, jobProp.get(jobName + ".class"));
            String taskType = jobProp.get(jobName + ".type");
            if (StrKit.isBlank(taskType)) {
                throw new RuntimeException("Please set " + jobName + ".type");
            } else {
                String expr = jobProp.get(jobName + ".expr");
                if (StrKit.isBlank(expr)) {
                    throw new RuntimeException("Please set " + jobName + ".expr");
                } else {
                    this.scheduleJobByType(jobName, taskType, expr, task);
                    LOG.info("--------load job: " + jobName + " successfully--------");
                    LOG.info("class: " + jobProp.get(jobName + ".class"));
                    LOG.info("type : " + taskType);
                    LOG.info("expr : " + expr);
                    LOG.info("----------------");
                }
            }
        }
    }

    private void scheduleJobByType(String jobName, String taskType, String expr, Runnable task) {
        if ("cron".equals(taskType)) {
            this.cronSchedule(task, expr);
        } else {
            boolean var5;
            int periodSeconds;
            if ("fixedRate".equals(taskType)) {
                var5 = false;

                try {
                    periodSeconds = Integer.parseInt(expr);
                } catch (NumberFormatException var8) {
                    throw new RuntimeException(jobName + ".expr must be a number");
                }

                this.fixedRateSchedule(task, periodSeconds);
            } else {
                if (!"fixedDelay".equals(taskType)) {
                    throw new RuntimeException("Please set " + jobName + ".type to cron/fixedRate/fixedDelay");
                }

                var5 = false;

                try {
                    periodSeconds = Integer.parseInt(expr);
                } catch (NumberFormatException var7) {
                    throw new RuntimeException(jobName + ".expr must be a number");
                }

                this.fixedDelaySchedule(task, periodSeconds);
            }
        }

    }

    private Runnable createTask(String jobName, String taskClassName) {
        if (taskClassName == null) {
            throw new RuntimeException("Please set " + jobName + ".className");
        } else {
            Object temp = null;

            try {
                temp = Class.forName(taskClassName).newInstance();
            } catch (Exception var5) {
                throw new RuntimeException("Can not create instance of class: " + taskClassName, var5);
            }

            Runnable task = null;
            if (temp instanceof Runnable) {
                task = (Runnable)temp;
                return task;
            } else {
                throw new RuntimeException("Can not create instance of class: " + taskClassName + ". this class must implements Runnable interface");
            }
        }
    }

    private Set<String> getJobNamesFromProp(Prop jobProp) {
        Map<String, Boolean> jobNames = new HashMap();
        Iterator var4 = jobProp.getProperties().keySet().iterator();

        while(var4.hasNext()) {
            Object item = var4.next();
            String fullKeyName = item.toString();
            String jobName = fullKeyName.substring(0, fullKeyName.indexOf("."));
            jobNames.put(jobName, Boolean.TRUE);
        }

        return jobNames.keySet();
    }

    private static int getBestPoolSize() {
        try {
            int cores = Runtime.getRuntime().availableProcessors();
            return cores * 8;
        } catch (Throwable var1) {
            return 8;
        }
    }
}

