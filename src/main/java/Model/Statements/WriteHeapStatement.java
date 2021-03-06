package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.MyDictionaryInterface;
import Model.Expressions.Expression;
import Model.ProgramState;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class WriteHeapStatement implements Statement {
    String variableName;
    Expression expression;

    public WriteHeapStatement(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    public ProgramState execute(ProgramState state) throws InterpreterException {
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();

        if(! symbolTable.containsKey(variableName))
            throw new InterpreterException("Variable " + variableName + " is not defined!");

        Value variableValue = symbolTable.getElementWithKey(variableName);
        if(! (variableValue.getType() instanceof ReferenceType))
            throw new InterpreterException("Variable " + variableName + " is not of Refrence Type!");

        var referenceValue = (ReferenceValue) variableValue;
        if(! heap.containsKey(referenceValue.getAddress()))
            throw new InterpreterException("Address does not exist in the heap!");

        Value evaluatedExpression = expression.evaluate(symbolTable, heap);
        if(! evaluatedExpression.getType().equals(referenceValue.getLocationType()))
            throw new InterpreterException("Expression " + expression.toString() + " was not evaluated to " + referenceValue.getLocationType().toString());

        heap.update(referenceValue.getAddress(), evaluatedExpression);

        return null;
    }

    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> variableTypes) throws InterpreterException {
        var variableType = variableTypes.getElementWithKey(variableName);
        var expressionType = expression.typeCheck(variableTypes);

        if(!variableType.equals(new ReferenceType(expressionType)))
            throw new InterpreterException("Typechecker - Variable " + variableName + " and expression: " + expression.toString() + " have different types!");

        return variableTypes;
    }

    @Override
    public String toString() {
        return "writeHeap(" + variableName + ", " + expression.toString() + ")";
    }
}

