package com.fernandez.fixtures.service;

import com.fernandez.fixtures.output.ResultsIds;
import org.springframework.context.ApplicationEvent;

public class ResultsIdsSavedEvent extends ApplicationEvent {
    private final ResultsIds resultsIds;
    public ResultsIdsSavedEvent(ResultsIds resultsIds) {
        super(resultsIds);
        this.resultsIds = resultsIds;
    }

    public ResultsIds getResultsIds() {
        return resultsIds;
    }
}

