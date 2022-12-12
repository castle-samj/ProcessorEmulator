/**
 * @author Sam Castle - 12/12/2022 - CMSC312 CPU Emulator
 */
public class Instruction {
    /**
     * The potential types of instructions. Currently limited to I/O, Calculate, and Fork
     */
    enum REGISTER_TYPES {
        IO,
        CALCULATE,
        FORK
    }

    /* variables */
    private final Process parent_process;
    private final short parent_pid;
    private final REGISTER_TYPES register;
    private int cycles_remain;
    private final int[] location;

    /* constructor */
    public Instruction(REGISTER_TYPES new_type, int new_cpu_cycles, Process new_parent) {
        this.parent_process = new_parent;
        this.parent_pid = new_parent.getPID();
        this.register = new_type;
        this.cycles_remain = new_cpu_cycles;
        this.location = new int[2];
    }


    /* setters */
    public void setLocation(int physical_location, int location_address) {
        this.location[0] = physical_location;
        this.location[1] = location_address;
    }


    /* getters */
    public Process getParentProcess() {
        return this.parent_process;
    }
    public short getParentPID() {
        return this.parent_pid;
    }
    public REGISTER_TYPES getRegister() {
        return register;
    }
    public int getCyclesRemain() {
        return cycles_remain;
    }
    public int[] getLocation() {
        return this.location;
    }

    /* special */
    public boolean isIO() {
        return this.register.equals(REGISTER_TYPES.IO);
    }
    public void changeParentProcessState(IKernel.state new_state) {
        this.parent_process.setCurrentState(new_state);
    }
    // reduce number of cycles by one
    public void decrementInstructionCycles() {
        this.cycles_remain--;
    }
} // end class process