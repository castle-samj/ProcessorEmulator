import java.util.ArrayList;

/**
 * Process Control Block class for each process generated
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class PCB {
    // variables
    private state currentState;
    private int PID;
    private int programCounter; // current register index value
    private ArrayList<operation> registers = new ArrayList<operation>();
    // vars not implemented
    // Priority
    // Accounting

    /* setters */
    public void setCurrentState(state newState) {
        this.currentState = newState;
    }
    public void setProgramCounter(int operationIndex) {
        this.programCounter = operationIndex;
    }
    public void setPID(int num) {
        this.PID = num;
    }
    public void setRegisters(operation registers) {
        this.registers.add(registers);
    }

    /* getters */
    public state getCurrentState() {
        return currentState;
    }
    public int getProgramCounter() {
        return programCounter;
    }
    public int getPID() {
        return PID;
    }
    public operation getRegister() {
        return this.registers.get(this.programCounter);
    }
    public String getRegisterType(int i){
        return this.registers.get(i).getOperationType();
    }
    public int getRegisterCycles(int i) {
        return this.registers.get(i).cyclesRemain;
    }
    public int size(){
        return this.registers.size();
    }
} // end class pcb
