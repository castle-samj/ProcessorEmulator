/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 * Used to create an instance of Main Memory hardware
 */
public class MainMemory extends IHardware
{
    // memory always to have size 512 for this project, but can be changed in Main()
    // total number of frames is based on total_mem/frame_size (ie 64 if 512/8)
    private Instruction[] memory_frames;
    private Short[] meta_frames;
    private boolean[] frames_in_use;

    /* Constructor */
    public MainMemory() {
        int new_size = IKernel.getSize_of_main_memory()/IKernel.getSizeOfFrame();
        this.hid = IKernel.generateHID();
        this.memory_frames = new Instruction[new_size];
        this.meta_frames = new Short[new_size];
        this.frames_in_use = new boolean[new_size];
    }

    /** Attempt to swap an instruction into main-memory, Returns true if successful */
    public boolean swapIn(Instruction new_instruction) {
        boolean conclusion = false;
        int counter = memory_frames.length-1;
        while (counter >= 0) {
            int frame_address = randomAddress();
            if (frames_in_use[frame_address]) {
                this.memory_frames[frame_address] = new_instruction;
                this.meta_frames[frame_address] = new_instruction.getParentPID();
                this.frames_in_use[frame_address] = true;
                conclusion = true;
                break;
            }
        }
        return conclusion;
    }
    public void swapOut(Instruction old_instruction) {

    }

    /** call using the process memory management section in PCB */
    public Instruction getInstructionByAddress(int address) {
        return this.memory_frames[address];
    }

    /**
     * Returns the Instruction associated with the requested PID.
     * If the PID is not found in the memory, Returns first Instruction in used_frames list.
     * Note: This should not happen, but Java got angry about "maybe not" initializing byPID
     */
    public Instruction getInstructionByPID(Short pid) {
        int index = 0;
        for (Short test_index : meta_frames) {
            if (test_index.equals(pid)){
                index = test_index;
            }
        }
        return memory_frames[index];
    }

    // locate an unused frame_address in memory
    private int randomAddress() {
        int new_address = 0;
        for (int index = 0; index < frames_in_use.length; index++) {
            if (!frames_in_use[index]) {
                new_address = index;
            }
        }
        return new_address;
    }
}
