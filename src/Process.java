/**
 * Subclass of PCB. Used to create the process object
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Process extends PCB
{
    /** constructor */
    public Process() {
        this.setPID();
        this.setCurrentState(IKernel.state.NEW);
    }

}