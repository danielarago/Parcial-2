import com.github.javafaker.Faker;
import entities.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectTest {

    private static Faker faker;

    private Project wellFormedProject;
    private Project badFormedProject1; // Project without iterations
    private Project badFormedProject2; // Project where an iteration hasn't activities
    private Project badFormedProject3; // Project where a normal activity hasn't steps
    private Project badFormedProject4; // Project where a documented activity hasn't questions
    private Project badFormedProject5; // Project where a documented activity hasn't normal activity
    private Project badFormedProject6; // Project where a documented activity with normal activity hasn't steps
    private Project ExecutiveSynthesizerProject; //Project using an ExecutiveSynthesizer
    private Project StudentSynthesizerProject; //Project using a StudentSynthesizer

    public ProjectTest() {

        faker = new Faker(new Locale("en-US"));
    }

    @BeforeEach
    public void setup() {

        setupWellFormedProject();
        setupBadFormedProject1();
        setupBadFormedProject2();
        setupBadFormedProject3();
        setupBadFormedProject4();
        setupBadFormedProject5();
        setupBadFormedProject6();
        setUpExecutiveSynthesizerProject();
        setUpStudentSynthesizerProject();

    }

    @AfterEach
    public void takeDown(){
        wellFormedProject = null;
        badFormedProject1 = null;
        badFormedProject2 = null;
        badFormedProject3 = null;
        badFormedProject4 = null;
        badFormedProject5 = null;
        badFormedProject6 = null;
        ExecutiveSynthesizerProject = null;
        StudentSynthesizerProject = null;
    }

    @Test
    @DisplayName("GIVEN a well formed project WHEN get duration THEN return the project duration")
    public void shouldGetDurationWhenWellFormedProject() {

        try {
            Duration duration = wellFormedProject.getDuration();
            assertEquals(5, duration.toDays());
        } catch (SabanaResearchException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("GIVEN a project without iterations WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenProjectWithoutIterations() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject1.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_PROJECT, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN a iteration without activities WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenIterationWithoutActivities() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject2.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_ITERATION, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN a normal activity without steps WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenNormalActivityWithputSteps() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject3.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_NORMAL_ACTIVITY, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN a documented activity without questions WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenDocumentedActivityWithoutQuestions() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject4.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_DOCUMENTED_ACTIVITY, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN a documented activity without normal activity WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenDocumentedActivityWithoutNormalActivity() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject5.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_DOCUMENTED_ACTIVITY_WITHOUT_NORMAL_QUESTION, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN a documented activity with normal activity without steps WHEN get duration THEN get SabanaResearchException")
    public void shouldThrowsSabanaResearchExceptionWhenDocumentedActivityWithNormalActivityWithoutSteps() {

        SabanaResearchException exception = assertThrows(SabanaResearchException.class, () -> badFormedProject6.getDuration());
        assertEquals(SabanaResearchException.BAD_FORMED_NORMAL_ACTIVITY, exception.getMessage());
    }

    @Test
    @DisplayName("GIVEN an executive synthesizer WHEN asked to summarize THEN get a complete summary of goal's and duration's of its iterations")
    public void shouldGetExecutiveSummary() {
        String actualSummary = null;

        try {
            List<Object> iterations = new ArrayList<>(ExecutiveSynthesizerProject.getIterations());
            actualSummary = ExecutiveSynthesizerProject.summarize(iterations);
        }
        catch (SabanaResearchException e) {
            fail(e.getMessage());
        }

        String expectedSummary = "Duration = PT96H, goal = Book club. Duration = PT72H, goal = Research literature. ";

        assertEquals(expectedSummary, actualSummary);
    }

    @Test
    @DisplayName("GIVEN a student synthesizer WHEN asked to summarize THEN get a complete summary of name's and duration's of its students")
    public void shouldGetStudentSummary() {
        String actualSummary = "";

        Group group = StudentSynthesizerProject.getGroup();

        try {
            List<Object> students = new ArrayList<>(group.getTotalStudents());
            actualSummary = StudentSynthesizerProject.summarize(students);
        }
        catch (SabanaResearchException e) {
            fail(e.getMessage());
        }

        String expectedSummary = "Duration = PT120H, name = Maya Angelou. Duration = PT48H, name = Robert Frost. ";

        assertEquals(expectedSummary, actualSummary);
    }

    private void setupWellFormedProject() {

        Group group = new Group(faker.team().name());
        wellFormedProject = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), wellFormedProject);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));
    }

    private void setupBadFormedProject1() {

        Group group = new Group(faker.team().name());
        badFormedProject1 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
    }

    private void setupBadFormedProject2() {

        Group group = new Group(faker.team().name());
        badFormedProject2 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), badFormedProject2);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));

        // Create Iteration without activities
        new Iteration(faker.team().name(), badFormedProject2);
    }

    private void setupBadFormedProject3() {

        Group group = new Group(faker.team().name());
        badFormedProject3 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), badFormedProject3);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));

        // Create a Normal Activity without steps
        new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
    }

    private void setupBadFormedProject4() {

        Group group = new Group(faker.team().name());
        badFormedProject4 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), badFormedProject4);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity without questions
        NormalActivity activity2 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity2.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity2);
    }

    private void setupBadFormedProject5() {

        Group group = new Group(faker.team().name());
        badFormedProject5 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), badFormedProject5);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity without normal activity
        DocumentedActivity documentedActivity2 = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, null);
        documentedActivity2.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));
    }

    private void setupBadFormedProject6() {

        Group group = new Group(faker.team().name());
        badFormedProject6 = new Project(faker.team().name(), LocalDate.now().minusDays(10), LocalDate.now().plusDays(10), group);
        Iteration iteration = new Iteration(faker.team().name(), badFormedProject6);

        // Create a Normal Activity
        NormalActivity normalActivity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration);
        normalActivity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity
        NormalActivity activity = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        activity.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        DocumentedActivity documentedActivity = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity);
        documentedActivity.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));

        // Create a Documented Activity with normal activity without steps
        NormalActivity activity2 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, null);
        DocumentedActivity documentedActivity2 = new DocumentedActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration, activity2);
        documentedActivity2.addQuestion(new Question(Question.EASY_QUESTION, faker.team().name(), Duration.ofDays(1)));
    }

    private void setUpExecutiveSynthesizerProject() {

        //Create activities for the iterations
        NormalActivity normalActivity1 = new NormalActivity();
        normalActivity1.addStep(new Step(faker.team().name(), Duration.ofDays(2)));
        NormalActivity normalActivity2 = new NormalActivity();
        normalActivity2.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        NormalActivity normalActivity3 = new NormalActivity();
        normalActivity3.addStep(new Step(faker.team().name(), Duration.ofDays(2)));
        NormalActivity normalActivity4 = new NormalActivity();
        normalActivity4.addStep(new Step(faker.team().name(), Duration.ofDays(2)));
        List<Activity> activities1 = new ArrayList<>();
        List<Activity> activities2 = new ArrayList<>();
        activities1.add(normalActivity1);
        activities1.add(normalActivity2);
        activities2.add(normalActivity3);
        activities2.add(normalActivity4);

        //Create a few iterations
        Iteration iteration1 = new Iteration("Research literature", activities1);
        Iteration iteration2 = new Iteration("Book club", activities2);

        //Add iterations to project
        List<Iteration> iterations = new ArrayList<>();
        iterations.add(iteration1);
        iterations.add(iteration2);
        ExecutiveSynthesizer executiveSynthesizer = new ExecutiveSynthesizer();
        ExecutiveSynthesizerProject = new Project(executiveSynthesizer, iterations);

    }

    private void setUpStudentSynthesizerProject(){
        //Create students
        Student student1 = new Student("Robert Frost");
        Student student2 = new Student("Maya Angelou");

        //Put students in group
        List<Student> members = new ArrayList<>();
        members.add(student1);
        Group group = new Group(members, student2);

        StudentSynthesizer studentSynthesizer = new StudentSynthesizer();
        StudentSynthesizerProject = new Project(group, studentSynthesizer);

        //Create a few iterations
        Iteration iteration1 = new Iteration("Research literature", StudentSynthesizerProject);
        Iteration iteration2 = new Iteration("Book club", StudentSynthesizerProject);

        //Create activities for the iterations
        NormalActivity normalActivity1 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration1);
        normalActivity1.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        NormalActivity normalActivity2 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration2);
        normalActivity2.addStep(new Step(faker.team().name(), Duration.ofDays(1)));
        NormalActivity normalActivity3 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration1);
        normalActivity3.addStep(new Step(faker.team().name(), Duration.ofDays(2)));
        NormalActivity normalActivity4 = new NormalActivity(faker.team().name(), Activity.ACTIVE_STATE, iteration2);
        normalActivity4.addStep(new Step(faker.team().name(), Duration.ofDays(3)));

        //Give activities to students
        student1.addAssignedActivity(normalActivity1);
        student1.addAssignedActivity(normalActivity2);
        student2.addAssignedActivity(normalActivity3);
        student2.addAssignedActivity(normalActivity4);
    }
}
