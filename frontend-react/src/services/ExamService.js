import axios from "axios";

const EXAM_API_URL = "http://localhost:8080/examen";

class ExamService{
    
    CargarArchivo(file){
        return axios.post(EXAM_API_URL, file);
    }
    generarReporte(rut){
        return axios.get(EXAM_API_URL+ '/Generate-report/' + rut);
    }

    generarPlanilla(rut){
        return axios.get(EXAM_API_URL+ '/Generate-spreadsheet/' + rut);
    }
}

export default new ExamService()
