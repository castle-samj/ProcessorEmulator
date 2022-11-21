import java.util.ArrayList;

/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Process Control Block class for each process generated
 */
abstract class PCB {
    /* variables */
    private IKernel.state current_state;
    private short pid;
    // pointer to current instruction
    private int program_counter = 0;
    private final ArrayList<Instruction> instructions_in_process = new ArrayList<>();

    // TODO vars not implemented
    // List of open files (parent/child?)
    // Priority
    // Fork() [child?]


    /* PCB setters */
    public void setCurrentState(IKernel.state new_state) {
        this.current_state = new_state;
    }
    public void setPID() {
        this.pid = IKernel.generatePid();
    }
    // TODO use this when Instruction completes in CPU
    public void updateProgramCounter() {
        this.program_counter++;
    }


    /* PCB getters */
    public IKernel.state getCurrentState() {
        return current_state;
    }
    public short getPID() {
        return this.pid;
    }


    /* process-specific getters */
    public int getProcessCyclesRemain() {
        int sum = 0;
        for (int i = this.instructions_in_process.size()-1; i > this.sizeOfProcess(); i--){
            sum = sum + this.getInstructionCyclesRemain(i);
        }
        return sum;
    }
    public int sizeOfProcess() {
        return (this.instructions_in_process.size() - program_counter)-1;
    }
    public boolean isEmpty() {
        return this.instructions_in_process.isEmpty();
    }


    /* instruction-specific setters */
    public void addInstruction(int num, Instruction new_instruction) {
        this.instructions_in_process.add(num, new_instruction);
    }
    public void setAllInstructionLocation(int physical_location, int location_address) {
        for (Instruction instruction : instructions_in_process) {
            instruction.setLocation(physical_location, location_address);
        }
    }

    /* instruction-specific getters */
    /** Overloaded. Takes an index if known or returns the current Instruction at ProgramCounter */
    public Instruction getCurrentInstruction() {
        return this.instructions_in_process.get(program_counter);
    }
    public Instruction getCurrentInstruction(int index) {
        return this.instructions_in_process.get(index);
    }
    /** Overloaded. Takes an index if known or returns the cycles of Instruction at ProgramCounter */
    public int getInstructionCyclesRemain() {
        return this.instructions_in_process.get(this.program_counter).getCyclesRemain();
    }
    public int getInstructionCyclesRemain(int i) {
        return this.instructions_in_process.get(i).getCyclesRemain();
    }
    /** Returns true if current Instruction is of type I/O */
    public boolean isIO() {
        return this.getCurrentInstruction().isIO();
    }
    public int[] getCurrentInstructionLocation() {
        return this.instructions_in_process.get(program_counter).getLocation();
    }
} // end class pcb