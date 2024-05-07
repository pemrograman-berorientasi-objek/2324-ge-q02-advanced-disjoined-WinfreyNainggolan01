package academic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;

/**
 * @author 12S22001 Winfrey Nainggolan
 * @author 12S22012 Reinhard Batubara
 */
public class Executor {
     ArrayList<Student> listStudent = new ArrayList<Student>();
    ArrayList<Lecturer> listLecturer = new ArrayList<Lecturer>();
    ArrayList<Course> listCourse = new ArrayList<Course>();
    ArrayList<Enrollment> listEnrollment = new ArrayList<Enrollment>();
    ArrayList<CourseOpening> listCourseOpening = new ArrayList<CourseOpening>();

    
    public Executor() {
        
    }


// STUDENT
    public void CtrlStudent(String data[]) {
        if(!Generic.isExist(listStudent, data[1])) {
            this.listStudent.add(new Student(data[1], data[2], data[3], data[4]));
        }

    }


// LECTURER
    public void CtrlLecturer(String[] data) {
        if(!Generic.isExist(listLecturer, data[1])){
            this.listLecturer.add(new Lecturer(data[1], data[2], data[3], data[4], data[5]));
        }
    }


// COURSE
    public void CtrlCourse(String[] data) {
        Course course = new Course(data[1], data[2], Byte.parseByte(data[3]), data[4]);
        this.listCourse.add(course);

    }

    public static void setLecCourse(String[] lec_init, ArrayList<Lecturer> listLecturer) {
        Course course = new Course();
        StringBuilder sb = new StringBuilder();
        byte count = (byte)lec_init.length;

        for (int i = 0; i < lec_init.length; i++) {
            for(Lecturer lec : listLecturer) {
                if(lec_init[i].equals(lec.getInit())) {
                    sb.append(lec.getInit()+" ("+lec.getEmail()+")");
                    if(count > 1) {
                        sb.append(";");
                        count--;
                    }
                }
            }
        }
        course.setLecCourse(sb.toString());
    }


// ENROLLMENT
    public void CtrlEnrollment(String[] data) {
        Course course = new Course();
        Student student = new Student();

        for(Course crs : this.listCourse) {
            if(crs.getId().equals(data[1])) {
                course = crs;
            }
        }
        for(Student std : this.listStudent) {
            if(std.getId().equals(data[2])) {
                student = std;
            }
        }

        Enrollment enrollment = new Enrollment(course, student, data[3], data[4]);
        enrollment.setGradeStd(enrollment.getGradeStd());
        listEnrollment.add(enrollment);
    }

    public void printListEnroll(ArrayList<Enrollment> listEnr) {
        
        listEnr.forEach(enrollment -> 
        Optional.ofNullable(enrollment.getRemedial())
        .ifPresentOrElse(remedial -> System.out.println(enrollment.toStringDetail()), ()-> System.out.println(enrollment)));

    }



// ENROLLMENT GRADE
    public void CtrlEnrollmentG(String[] data) {
        for(Enrollment enr : this.listEnrollment) {
            if(data[1].equals(enr.getCrsId()) && data[2].equals(enr.getStdId()) && enr.getYear().equals(data[3]) && enr.getTerm().equals(data[4])) {
                enr.setGradeStd(data[5]);
                break;
            }
        }
    }
    

// STUDENT DETAILS
    public void CtrlStudentD(String[] data) {

        for(var std : listStudent) {
            if(std.getId().equals(data[1])){
                if(std.getGpa() == 0) {
                    CalculateGPA(data[1], listEnrollment);
                    break;
                }
            }
        }

        for(var std : listStudent){
            if(std.getId().equals(data[1])){
                System.out.println(std.toStringDetail());
                break;
            }
        }

    }


    public void CalculateGPA(String data, ArrayList<Enrollment> listEnr){
        double sumGrade = 0, result = 0, mulGpa = 0, tempGrade = 0;
        int sumSks = 0;
        boolean empty = false;
        byte count = 0;
        String tempIdEnroll = "";

        for(Enrollment enr : listEnr) {
            if(enr.getStdId().equals(data)) {
                if(enr.getGradeStd().equals("None")) {
                    if(count == 0)
                        empty = true;
                    break;
                }else {
                    if(enr.getRemedial() != null) {
                        sumGrade = EnumGrade.getScale(enr.getRemedial());
                    }else {
                        sumGrade = EnumGrade.getScale(enr.getGradeStd());
                    }

                    for(Course crs : listCourse) {
                        if(enr.getCrsId().equals(crs.getId())) {
                            if(enr.getCrsId().equals(tempIdEnroll)) {
                                sumSks -= crs.getCredit();
                                mulGpa -= (crs.getCredit() * tempGrade);
                            }
                            sumSks += crs.getCredit();
                            mulGpa += (crs.getCredit() * sumGrade);
                            tempIdEnroll = enr.getCrsId();
                            tempGrade = sumGrade;
                            break;
                        }
                    }
                }
            }
            count = 1;
        }
            try {
                if(sumSks == 0) {
                    result = 0;
                }else {
                    result = (mulGpa/sumSks);
                }
            }catch(Exception e) {
                result = 0;
                System.out.println("Hasil Infinite/ Tak hingga");
            }
        AddValueStd(listStudent, empty, data, result, sumSks);
    }


