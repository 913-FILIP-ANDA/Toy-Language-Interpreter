package Model.Statements;

import Exceptions.InterpreterException;
import Model.ADTs.MyDictionaryInterface;
import Model.ADTs.MyStackInterface;
import Model.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements Statement{
    Statement firstStatement;
    Statement secondStatement;

    public CompoundStatement(Statement firstStatement, Statement secondStatement){
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }
    public ProgramState execute(ProgramState state) throws InterpreterException {
        MyStackInterface<Statement> stack = state.getExecutionStack();
        stack.push(this.secondStatement);
        stack.push(this.firstStatement);
        //state.setExecutionStack(stack);
        return null;
    }

    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> variableTypes) throws InterpreterException {
        return secondStatement.typeCheck(firstStatement.typeCheck(variableTypes));
    }

    public String toString(){
        return this.firstStatement.toString() + " ; " + this.secondStatement.toString();
    }
}