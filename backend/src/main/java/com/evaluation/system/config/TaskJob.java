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

    @Autowired
    GroupSuggestionRepository groupSuggestionRepository;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String[] key = jobDataMap.getKeys();
        //获取要进行统分的评分表id
        int kid = Integer.parseInt(jobDataMap.getString(key[0]));
        System.out.println("正在统分....");
        statEvaluationOuter(kid);
        statEvaluationInner(kid);
        System.out.println("任务结束...");
    }

    /**
     * 统计组内评分表，获取个人评分
     * @param kid
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
     * @param kid 获取评分表ID
     */
    @Transactional
    @SuppressWarnings("all")
    public void statEvaluationOuter(int kid){

        try {
            EvaluationOuter e = evaluationOuterRepository.findOneByEvaluationOuterId(kid);
            List<SubmitOuter> subList = submitOuterRepository.findByEvaluationOuterId(e.getEvaluationOuterId());
            int groupNum = e.getClassInfo().getGroupNum();

            //用于保存提交了评分表的小组的id
            List<Integer> subGroup = new ArrayList<>();

            // 用于判断有没有缺交
            int flag = 0;

            //只有在评分表总数大于1的时候才能进行统计，否则会出现有组没有分数的时候
            if (subList.size() <= 1) {
                System.out.println("当前提交的互评表无法进行统计");
                return;
            }

            //总数大于1小于小组总数的时候，要记录提交了
            else if(subList.size() > 1 && subList.size() < groupNum)
            {

                for(SubmitOuter submitOuter : subList)
                {
                    subGroup.add(submitOuter.getGroupId());
                }
            }

            //如果交的数量等于小组数量，就说明没有缺交情况，flag置为1
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
                    String suggestion = (String)tableRaw.get(tableRaw.size() - 1);
                    if(!statis.containsKey(groupId)){
                        statis.put(groupId,0);
                    }
                    statis.put(groupId,statis.get(groupId) + score);
                    GroupSuggestion groupSuggestion = new GroupSuggestion();
                    groupSuggestion.setEvaluationOuterId(e.getEvaluationOuterId());
                    groupSuggestion.setGroupId(groupId);
                    groupSuggestion.setSuggestion(suggestion);
                    groupSuggestionRepository.save(groupSuggestion);
                }
            }

            for(Integer keyId:statis.keySet()){
                GroupScore groupScore = groupScoreRepository.findOneByIdAndGourp(kid,keyId);
                //检测数据库是否已经存在该组的分数
                if(groupScore == null)
                {
                    groupScore = new GroupScore();
                }
                groupScore.setGroupId(keyId);
                //全部组都提交了表的情况
                if(flag == 1)
                {
                    Double score = statis.get(keyId).doubleValue() / (groupNum - 1);
                    score = Double.valueOf(String.format("%.5f",score));
                    groupScore.setContent(score);
                }
                //有部分组没有提交的情况，提交的组的算分逻辑
                else if(subGroup.contains(keyId))
                {
                    Double score = statis.get(keyId).doubleValue() / (subList.size() - 1);
                    score = Double.valueOf(String.format("%.5f",score));
                    groupScore.setContent(score);
                }
                //未提交组的算分逻辑
                else
                {
                    Double score = statis.get(keyId).doubleValue() / subList.size();
                    score = Double.valueOf(String.format("%.5f",score));
                    groupScore.setContent(score);
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