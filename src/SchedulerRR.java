import java.util.ArrayList;

/**
 * Round Robin style scheduler, where each process is added to the end of a queue. Process at
 * front of queue is processed on CPu and, if more time is needed, returned to end of schedule.
 * @author Sam Castle - 10/23/2022 - CMSC312 CPU Emulator
 */
public class SchedulerRR {
    private ArrayList<process> schedule = new ArrayList<process>();

    /* setters */
    public void addToSchedule(process newProcess, Dispatcher currentDispatcher) {
        this.schedule.add(newProcess);
        this.schedule.get(this.schedule.size()-1).changeState(state.READY, currentDispatcher);
    }

    /* getters */
    public String getState() {
        return this.schedule.get(0).getState().toString();
    }

    /* extensions */
    public int size(){
        return this.schedule.size();
    }
    public boolean isEmpty(){
        return this.schedule.isEmpty();
    }
    public process get(int i){
        return this.schedule.get(i);
    }
    public void remove(int i){
        this.schedule.remove(i);
    }
    public void clear(){
        this.schedule.clear();
    }
}
