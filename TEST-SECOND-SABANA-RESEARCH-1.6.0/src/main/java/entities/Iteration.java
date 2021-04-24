package entities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Iteration {

    private String goal;
    private Project project;
    private List<Activity> activities;

    public Iteration(String goal, Project project) {
        this.goal = goal;
        this.project = project;
        this.activities = new ArrayList<>();

        project.addIteration(this);
    }

    public void addActivity(Activity activity) {
        this.activities.add(activity);
    }

    public Duration getDuration() throws SabanaResearchException {

        if (activities.size() == 0)
            throw new SabanaResearchException(SabanaResearchException.BAD_FORMED_ITERATION);

        Duration duration = Duration.ZERO;

        for (Activity a : activities){
            duration = a.getDuration().plus(duration);
        }

        return duration;
    }

    public int countOpenActivities(){
        return (int) this.activities.stream().map(a -> a.isActive()).filter(b -> !b).count();
    }

}
