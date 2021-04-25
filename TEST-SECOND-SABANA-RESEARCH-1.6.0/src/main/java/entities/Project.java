package entities;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private String name;
    private LocalDate dateInit;
    private LocalDate dateEnd;
    private Group group;
    private List<Iteration> iterations;
    private ISynthesizer synthesizer;

    public Project(String name, LocalDate dateInit, LocalDate dateEnd, Group group) {
        this.name = name;
        this.dateInit = dateInit;
        this.dateEnd = dateEnd;
        this.group = group;
        this.iterations = new ArrayList<>();

        group.addProject(this);
    }

    public Project(ISynthesizer synthesizer, List<Iteration> iterations) {
        this.synthesizer = synthesizer;
        this.iterations = iterations;
    }

    public Project(Group group, ISynthesizer synthesizer) {
        this.group = group;
        this.iterations = new ArrayList<>();
        this.synthesizer = synthesizer;
    }

    public void addIteration(Iteration iteration) {
        this.iterations.add(iteration);
    }

    public void setDateInit(LocalDate dateInit) {
        this.dateInit = dateInit;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Duration getDuration() throws SabanaResearchException {

        if (iterations.size() == 0)
                throw new SabanaResearchException(SabanaResearchException.BAD_FORMED_PROJECT);

        Duration duration = Duration.ZERO;

        for (Iteration i : iterations){
            duration = i.getDuration().plus(duration);
        }

        return duration;
    }

    public int countOpenActivities() {
        return this.iterations.stream().map(i -> i.countOpenActivities()).reduce(0, (a, b) -> a+b);
    }

    public int countClosedActivities() {
        return this.iterations.stream().map(i -> i.countClosedActivities()).reduce(0, (a, b) -> a+b);
    }

    public boolean isActive() {
        boolean result;

        if (LocalDate.now().isAfter(this.dateEnd)){
            result = false;
        } else {
            int openActivities = this.countOpenActivities();
            result = openActivities > 0;
        }

        return result;
    }

    public boolean isInactive() {
        boolean result;

        if (LocalDate.now().isBefore(this.dateEnd)){
            result = false;
        } else {
            int closedActivities = this.countClosedActivities();
            result = closedActivities > 0;
        }

        return result;
    }

    public String summarize(List<Object> objects) throws SabanaResearchException {
        return synthesizer.synthesize(objects);
    }

    public List<Iteration> getIterations() {
        return iterations;
    }

    public Group getGroup() {
        return group;
    }
}
