import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Sam Castle - 11/20/2022 - CMSC312 CPU Emulator
 */
public class Main extends IKernel {
    public static void main(String[] args) {

        /* System Configuration and Initialization */

        // initialize sizes (in MB)
        setSizeOfMemory(512);
        setSizeOfFrame(8);
        setSizeOfHardDrive(4096);
        setMaxInstructionsPerProcess(5);
        setTimeSliceDefault(10);
        // physical locations declared in IHardware
        CPU CPU_ONE = new CPU();
        CPU CPU_TWO = new CPU();
        MainMemory CURRENT_RAM = new MainMemory();
        MassStorage CURRENT_HDD = new MassStorage();
        IODevice CURRENT_IO_CONTROLLER = new IODevice();
        // TODO allow user to select scheduler
        sRoundRobin CURRENT_SCHEDULER = new sRoundRobin();
        Dispatcher CURRENT_DISPATCHER = new Dispatcher(CPU_ONE, CURRENT_HDD, CURRENT_RAM, CURRENT_IO_CONTROLLER);
        CURRENT_DISPATCHER.setLocalScheduler(CURRENT_SCHEDULER);

        Scanner userInput = new Scanner(System.in);
        boolean run = true; // shell control var
        System.out.println("Sam's CPU Emulator");
        // interactive shell until user select exit
        while (run) {
            System.out.println("-- Select Action --");
            System.out.println("     1. Automatic Mode");
            System.out.println("     2. Generate New Processes from Templates");
            System.out.println("     3. List Processes in Schedule");
            System.out.println("     4. Manual-Start Simulation");
            System.out.println("     5. Clear Schedule");
            System.out.println("     9. Exit");

            // parse input
            int menu;
            try { menu = Integer.parseInt(userInput.nextLine()); }
            catch (Exception e) { menu =-1; }

            //switch statement to catch inputs
            switch (menu) {
                case -1:
                        System.out.println("Invalid input!\n");
                        break;

                case 1:
                        // automatic trial run
                        auto(CURRENT_DISPATCHER);
                        break;

                case 2:
                        // create new processes
                        System.out.println("Enter a number of processes to create:");
                        int userNum = Integer.parseInt(userInput.nextLine());
                        build(userNum, CURRENT_HDD);
                        for (int i = 0; i < userNum; i++) {
                            CURRENT_SCHEDULER.scheduleSomething();
                        }
                        break;

                case 3:
                        // display scheduled processes
                        if (CURRENT_SCHEDULER.isEmpty()) {
                            System.out.println("Schedule is empty.\n");
                        } else {
                            // list of current Processes in Schedule
                            ArrayList<Process> procs_to_display = new ArrayList<>();
                            for (int i = 0; i < CURRENT_SCHEDULER.size() - 1; i++) {
                                // get instruction in Scheduler
                                Instruction instruction = CURRENT_SCHEDULER.getInstruction(CURRENT_DISPATCHER, i);
                                if (instruction != null) {
                                    if (!procs_to_display.contains(instruction.getParentProcess())) {
                                        procs_to_display.add(instruction.getParentProcess());
                                    }
                                }
                            }
                            for (Process process : procs_to_display) {
                                ProcessDisplay.display(process);
                            }
                        }
                        break;

                case 4:
                        // start simulation on CPU
                        startSimulation(CURRENT_DISPATCHER);
                        break;
                case 5:
                        CURRENT_SCHEDULER.clear();
                        System.out.println("Schedule is empty.\n");
                        break;

                case 9:
                        // exit case
                        run = false;
                        break;
                default:
                        // default catch for errors
                        System.out.println("You input " + menu + ". Please input a valid integer.\n");
            } // end switch

        } //end while control loop
        System.out.println("Closing Application");
    } // end main

    /** do all the steps automatically/dynamically */
    public static void auto(Dispatcher currentDispatcher) {
        System.out.println("Automatic Mode");
        System.out.println("Generating random number of processes");
        int number_of_processes = (int) (Math.random() * 100) + 1;
        System.out.println("Building " + number_of_processes + " new processes..");
        build(number_of_processes, currentDispatcher.getLocalHDD());
        System.out.println("Begin Dispatching..");
        startSimulation(currentDispatcher);
    } // end auto

    public static void build(int num, MassStorage current_HDD) {
        // TODO correct xml file templates
        try {
            ProcessBuilder.build(num, current_HDD);
        }
        catch (Exception e) {
            System.out.println("Main: There was a problem while trying to build processes. \n");
        }
    } // end build

    public static void startSimulation(Dispatcher currentDispatcher){
        // TODO implement number of cycles before pausing

        do {
            currentDispatcher.getLocalScheduler().scheduleSomething();
            if (!currentDispatcher.getLocalScheduler().isEmpty()) {
                currentDispatcher.getLocalScheduler().ensureNextIsInMainMemory();
                currentDispatcher.loadProcess();
            }
        } while (processesExist(currentDispatcher));

        if (currentDispatcher.getLocalScheduler().isEmpty()) {
            System.out.println("Schedule Complete. Current Snapshot of Hard Drive: ");
        }
        // TODO for each process terminated
        currentDispatcher.getLocalHDD().dumpHardDriveInfo();
        System.out.println("\n");
    } // end startSimulation

    public static boolean processesExist(Dispatcher currentDispatcher) {
        boolean is_empty = true;
        if (!currentDispatcher.getLocalRAM().isEmpty()) { is_empty = false; }
        else if (!currentDispatcher.getLocalHDD().isVirtualMemoryEmpty()) { is_empty = false; }
        else if (!currentDispatcher.getLocalScheduler().isEmpty()) { is_empty = false; }
        else if (!currentDispatcher.getLocalIO().isEmpty()) { is_empty = false; }
        return !is_empty;
    }

} //end class Main

