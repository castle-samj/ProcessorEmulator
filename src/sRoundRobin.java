/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Round Robin style scheduler, where each process is added to the end of a queue. Process at
 * front of queue is selected for processing on CPU and, if more time is needed, returned to end of schedule list.
 */
public class sRoundRobin extends IScheduler {

    /* setters */
    @Override
    public void addToReadyQueue(Instruction new_instruction) {
        new_instruction.changeParentProcessState(state.READY);
        this.ready_queue.add(new_instruction);
    }


    /* getters */
    @Override
    public Instruction getNextInstruction() {
        Instruction tempInstruction = this.ready_queue.get(0);
        this.remove();
        return tempInstruction;
    }
    @Override
    public Instruction referenceInstruction(int index) {
        return this.ready_queue.get(index);
    }
    public Instruction getInstructionByPID(short pid) {
        int index = this.ready_queue.indexOf(pid);
        return this.ready_queue.get(index);
    }

    /* extensions */
    @Override
    public int size() {
        return this.ready_queue.size();
    }
    @Override
    public boolean isEmpty() {
        return this.ready_queue.isEmpty();
    }
    public void remove() {
        this.ready_queue.remove(0);
    }
    public void clear() {
        this.ready_queue.clear();
    }
}
