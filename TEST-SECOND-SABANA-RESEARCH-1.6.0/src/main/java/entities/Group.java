package entities;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private String name;
    private List<Project> projects;
    private List<Student> members;
    private Student leader;

    public Group(String name) {
        this.name = name;
        this.projects = new ArrayList<>();
    }

    public Group(List<Student> members, Student leader) {
        this.members = members;
        this.leader = leader;
    }

    public int countActiveProjects(){
        return (int) this.projects.stream().map(p -> p.isActive()).filter(b->b).count();
    }

    public int countInactiveProjects(){
        return (int) this.projects.stream().map(p -> p.isInactive()).filter(b->b).count();
    }

    public void addProject(Project plan) {
        this.projects.add(plan);
    }

    public List<Student> getTotalStudents(){
        members.add(leader);
        return members;
    }

}
