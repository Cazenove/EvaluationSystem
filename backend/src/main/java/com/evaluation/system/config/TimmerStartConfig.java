package com.evaluation.system.config;

import com.evaluation.system.bean.EvaluationOuter;
import com.evaluation.system.dao.EvaluationOuterRepository;
import com.evaluation.system.dao.SubmitOuterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimmerStartConfig implements ApplicationRunner {

    @Autowired
    PublishTimedTask timedTask;

    @Autowired
    EvaluationOuterRepository evaluationOuterRepository;

    @Autowired
    SubmitOuterRepository submitOuterRepository;

    @Autowired
    TaskController taskController;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<EvaluationOuter> list = evaluationOuterRepository.findAll();
        for(EvaluationOuter evaluationOuter:list)
        {
            long endTime = Long.parseLong(evaluationOuter.getEndTime()) * 1000;
            Integer id = evaluationOuter.getEvaluationOuterId();
            if(System.currentTimeMillis() < endTime)
            {
                timedTask.publishTask(taskController.getCronString(endTime),System.currentTimeMillis(),endTime,
                        id.toString(),id.toString());
            }
        }

    }
}
