package com.evaluation.system.config;

import com.evaluation.system.bean.*;
import com.evaluation.system.dao.*;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author: ak
 * @date: 2020/5/5 10:10
 * @description:
 */

@Component
public class TaskJob implements Job {

    @Autowired
    PublishTimedTask timedTask;

    @Autowired
    EvaluationOuterRepository evaluationOuterRepository;

    @Autowired
    SubmitOuterRepository submitOuterRepository;

    @Autowired
    GroupScoreRepository groupScoreRepository;

    @Autowired
    EvaluationInnerRepository evaluationInnerRepository;

    @Autowired
    SubmitInnerRepository submitInnerRepository;

    @Autowired
    PersonScoreRepository personScoreRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String[] key = jobDataMap.getKeys();
        int kid = Integer.parseInt(jobDataMap.getString(key[0]));
        System.out.println("正在统分....");
        statEvaluationOuter(kid);
        statEvaluationInner(kid);
        System.out.println("任务结束...");
    }

    /**
     * 统计组内评分表，获取个人评分
     * @param jobExecutionContext
     */
    @Transactional
    @SuppressWarnings("all")
    public void statEvaluationInner(int kid)
    {
        try{
            EvaluationInner e = evaluationInnerRepository.findOneByEvaluationInnerId(kid);
            List<SubmitInner> subList = submitInnerRepository.findByEvaluationInnerId(kid);

            for (SubmitInner submitInner : subList){
                Map<String,Object> content= submitInner.getContent();
                List<List> tableData= (List)content.get("tableData");

                for(List tableRaw:tableData){
                    String userId = (String)tableRaw.get(0);
                    String decision = (String)tableRaw.get(2);
                    int score = (int)tableRaw.get(tableRaw.size() - 1);
                    PersonScore personScore = personScoreRepository.findOneByIdAndUserId(kid,userId);
                    if(personScore == null)
                    {
                        personScore = new PersonScore();
                    }
                    personScore.setGroupId(submitInner.getGroupId());
                    personScore.setContent(score);
                    personScore.setEvaluationInnerId(kid);
                    personScore.setUserId(userId);
                    personScore.setDecision(decision);
                    personScoreRepository.save(personScore);
                }
            }
        }
        catch (Exception e)
        {
            throw e;
        }


    }

    /**
     * 统计组间小组评分
     * @param jobExecutionContext 获取评分表ID
     */
    @Transactional
    @SuppressWarnings("all")
    public void statEvaluationOuter(int kid){

        try {
            EvaluationOuter e = evaluationOuterRepository.findOneByEvaluationOuterId(kid);
            List<SubmitOuter> subList = submitOuterRepository.findByEvaluationOuterId(e.getEvaluationOuterId());
            int groupNum = e.getClassInfo().getGroupNum();
            List<Integer> subGroup = new ArrayList<>();
            // 用于判断有没有缺交
            int flag = 0;
            if (subList.size() <= 1) {
                System.out.println("当前提交的互评表无法进行统计");
                return;
            }


            else if(subList.size() > 1 && subList.size() < groupNum)
            {

                for(SubmitOuter submitOuter : subList)
                {
                    subGroup.add(submitOuter.getGroupId());
                }
            }

            else if (subList.size() == groupNum)
            {
                flag = 1;
            }

            Map<Integer, Integer> statis = new HashMap<>(16);

            for (SubmitOuter submitOuter : subList){
                Map<String,Object> content= submitOuter.getContent();
                List<List> tableData= (List)content.get("tableData");

                for(List tableRaw:tableData){
                    int groupId = (int)tableRaw.get(0);
                    int score = (int)tableRaw.get(tableRaw.size() - 2);
                    if(!statis.containsKey(groupId)){
                        statis.put(groupId,0);
                    }
                    statis.put(groupId,statis.get(groupId) + score);
                }
            }

            for(Integer keyId:statis.keySet()){
                GroupScore groupScore = groupScoreRepository.findOneByIdAndGourp(kid,keyId);
                if(groupScore == null)
                {
                    groupScore = new GroupScore();
                }
                groupScore.setGroupId(keyId);
                if(flag == 1)
                {
                    groupScore.setContent(statis.get(keyId) / (groupNum - 1));
                }
                else if(subGroup.contains(keyId))
                {
                    groupScore.setContent(statis.get(keyId) / (subList.size() - 1));
                }
                else
                {
                    groupScore.setContent(statis.get(keyId) / subList.size());
                }
                groupScore.setEvaluationOuterId(kid);
                groupScoreRepository.save(groupScore);
            }


        }

        catch (Exception e){
            throw e;
        }

    }
}