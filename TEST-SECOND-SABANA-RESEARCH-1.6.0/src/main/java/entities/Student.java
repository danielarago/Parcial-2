package entities;

import java.time.Duration;
import java.util.List;

public class Student {
    private int code;
    private String name;
    private String lastName;
    private String email;
    private List<Activity> assignedActivities;

    public Duration getActivitiesDurations() throws SabanaResearchException {
        Duration duration = Duration.ZERO;

        for (Activity a: assignedActivities){
            duration = a.getDuration().plus(duration);
        }

        return duration;
    }

    public String getName() {
        return name;
    }
}
