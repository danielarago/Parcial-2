package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SabanaResearch {

    private List<Group> groups;
    private List<Summary> summaries;

    public SabanaResearch(List<Group> groups) {
        this.groups = groups;
        this.summaries = new ArrayList<>();
    }

    public int countOfGroups() {
        return this.groups.size();
    }

    public int countOfSummaries() {
        return this.summaries.size();
    }

    /**
     * Create a summary entry in the current date.
     * - Calculate the count of active projects.
     *
     * @return The new Summary entry.
     */
    public Summary createSummaryEntry() {
        int ap = this.groups.stream().map(g -> g.countActiveProjects()).reduce(0, (a,b)->a+b);

        Summary summary = new Summary(LocalDate.now(), ap);

        summaries.add(summary);

        return summary;
    }

    public int countInactiveProjects(){
        return this.groups.stream().map(g -> g.countInactiveProjects()).reduce(0, (a,b)->a+b);
    }
}
