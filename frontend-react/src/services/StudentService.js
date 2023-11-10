import axios from 'axios';

const STUDENT_API_URL = "http://localhost:8080/estudiante";

class StudentService {

    getStudents(){
        return axios.get(STUDENT_API_URL);
    }

    createStudent(student){
        return axios.post(STUDENT_API_URL, student);
    }

    getStudentById(studentId){
        return axios.get(STUDENT_API_URL + '/' + studentId);
    }
}

export default new StudentService()