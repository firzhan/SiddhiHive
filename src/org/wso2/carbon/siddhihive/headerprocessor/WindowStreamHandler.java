package org.wso2.carbon.siddhihive.headerprocessor;


import org.wso2.carbon.siddhihive.utils.Constants;
import org.wso2.siddhi.query.api.definition.StreamDefinition;
import org.wso2.siddhi.query.api.query.input.Stream;
import org.wso2.siddhi.query.api.query.input.WindowStream;
import org.wso2.siddhi.query.api.query.input.handler.Filter;

public class WindowStreamHandler implements StreamHandler {

    private String windowIsolatorClause;
    private String fromClause;
    private String whereClause;
    private WindowStream windowStream;
    private WindowIsolator windowIsolator;

    public WindowStreamHandler() {
        this.windowIsolator = new WindowIsolator();
    }


    public String generateFromClause(String streamId) {
        String clause = Constants.FROM + " " + streamId;
        return null;
    }

    @Override
    public void process(Stream stream, StreamDefinition streamDefinition) {
        this.windowStream = (WindowStream) stream;
        windowIsolatorClause = windowIsolator.process(windowStream.getWindow(), streamDefinition);
        fromClause = generateFromClause(windowStream.getStreamId());
        whereClause = generateWhereClause(windowStream.getFilter());
    }

    private String generateWhereClause(Filter filter) {
        //TODO
        return null;

    }
}
