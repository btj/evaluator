package evaluator_ir;

interface Instruction {}

record LoadConstant(int r, int c) implements Instruction {}
record JumpIfZero(int r, int a) implements Instruction {}
record Multiply(int r1, int r2) implements Instruction {}

interface Value {}

interface AddableValue extends Value {
	
	Value add(Value other);
	
}

interface AndableValue extends Value {
	
	Value and(Value other);
	
}

class IntValue implements AddableValue, AndableValue {
	
	final int value;
	
	public IntValue(int value) { this.value = value; }
	
	public Value add(Value other) { return new IntValue(value + ((IntValue)other).value); }
	
	public Value and(Value other) { return new IntValue(value & ((IntValue)other).value); }
	
}

class StringValue implements AddableValue {
	
	final String value;
	
	public StringValue(String value) { this.value = value; }
	
	public Value add(Value other) { return new StringValue(value + ((StringValue)other).value); }
	
}

class BooleanValue implements AndableValue {
	
	final boolean value;
	
	public BooleanValue(boolean value) { this.value = value; }
	
	public Value and(Value other) { return new BooleanValue(value & ((BooleanValue)other).value); }
	
}

public class Evaluator {
	
	public static Value evaluate(Value v1, char operator, Value v2) {
		switch (operator) {
		case '+': {
			if (v1 instanceof AddableValue i1)
				return i1.add(v2);
			else
				throw new RuntimeException("Operator not supported on this type of value");
		}
		case '&': {
			if (v1 instanceof AndableValue i1)
				return i1.and(v2);
			else
				throw new RuntimeException("Operator not supported on this type of value");
		}
		default:
			throw new RuntimeException("Operator not supported");
		}
	}

}
