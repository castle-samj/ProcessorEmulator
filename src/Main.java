import java.util.Scanner;

/**
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Sam's CPU Emulator");
        boolean run = true; // shell control var
        Scanner userInput = new Scanner(System.in);
        SchedulerRR currentSchedule = new SchedulerRR();
        Dispatcher currentDispatcher = new Dispatcher();

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
            switch(menu) {
                case -1:
                    System.out.println("Invalid input!\n");
                    break;
                case 1: // automatic trial run
                    auto(currentSchedule, currentDispatcher);
                    break;
                case 2: // create new processes
                    System.out.println("Enter a number of processes to create:");
                    int userNum = Integer.parseInt(userInput.nextLine());
                    build(currentSchedule, currentDispatcher, userNum);
                    break;
                case 3: // display scheduled processes
                    if (currentSchedule.isEmpty()) {
                        System.out.println("Schedule is empty.\n");
                    }
                    else {
                        for (int i = 0; i < currentSchedule.size()-1;i++) {
                            procDisplay.processDisplay(currentSchedule.get(i));
                        }
                    }
                    break;
                case 4:
                    // start simulation on CPU
                    startSimulation(currentSchedule, currentDispatcher);
                    break;
                case 5:
                    currentSchedule.clear();
                    System.out.println("Schedule is empty.\n");
                    break;
                case 9: // exit case
                    run = false;
                    break;
                default: // default catch for errors
                    System.out.println("You input " + menu + ". Please input a valid integer.\n");
                    break;
            } // end switch

        } //end while control loop
        System.out.println("Closing Application");
    } // end main

    public static void auto(SchedulerRR currentSchedule, Dispatcher currentDispatcher)
    {
        // do all the steps automatically/dynamically
        System.out.println("Automatic Mode");
        System.out.println("Generating random number of processes");
        int numProcs = (int) (Math.random() * 100) + 1;
        System.out.println("Building " + numProcs + " new processes..");
        build(currentSchedule, currentDispatcher, numProcs);
        System.out.println("Begin Dispatching..");
        startSimulation(currentSchedule, currentDispatcher);
    } // end auto

    public static void build(SchedulerRR currentSchedule, Dispatcher currentDispatcher, int num) {
        // TODO correct xml file templates
        try {
            procBuilder.processBuilder(currentSchedule, currentDispatcher, num);
        }
        catch (Exception e) {
            System.out.println("Hmm.. I didn't like that input. Try again. \n");
        }
    } // end build

    public static void startSimulation(SchedulerRR currentSchedule, Dispatcher currentDispatcher){
        // TODO implement number of cycles before pausing
        currentDispatcher.loadSchedule(currentSchedule);
        if (currentSchedule.isEmpty()) {
            System.out.println("Schedule Complete. Terminated Processes (in order): ");
        }
        currentDispatcher.TERMINATED.forEach((e) -> System.out.print(e.getPID() + ". "));
        System.out.println("\n");
    } // end startSimulation

} //end class Main

