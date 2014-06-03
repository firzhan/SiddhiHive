package org.wso2.carbon.siddhihive.selectorprocessor;

/**
 * Created by Firzhan on 5/30/14.
 */

import org.wso2.carbon.siddhihive.handler.AttributeHandler;
import org.wso2.carbon.siddhihive.handler.ConditionHandler;
import org.wso2.carbon.siddhihive.utils.Constants;
import org.wso2.siddhi.query.api.condition.AndCondition;
import org.wso2.siddhi.query.api.condition.Condition;
import org.wso2.siddhi.query.api.condition.OrCondition;
import org.wso2.siddhi.query.api.expression.Variable;
import org.wso2.siddhi.query.api.query.Query;
import org.wso2.siddhi.query.api.query.selection.Selector;
import org.wso2.siddhi.query.api.query.selection.attribute.ComplexAttribute;
import org.wso2.siddhi.query.api.query.selection.attribute.OutputAttribute;
import org.wso2.siddhi.query.api.query.selection.attribute.OutputAttributeExtension;
import org.wso2.siddhi.query.api.query.selection.attribute.SimpleAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QuerySelectorProcessor {

    private ConditionHandler conditionHandler = null;
    private AttributeHandler attributeHandler = null;

    public QuerySelectorProcessor(){

        conditionHandler = new ConditionHandler();
        attributeHandler = new AttributeHandler();
    }

    public Map<String, String> handleSelector(Query query){

        if(query == null)
           return  null;

        Selector selector = query.getSelector();

        String selectionQuery = handleSelectionList(selector);
        String groupByQuery =   handleGroupByList(selector);
        String handle = handleHavingCondition(selector);

        Map<String, String> selectorQueryMap = new HashMap<String, String>();
        selectorQueryMap.put("selectionQuery", selectionQuery);
        selectorQueryMap.put("groupByQuery", groupByQuery);
        selectorQueryMap.put("havingQuery", handle);

        return selectorQueryMap;
    }

    private String handleSelectionList(Selector selector){

        List<OutputAttribute> selectionList =  selector.getSelectionList();

        int selectionListSize = selectionList.size();

        if(selectionListSize == 0)
            return " ";

        String selectionString = " ";

        for(int i=0; i < selectionListSize; i++){

            OutputAttribute outputAttribute = selectionList.get(i);

            if ( outputAttribute instanceof SimpleAttribute){
                selectionString += attributeHandler.handleSimpleAttribute((SimpleAttribute) outputAttribute);
            }
            else if(outputAttribute instanceof ComplexAttribute){
                selectionString += attributeHandler.handleComplexAttribute((ComplexAttribute) outputAttribute);
            }
            else if(outputAttribute instanceof OutputAttributeExtension){

            }

            if( (selectionListSize > 1 ) && ( (i+1) < selectionListSize) )
                selectionString += " , ";
        }

        return  selectionString;
    }

    private String handleGroupByList(Selector selector){

        String groupBy = " GROUP BY ";
        List<Variable> groupByList = selector.getGroupByList();

        int groupByListSize = groupByList.size();

        if(groupByListSize == 0)
            return " ";

        for(int i=0; i < groupByListSize; i++){

            Variable variable = groupByList.get(i);

            groupBy += "  " + conditionHandler.handleVariable(variable);

            if( (groupByListSize > 1 ) && ( (i+1) < groupByListSize) )
                groupBy += " , ";


        }

        return groupBy;
    }

    private String handleHavingCondition(Selector selector) {

        String handleCondition = "  ";
        Condition condition = selector.getHavingCondition();

        if(condition == null )
            return " ";

        handleCondition = conditionHandler.processCondition(condition);

        if ((condition instanceof OrCondition) || (condition instanceof AndCondition))
            handleCondition = Constants.OPENING_BRACT + Constants.SPACE + handleCondition + Constants.SPACE + Constants.CLOSING_BRACT;

        handleCondition = Constants.HAVING + handleCondition;

        return handleCondition;
    }

//    private String handleSimpleAttribute(SimpleAttribute simpleAttribute){
//
//        String expressionValue = "";
//
//        String rename = simpleAttribute.getRename();
//
//        Expression expression = simpleAttribute.getExpression();
//
//        if(expression instanceof Variable){
//
//            boolean multipleAttr = false;
//
//            if(expressionValue.trim().equalsIgnoreCase("") == false)
//                multipleAttr = true;
//
//            Variable variable = (Variable)expression;
//            expressionValue += conditionHandler.handleVariable(variable);
//
//            if(variable.getAttributeName().equals(rename) == false){
//                expressionValue += " AS " + rename;
//            }
//
//            if(multipleAttr)
//                expressionValue += ","  ;
//        }
//
//
//        return expressionValue;
//    }

//    private String handleComplexAttribute(ComplexAttribute complexAttribute){
//
//        String expressionValue = "";
//        String rename = complexAttribute.getRename();
//        String complexAttrName = complexAttribute.getAttributeName();
//
//        Expression[] expressions = complexAttribute.getExpressions();
//
//        int expressionLength = expressions.length;
//
//        for(int i=0; i < expressionLength; i++){
//
//            Expression expression = expressions[i];
//
//            if(expression instanceof Variable){
//
//                boolean multipleAttr = false;
//
//                if(expressionValue.trim().equalsIgnoreCase("") == false)
//                    multipleAttr = true;
//
//
//                Variable variable = (Variable)expression;
//                expressionValue +=  " "+ complexAttrName + "( " + conditionHandler.handleVariable(variable) + " )";
//
//                if(variable.getAttributeName().equals(rename) == false){
//                    expressionValue += " AS " + rename;
//
//                    if(multipleAttr)
//                        expressionValue += ","  ;
//                }
//            }
//
//        }
//
//        return expressionValue;
//    }


//    private String handleCompareCondition(Compare compare){
//
//        String leftExpressiveValue = conditionHandler.handleCompareExpression(compare.getLeftExpression());
//        String rightExpressiveValue = conditionHandler.handleCompareExpression(compare.getRightExpression());
//
//
//        String operatorString = conditionHandler.getOperator(compare.getOperator());
//
//        return " "+leftExpressiveValue+"  "+ operatorString + "  " + rightExpressiveValue;
//
//    }

//    private String handleCompareExpression(Expression expression){
//
//        String expressionValue = " ";
//
//        if (expression instanceof  Variable){
//            expressionValue = conditionHandler.handleVariable((Variable)expression);
//        }
//        else if(expression instanceof Constant){
//
//            if(expression instanceof IntConstant){
//
//                IntConstant intConstant = (IntConstant) expression;
//                expressionValue = intConstant.getValue().toString();
//            }
//        }
//
//        return expressionValue;
//    }


//    private String handleVariable(Variable variable){
//        return (variable.getStreamId() != null ? (variable.getStreamId()+"."):"") + variable.getAttributeName();
//    }

//    private String getOperator(Condition.Operator operator){
//
//
//        if(operator == Condition.Operator.EQUAL)
//            return " = ";
//        else if(operator == Condition.Operator.GREATER_THAN)
//            return " > ";
//        else if(operator == Condition.Operator.GREATER_THAN_EQUAL)
//            return " >= ";
//        else if(operator == Condition.Operator.LESS_THAN)
//            return " < ";
//        else if(operator == Condition.Operator.LESS_THAN_EQUAL)
//            return " <= ";
////        else if(operator == Condition.Operator.CONTAINS)
////            return " CONTAINS ";
////        else if(operator == Condition.Operator.INSTANCE_OF)
////            return " = ";
//
//
//        return " ";
//    }
}
