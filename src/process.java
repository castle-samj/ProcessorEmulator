/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class process
{
    /** variables */
    public PCB PCB = new PCB();

    /** constructor? */
    public process(int procCount, Dispatcher currentDispatcher) {
        this.PCB.setPID(procCount);
        this.changeState(state.NEW, currentDispatcher);
    }

    /** setters */
    public void setNew(state newState) { // for new processes
        this.PCB.setCurrentState(newState);
    }
    public void changeState(state newState, Dispatcher currentDispatcher) {
        this.PCB.setCurrentState(newState);
        currentDispatcher.removeFromStateList(this);
        currentDispatcher.addToStateList(this, newState);
    }
    public void setCurrentOperation(int opIndex) {
        this.PCB.setProgramCounter(opIndex);
    }
    public void addOperations(operation selectOp) {
        this.PCB.setRegisters(selectOp);
    }

    /** getters */
    public int getPID() {
        return this.PCB.getPID();
    }
    public state getState() {
        return this.PCB.getCurrentState();
    }
    public operation getProgramCounter() {
        return this.PCB.getRegister();
    }
    public int size(){
        return this.PCB.size();
    }
    public String getRegisterType(int i){
        return this.PCB.getRegisterType(i);
    }
    public int getRegisterCycles(int i){
        return this.PCB.getRegisterCycles(i);
    }
    public int getOperationsRemain(){
        return (5 - this.PCB.getProgramCounter());
    }
}