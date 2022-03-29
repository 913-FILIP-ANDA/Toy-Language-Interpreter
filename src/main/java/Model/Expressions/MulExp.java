package Model.Expressions;


import Exceptions.InterpreterException;
import Model.ADTs.MyDictionaryInterface;
import Model.ADTs.MyHeapInterface;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.Value;

public class MulExp implements Expression{
    Expression exp1;
    Expression exp2;

    public MulExp(Expression exp1, Expression exp2) {
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    @Override
    public Value evaluate(MyDictionaryInterface<String, Value> tbl, MyHeapInterface<Value> heap) throws InterpreterException {
        //((exp1*exp2)-(exp1+exp2))
        ArithmeticExpression result = new ArithmeticExpression(new ArithmeticExpression(exp1, exp2, BinaryExpression.OPERATION.MULTIPLICATION), new ArithmeticExpression(exp1, exp2, BinaryExpression.OPERATION.ADDITION), BinaryExpression.OPERATION.SUBTRACTION);
        return result.evaluate(tbl, heap);
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnv) throws InterpreterException {
        Type typ1 = exp1.typeCheck(typeEnv), typ2 = exp2.typeCheck(typeEnv);
        if (typ1.equals(new IntType()))
            if (typ2.equals(new IntType()))
                return new IntType();
            else
                throw new InterpreterException("Second operand is not an integer");
        else
            throw new InterpreterException("First operand is not an integer");
    }


    @Override
    public String toString(){
        return "MUL(" + exp1 + "," + exp2 + ")";
    }
}