package academic.model;


/**
 * @author 12S22001 Winfrey Nainggolan
 * @author 12S22012 Reinhard Batubara
 */
public class Student extends Identifier{

    // class definition
    private String year;
    private String studyProgram;
    private double gpa;
    private int sks;

    public Student(String NIM, String name, String year, String studyProgram){ 
        super.id = NIM;
        super.name = name;
        this.year = year;
        this.studyProgram = studyProgram;
        this.gpa = 0;
        this.sks = 0;
    }

    public Student(){
    
    }

    // MUTATOR
    public void setGpa(double _gpa){
        this.gpa = _gpa;
    }

    public void setSks(int _sks){
        this.sks = _sks;
    }

    // ACCESSOR
    public double getGpa(){
        return this.gpa;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + this.year + "|" + this.studyProgram;
    }

    public String toStringDetail(){
        return super.toString() + "|" + this.year + "|" + this.studyProgram + "|" +  String.format("%.2f", this.gpa)+ "|" + this.sks;
    }
}