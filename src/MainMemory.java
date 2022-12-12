/**
 * @author Sam Castle - 12/12/2022 - CMSC312 CPU Emulator
 * Used to create an instance of Main Memory hardware
 */
public class MainMemory extends IHardware {
    // memory always to have size 512 for this project, but can be changed in Main()
    // total number of frames is based on total_mem/frame_size (ie 64 if 512/8)
    private final Instruction[] main_memory;
    /** Stores PIDs of Processes in main_memory */
    private final Short[] main_memory_meta;
    private final boolean[] main_memory_used;
    private short frames_in_use;

    /* Constructor */
    public MainMemory() {
        this.hid = IKernel.generateHID();
        this.main_memory = new Instruction[max_memory_address];
        this.main_memory_meta = new Short[max_memory_address];
        this.main_memory_used = new boolean[max_memory_address];
    }

    /** Attempt to swap an instruction into main-memory, Returns true if successful */
    public boolean moveInstructionIntoMainMemory(Instruction new_instruction) {
        boolean conclusion = false;
        int counter = max_memory_address;
        while (counter > 0) {
            int frame_address = randomAddress();
            if (!main_memory_used[frame_address]) {
                this.main_memory[frame_address] = new_instruction;
                this.main_memory_meta[frame_address] = new_instruction.getParentPID();
                this.main_memory_used[frame_address] = true;
                new_instruction.setLocation(this.main_memory_location, frame_address);
                conclusion = true;
                break;
            }
            counter--;
        }
        return conclusion;
    }


    /** Pull the Instruction from memory by its address */
    public Instruction getInstructionByAddress(int address) {
        Instruction tempInstruction = this.main_memory[address];
        this.main_memory[address] = null;
        this.main_memory_meta[address] = -1;
        this.main_memory_used[address] = false;
        this.frames_in_use--;
        return tempInstruction;
    }

    /**
     * Returns the Instruction associated with the requested PID and removes it from main_memory.
     * If the PID is not found in the memory, Returns first Instruction in used_frames list.
     * Note: This should not happen, but Java got angry about "maybe not" initializing byPID
     */
    public Instruction getInstructionByPID(Short pid) {
        int index = 0;
        for (short test_index = 1; test_index < (main_memory_meta.length-1); test_index++) {
            if (main_memory_meta[test_index] == pid){
                index = test_index;
                break;
            }
        }
        Instruction temporary_instruction = this.main_memory[index];
        this.main_memory[index] = null;
        this.main_memory_meta[index] = 0;
        this.main_memory_used[index] = false;
        return temporary_instruction;
    }
    public boolean hasPID(short pid) {
        boolean conclusion = false;
        for (short test_index = 1; test_index < (main_memory_meta.length-1); test_index++) {
            if (main_memory_meta[test_index] != null) {
                if (main_memory_meta[test_index] == pid) {
                    conclusion = true;
                    break;
                }
            }
        }
        return conclusion;
    }

    // locate an unused frame_address in memory
    private int randomAddress() {
        int new_address = 0;
        for (int index = 1; index < max_memory_address; index++) {
            if (!main_memory_used[index]) {
                new_address = index;
                break;
            }
        }
        return new_address;
    }

    public boolean isEmpty() {
        boolean conclusion = true;
        for (boolean bool : main_memory_used) {
            if (bool) {
                conclusion = false;
                break;
            }
        }
        return conclusion;
    }
    public boolean isFull() {
        boolean conclusion = true;
        for (boolean bool : main_memory_used) {
            if (!bool) {
                conclusion = false;
                break;
            }
        }
        return conclusion;
    }

} // end MainMemory