    public void AddValueStd(ArrayList<Student> listStudent, boolean empty, String data, double result, int sumSks) {
        for (int i = 0; i < listStudent.size(); i++) {
            if(data.equals(listStudent.get(i).getId())) {
                if(!empty) {
                    listStudent.get(i).setGpa(result);
                    listStudent.get(i).setSks(sumSks);
                }
            }
        }
    }


// ENROLLMENT REMEDIAL
    public void CtrlEnrollmentR(String[] data) { 
        for(Enrollment enr : listEnrollment) {
            if(enr.getCrsId().equals(data[1]) && enr.getStdId().equals(data[2]) && enr.getYear().equals(data[3]) && enr.getGradeStd() != "None" && enr.getTerm().equals(data[4])) {
                if(enr.getCount() < 1) {
                    enr.setRemedial(data[5]);
                    enr.setCount();
                    break;
                }
            }
        }
    }


// COURSE OPENING
    public void CtrlCourseO(String[] data) {
        String init[] = data[4].split(",");
        if(init.length == 1){
            CourseOpening courseOpening = new CourseOpening(data[1], data[2], data[3], data[4]); 
            listCourseOpening.add(courseOpening);
        }

        
    }


// COURSE HISTORY
    public void CtrlCourseH(String[] data) {
        Collections.sort(listCourseOpening, Comparator.comparing(CourseOpening::getSemOpen).reversed());
        for (CourseOpening crsOp : listCourseOpening) {
                for(Course crs : listCourse) {
                    if(crs.getId().equals(data[1])) {
                        crsOp.setName(crs.getName());
                        crsOp.setCredit(crs.getCredit());
                        crsOp.setGrade(crs.getGrade());
                            for(Lecturer lec : listLecturer) {
                                if(crsOp.getLecOpen().equals(lec.getInit())) {
                                    crsOp.setLecEmail(lec.getEmail());
                                    break;
                                }
                            }

                        break;
                    }
            }
            System.out.println(crsOp);

            for(Enrollment enr : listEnrollment) {
                if(enr.getCrsId().equals(crsOp.getId()) && enr.getYear().equals(crsOp.getYearOpen()) && enr.getTerm().equals(crsOp.getSemOpen())) {

                    if(enr.getRemedial() != null) {
                        System.out.println(enr.toStringDetail());
                    }else {
                        System.out.println(enr);
                    }
                }
            }
        }
        
    }

// STUDENT TRANSCRIPT
    public void CtrlStudentT(String[] data){
        ArrayList<Enrollment> tempEnr = new ArrayList<Enrollment>();

        for(var enr : listEnrollment){
            if(enr.getStdId().equals(data[1])){
                tempEnr.add(enr);
            }
        }

        Collections.sort(tempEnr, Comparator
            .comparing(Enrollment::getCrsId)
            .thenComparing(Enrollment::getYear)
            .reversed());

        // Local Class
        class SortingEnrollment {
            private ArrayList<Enrollment> listEnrTranscript = new ArrayList<Enrollment>();

            public SortingEnrollment() {
                
            }

            public void addEnrTranscript(ArrayList<Enrollment> tempEnr){
                String tempId = "";

                for(var crs : listCourse){
                    for(var enr : tempEnr){
                        if(enr.getCrsId().equals(tempId))
                            continue;
                        
                        if(enr.getStdId().equals(data[1]) && enr.getCrsId().equals(crs.getId())){
                            listEnrTranscript.add(enr);
                            tempId = crs.getId();
                        }
                    }
                }

                tempEnr.removeAll(tempEnr);
                GPACalculation();
            }

            public void GPACalculation(){
                for(var std : listStudent){
                    if(std.getId().equals(data[1])){
                        resetGPA(std);
                        CalculateGPA(data[1], listEnrTranscript);
                        System.out.println(std.toStringDetail());
                    }
                }

                printEnrTranscript(listEnrTranscript);
            }

            public void printEnrTranscript(ArrayList<Enrollment> listET){
                for(var enr : listET) {
                    if(enr.getRemedial() != null) {
                        System.out.println(enr.toStringDetail());
                    }else{
                        System.out.println(enr);
                    }
                }
            }

        }

        SortingEnrollment sortEnr = new SortingEnrollment();
        sortEnr.addEnrTranscript(tempEnr);
        
    }

    public void resetGPA(Student std){
        std.setGpa(0);
        std.setSks(0);
    }


    // Print all ELement
    public void printElement() {
        Generic.printList(this.listLecturer);
        Generic.printList(this.listCourse);
        Generic.printList(this.listStudent);
        printListEnroll(this.listEnrollment);
    }
}
