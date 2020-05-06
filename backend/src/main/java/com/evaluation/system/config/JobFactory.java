package com.evaluation.system.config;

import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * @author: ak
 * @date: 2020/5/6 19:00
 * @description:
 */
@Configuration
public class JobFactory extends SpringBeanJobFactory {

    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Autowired
    private JobFactory jobFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        beanFactory.autowireBean(jobInstance);
        return jobInstance;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}
