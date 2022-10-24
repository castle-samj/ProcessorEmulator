/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class cpu {
    /**
    Takes a process and Dispatcher. Returns an int to signify exit case.
     <ul>
     <li>Case 0: Process was type calculate and has cycles remaining.</li>
     <li>Case 1: Process is type I/O and needs to be moved to WAITING state.</li>
     <li>Case 2: Process has no cycles remaining and timeSlice is still valid. Send to TERMINATED state.</li>
     </ul>
     */
    public static int cpuTime(process currentProcess, SchedulerRR currentSchedule, Dispatcher currentDispatcher) {
        // TODO convert timeSlice to global, allow it to be set by user
        //  will be used for custom cycles-before-pause
        int timeSlice = 10;
        int exitCondition;

        // check if calculate or I/O
        if (currentProcess.getProgramCounter().getOperationType().equals("I/O")){
            exitCondition = 1; // no need to process if I/O
        }
        else {
            operation cP = currentProcess.getProgramCounter();
            while (timeSlice > 0 && (cP.cyclesRemain > 0)) {
                cP.decrementCycles();

                // process operations in WAITING are decremented concurrently with CPU cycles
                if (currentDispatcher.hasWaiting()) {
                    process waitingProcess = currentDispatcher.WAITING.get(0);
                    waitingProcess.getProgramCounter().decrementCycles();
                    if (!currentDispatcher.hasWaiting()){
                        currentSchedule.addToSchedule(waitingProcess, currentDispatcher);
                    }
                }
                timeSlice--;
            }

            if (cP.cyclesRemain == 0) {
                exitCondition = 2; // handle TERMINATE first
            } else {
                exitCondition = 0; // default exit because more cycles remain
            }
        } // end calculate section

        return exitCondition;
    } // end cpuTime
}
