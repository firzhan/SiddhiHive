package org.wso2.carbon.siddhihive.utils;

import org.wso2.siddhi.query.api.condition.*;
import org.wso2.siddhi.query.api.expression.Expression;
import org.wso2.siddhi.query.api.expression.Variable;
import org.wso2.siddhi.query.api.expression.constant.Constant;
import org.wso2.siddhi.query.api.expression.constant.IntConstant;

/**
 * Created by Firzhan on 6/2/14.
 */
public class ConditionHandler {

    public ConditionHandler() {

    }

    public String processCondition(Condition condition) {

        String handleCondition = " ";

        if (condition == null)
            return " ";

        if (condition instanceof Compare) {
            handleCondition += handleCompareCondition((Compare) condition);
        } else if (condition instanceof AndCondition) {
            String leftCondition = processCondition(((AndCondition) condition).getLeftCondition());
            String rightCondition = processCondition(((AndCondition) condition).getRightCondition());
            handleCondition += leftCondition + Constants.AND + rightCondition;
        } else if (condition instanceof BooleanCondition) {
            //do
        } else if (condition instanceof InCondition) {
            //do
        } else if (condition instanceof NotCondition) {
            //do
        } else if (condition instanceof OrCondition) {
            String leftCondition = processCondition(((OrCondition) condition).getLeftCondition());
            String rightCondition = processCondition(((OrCondition) condition).getRightCondition());
            handleCondition += leftCondition + Constants.OR + rightCondition;
        }

        return handleCondition;

    }

    private String handleCompareCondition(Compare compare) {

        String leftExpressiveValue = handleCompareExpression(compare.getLeftExpression());
        String rightExpressiveValue = handleCompareExpression(compare.getRightExpression());


        String operatorString = getOperator(compare.getOperator());

        return " " + leftExpressiveValue + "  " + operatorString + "  " + rightExpressiveValue;

    }


    private String handleCompareExpression(Expression expression) {

        String expressionValue = " ";

        if (expression instanceof Variable) {
            expressionValue = handleVariable((Variable) expression);
        } else if (expression instanceof Constant) {

            if (expression instanceof IntConstant) {

                IntConstant intConstant = (IntConstant) expression;
                expressionValue = intConstant.getValue().toString();
            }
        }

        return expressionValue;
    }

    private String handleVariable(Variable variable) {
        return (variable.getStreamId() != null ? (variable.getStreamId() + ".") : "") + variable.getAttributeName();
    }

    private String getOperator(Condition.Operator operator) {


        if (operator == Condition.Operator.EQUAL)
            return " = ";
        else if (operator == Condition.Operator.GREATER_THAN)
            return " > ";
        else if (operator == Condition.Operator.GREATER_THAN_EQUAL)
            return " >= ";
        else if (operator == Condition.Operator.LESS_THAN)
            return " < ";
        else if (operator == Condition.Operator.LESS_THAN_EQUAL)
            return " <= ";
        else if (operator == Condition.Operator.CONTAINS)
            return " CONTAINS ";
//        else if(operator == Condition.Operator.INSTANCE_OF)
//            return " = ";


        return " ";
    }
}
