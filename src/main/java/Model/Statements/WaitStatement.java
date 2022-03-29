package Model.Statements;
import Exceptions.InterpreterException;
import Model.ADTs.MyDictionary;
import Model.ADTs.MyDictionaryInterface;
import Model.Expressions.Expression;
import Model.Expressions.ValueExpression;
import Model.ProgramState;
import Model.Types.IntType;
import Model.Types.Type;
import Model.Values.IntValue;

public class WaitStatement implements Statement {
    private Expression expression;

    public WaitStatement(Expression expression)
    {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        IntValue value = (IntValue) expression.evaluate(state.getSymbolTable(), state.getHeap());
        int time = value.getValue();
        if (time > 0)
        {
            state.getExecutionStack().push(new CompoundStatement(new PrintStatement(new ValueExpression(value)),
                    new WaitStatement(new ValueExpression(new IntValue(time - 1)))));
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        if(expression.typeCheck(typeEnvironment).equals(new IntType()))
            return typeEnvironment;
        throw new InterpreterException("Parameter does not evaluate to int.");
    }

    @Override
    public String toString() {
        return "wait(" + expression.toString() + ");";
    }
}
