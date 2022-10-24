import java.util.ArrayList;

/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Dispatcher {
    public ArrayList<process> READY = new ArrayList<process>();
    public ArrayList<process> RUNNING = new ArrayList<process>();
    public ArrayList<process> WAITING = new ArrayList<process>();
    public ArrayList<process> TERMINATED = new ArrayList<process>();

    /**
     * add given process to given Dispatcher state list
     * @param newProcess process to add to state list
     * @param newState desired state
     */
    public void addToStateList (process newProcess, state newState) {
        switch (newState) {
            case READY:
                READY.add(newProcess);
                break;
            case RUNNING:
                RUNNING.add(newProcess);
                break;
            case WAITING:
                WAITING.add(newProcess);
                break;
            case TERMINATED:
                TERMINATED.add(newProcess);
                break;
        } // end switch (newState)
    } // end addToStateList

    /**
     * Remove given process from its current listing in Dispatcher state list
     * @param existingProcess process to remove from state list: existingState inferred from process.
     */
    public void removeFromStateList(process existingProcess) {
        state existingState = existingProcess.getState();
        switch (existingState) {
            case READY:
                READY.remove(existingProcess);
                break;
            case RUNNING:
                RUNNING.remove(existingProcess);
                break;
            case WAITING:
                WAITING.remove(existingProcess);
        } // end switch (existingState)
    } // end removeFromStateList

    public boolean hasWaiting(){
        if (this.WAITING.size() > 0){ return true; }
        else { return false; }
    } // end hasWaiting

    /**
     * load a schedule into dispatcher
     * @param schedule
     */
    public void loadSchedule(SchedulerRR schedule) {
        // while schedule is not empty
        while (!schedule.isEmpty()) {
            String tempState = schedule.getState();
            // load the first process on the schedule list
            process toLoad = schedule.get(0);
            schedule.remove(0); // remove from schedule
            int result = loadProcess(toLoad, schedule); // cpuTime returns exit condition
            if  (result == 0) {
                // add process to schedule for more time on CPU
                schedule.addToSchedule(toLoad, this);
            }
        }
    } // end loadScheduler

    /**
     * load the process from schedule that is first in line to the CPU. Also handles exit cases from CPU.
     * @see cpu#cpuTime
     * @param currentProcess process being sent to CPU
     */
    private int loadProcess(process currentProcess, SchedulerRR currentSchedule) {

        currentProcess.changeState(state.RUNNING, this);
        int result = cpu.cpuTime(currentProcess, currentSchedule, this);
        if (result == 1) {
            currentProcess.changeState(state.WAITING, this);
            this.WAITING.add(currentProcess);
        } else if (result == 2) {
            currentProcess.changeState(state.TERMINATED, this);
        }
        return result;
    } // end loadProcess
} // end Dispatcher