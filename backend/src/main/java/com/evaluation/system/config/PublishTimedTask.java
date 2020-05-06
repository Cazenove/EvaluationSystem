package com.evaluation.system.config;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: ak
 * @date: 2020/5/5 10:08
 * @description:
 */

@Service
public class PublishTimedTask {
    /**
     * 存储任务key和data的map
     */
    private final Map<String, String> tableDataMap = new HashMap<>();
    /**
     * 任务管理类
     */

    @Autowired
    Scheduler scheduler;

    /**
     * 发布任务
     *
     * @param cron      任务周期
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public void publishTask(String cron, long startTime, long endTime, String tableDataKey, String tableData) throws SchedulerException {
        try {
            setTableDataMap("trigger-" + tableDataKey, tableData);
            Scheduler scheduler = this.scheduler;

            //创建JobDetail实例，并与PrintWordsJob类绑定(Job执行内容)
            JobDetail jobDetail = JobBuilder.newJob(TaskJob.class)
                    .withIdentity("job-" + tableDataKey, "group-" + tableDataKey).build();
            //指定周期性定时任务
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger-" + tableDataKey, "triggerGroup-" + tableDataKey)
                    .usingJobData(tableDataKey, tableData)
                    .startNow()//立即生效
                    .startAt(new Date(startTime))
                    .endAt(new Date(endTime))
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();

            //执行
            scheduler.scheduleJob(jobDetail, cronTrigger);
            System.out.println("--------定时任务发布成功 ! ------------");
            System.out.println(cronTrigger.getJobDataMap().get(tableDataKey));
            System.out.println("当前任务key：" + tableDataKey);
            scheduler.start();

        } catch (SchedulerException e) {
            throw e;
        }
    }

    /**
     * 获取发布的任务的信息
     */
    public Object getTask(String triggerKey) throws SchedulerException {
        Scheduler scheduler = this.scheduler;
        Trigger trigger = scheduler.getTrigger(new TriggerKey("trigger-" + triggerKey, "triggerGroup-" + triggerKey));
        if (trigger == null) {
            HashMap<Object, Object> map = new HashMap<>(16);
            map.put("msg", "该任务已经结束");
            map.put("status", 400);
            return map;
        }
        return trigger.getJobDataMap().get(triggerKey);
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    /**
     * 修改任务时间
     *
     * @param key     任务的表示符key
     * @param newTime 新修改的时间
     * @param data    新数据
     */
    public Object changeTaskTime(String key, long newTime, String data) throws SchedulerException {
        HashMap<Object, Object> map = new HashMap<>();
        String tableData;
        if (data != null) {
            tableData = data;
        } else {
            tableData = getTableDataMap().get("trigger-" +key);
        }
        TriggerKey triggerKey = new TriggerKey("trigger-" + key, "triggerGroup-" + key);
        if (this.scheduler.getTrigger(triggerKey) == null) {
            map.put("msg", "任务不存在或者已经结束");
            map.put("status", 400);
            return map;
        }
        String cronString = new TaskController().getCronString(newTime - 1000);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + key, "triggerGroup-" + key)
                .usingJobData(key, tableData)
                .startNow()//立即生效
                .startAt(new Date(System.currentTimeMillis()))
                .endAt(new Date(newTime))
                .withSchedule(CronScheduleBuilder.cronSchedule(cronString))
                .build();
        this.scheduler.rescheduleJob(triggerKey, cronTrigger);
        System.out.println("--------定时任务修改成功 ! ------------");
        System.out.println("当前任务key：" + key);
        map.put("msg", "任务" + key + "修改成功");
        map.put("status", 200);
        return map;
    }

    public Object deleteTaskTime(String key) throws SchedulerException {
        HashMap<Object, Object> map = new HashMap<>();
        String tableData;
        TriggerKey triggerKey = new TriggerKey("trigger-" + key, "triggerGroup-" + key);
        if (this.scheduler.getTrigger(triggerKey) == null) {
            map.put("msg", "任务不存在或者已经结束");
            map.put("status", 400);
            return map;
        }
        this.scheduler.unscheduleJob(triggerKey);
        System.out.println("--------定时任务删除成功 ! ------------");
        System.out.println("当前任务key：" + key);
        map.put("msg", "任务" + key + "删除成功");
        map.put("status", 200);
        return map;
    }

    public Map<String, String> getTableDataMap() {
        return tableDataMap;
    }

    public void setTableDataMap(String key, String json) {
        this.tableDataMap.put(key, json);
    }
}

@RestController
class TaskController {
    @Autowired
    PublishTimedTask publishTimedTask;

    /**
     * 发布任务
     *
     * @param endTime        结束时间(数据格式需要是时间戳)
     * @param taskJsonString 任务详细信息的json字符串
     * @param taskKey        任务唯一指定的key标识
     */
    @GetMapping("publishTask")
    public Object publishTask(Long endTime, String taskJsonString, String taskKey) throws SchedulerException {
        HashMap<Object, Object> map = new HashMap<>(16);
        if (taskJsonString == null || taskKey == null) {
            map.put("msg", "参数存在空值");
            map.put("status", 400);
        }
        //开始时间，无需指定
        long startTime = System.currentTimeMillis();
        TaskController taskController = new TaskController();
        //提前1秒去完成统分
        String cronString = taskController.getCronString(endTime * 1000 - 1000);

        PublishTimedTask taskManager = new PublishTimedTask();
        taskManager.publishTask(cronString, startTime, endTime * 1000, taskKey, taskJsonString);
        map.put("msg", "发布成功");
        map.put("status", 200);
        return map;
    }

    /**
     * 获取还未截至的任务
     *
     * @param taskKey 任务唯一标识key
     */
    @GetMapping("getTask")
    public Object getTask(String taskKey) throws SchedulerException {
        HashMap<Object, Object> map = new HashMap<>(16);
        if (taskKey == null) {
            map.put("msg", "请输入任务指定表示符key");
            map.put("status", 400);
            return map;
        }
        return publishTimedTask.getTask(taskKey);
    }

    /**
     * 修改还未截至的任务
     *
     * @param taskKey 任务唯一标识key
     * @param newTime 修改后的新时间（传入的数据需要是时间戳）
     */
    @GetMapping("changeTaskTime")
    public Object changeTaskTime(String taskKey, Long newTime, String data) throws SchedulerException {
        HashMap<Object, Object> map = new HashMap<>(16);
        if (taskKey == null || newTime == null) {
            map.put("msg", "参数存在空值");
            map.put("status", 400);
            return map;
        }
        //如果需要更新数据，则传入新的json字符串，否则传空值
        return publishTimedTask.changeTaskTime(taskKey, newTime * 1000, data);
    }


    /**
     * 传入时间戳，转换成定时任务字符串，定时任务将在结束时间前一秒完成
     *
     * @param timeStamp 结束时间的时间戳
     * @return 对应的定时任务cron
     */
    public String getCronString(long timeStamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(sdf.format(new Date()));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = simpleDateFormat.format(new Date(timeStamp));
        String[] s = sd.split(" ");
        String[] timePrefix = s[0].split("-");
        String[] timeSuffix = s[1].split(":");
        return timeSuffix[2] + " " + timeSuffix[1] + " " + timeSuffix[0] + " " +
                timePrefix[2] + " " + timePrefix[1] + " ? *";
    }
}
