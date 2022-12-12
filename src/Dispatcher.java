/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class Dispatcher extends IKernel {

    /* Constructor */
    public Dispatcher(CPU new_cpu, MassStorage new_hdd, MainMemory new_ram, IODevice new_io) {
        this.setLocalCPU(new_cpu);
        this.setLocalHdd(new_hdd);
        this.setLocalRAM(new_ram);
        this.setLocalIO(new_io);
    }

    /**
     * load the process from schedule that is first in line to the CPU. Also handles exit cases from CPU.
     * @see CPU#cpuTime
     */
    public void loadProcess() {
        short next_instruction_pid = getLocalScheduler().getNextInstructionPID();
        Instruction current_instruction = findInstruction(next_instruction_pid);
        if (current_instruction != null) {
            current_instruction.changeParentProcessState(state.RUNNING);
            current_instruction.setLocation(getLocalCPU().cpu_location, 0);
            byte result = getLocalCPU().cpuTime(this, current_instruction);
            switch (result) {
                case 0 -> {
                    // Process has cycles remaining.
                    if (!getLocalScheduler().scheduleInstruction(current_instruction)) {
                        System.out.println("Something went wrong moving the Instruction from CPU to Memory");
                    }
                }
                case 1 ->
                    // Process is type I/O and needs to be moved to WAITING state.
                        getLocalIO().addToWaiting(current_instruction);
                case 2 -> {
                    // Instruction has no cycles remaining and timeSlice is still valid
                    if (current_instruction.getParentProcess().getProcessCyclesRemain() > 0) {
                        current_instruction.getParentProcess().updateProgramCounter();
                        getLocalScheduler().scheduleInstruction(current_instruction);
                    } else {
                        current_instruction.changeParentProcessState(state.TERMINATED);
                    }
                }
                case 3 -> {
                    try {
                        ProcessBuilder.build(1, getLocalHDD());
                    } catch (Exception e) {
                        System.out.println("There was a problem while trying to build processes. \n");
                    }
                }
            }
        } else {
            System.out.println("Could not load Instruction from Scheduler");
        }
    } // end loadProcess

    public Instruction findInstruction(short pid) {
        Instruction found_instruction = null;
        if (getLocalRAM().hasPID(pid)) {
            found_instruction = getLocalRAM().getInstructionByPID(pid);
        }
        else if (getLocalHDD().isPIDInVirtualMemory(pid)) {
            found_instruction = getLocalHDD().removeInstructionFromVirtualMemory(pid);
        }
        return found_instruction;
    }


    public void decrementIfInWaiting(){
        if (!getLocalIO().isEmpty()) {
            getLocalIO().decrementWaiting(this);
        }
     } // end decrementWaiting

} // end Dispatcher