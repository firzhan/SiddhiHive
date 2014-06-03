package org.wso2.carbon.siddhihive.headerprocessor;


import org.wso2.carbon.siddhihive.handler.ConditionHandler;
import org.wso2.carbon.siddhihive.utils.Constants;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.input.Stream;
import org.wso2.siddhi.query.api.query.input.WindowStream;
import org.wso2.siddhi.query.api.query.input.handler.Filter;

import java.util.HashMap;
import java.util.Map;

public class WindowStreamHandler implements StreamHandler {

    private String windowIsolatorClause;
    private String fromClause;
    private String whereClause;
    private WindowStream windowStream;
    private WindowIsolator windowIsolator;
    private Map<String, String> result;

    public WindowStreamHandler() {
        this.windowIsolator = new WindowIsolator();
    }


    public String generateFromClause(String streamId) {
        String clause = Constants.FROM + " " + streamId;
        return clause;
    }

    @Override
    public Map<String, String> process(Stream stream, StreamDefinition streamDefinition) {
        this.windowStream = (WindowStream) stream;
        windowIsolatorClause = windowIsolator.process(windowStream.getWindow(), streamDefinition);
        fromClause = generateFromClause(windowStream.getStreamId());
        whereClause = generateWhereClause(windowStream.getFilter());
        result = new HashMap<String, String>();
        result.put(Constants.FROM_CLAUSE, fromClause);
        result.put(Constants.WHERE_CLAUSE, whereClause);
        result.put(Constants.INCREMENTAL_CLAUSE, windowIsolatorClause);
        return result;
    }

    private String generateWhereClause(Filter filter) {
        ConditionHandler conditionHandler = new ConditionHandler();
        String filterStr = conditionHandler.processCondition(filter.getFilterCondition());
        return Constants.WHERE + " " + filterStr;

    }
}
