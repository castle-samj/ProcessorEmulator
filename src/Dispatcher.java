/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Dispatcher extends IKernel {

    /* Constructor */
    public Dispatcher(CPU new_cpu, MassStorage new_hdd, MainMemory new_ram, IOController new_io) {
        this.setLocalCPU(new_cpu);
        this.setLocalHdd(new_hdd);
        this.setLocalRAM(new_ram);
        this.setLocalIO(new_io);
    }

    /**
     * load the process from schedule that is first in line to the CPU. Also handles exit cases from CPU.
     * @see CPU#cpuTime
     */
    private void loadProcess() {
        Instruction currentInstruction = getLocalScheduler().getNextInstruction();
        currentInstruction.changeParentProcessState(state.RUNNING);

        byte result = getLocalCPU().cpuTime(this, currentInstruction);
        switch (result) {
            case 0:
                currentInstruction.changeParentProcessState(state.READY);
                break;
            case 1:
                currentInstruction.changeParentProcessState(state.WAITING);
                getLocalIO().addToWaiting(currentInstruction);
                break;
            case 2:
                currentInstruction.changeParentProcessState(state.TERMINATED);
                break;
            case 3:
                // TODO trigger Fork()
                break;
        }
    } // end loadProcess


    public void decrementIfInWaiting(){
        if (!getLocalIO().isEmpty()) {
            getLocalIO().decrementWaiting();
        }
    } // end decrementWaiting

} // end Dispatcher