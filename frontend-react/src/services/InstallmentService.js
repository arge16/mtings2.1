import axios from 'axios';

const INSTALLMENT_API_URL = "http://localhost:8080/cuotas";

class InstallmentService {

    getInstallments(rut){
        return axios.get(INSTALLMENT_API_URL+ '/bystudent/' + rut);
    }

    createContado(rut){
        return axios.get(INSTALLMENT_API_URL + '/generar-contado/' + rut);
    }

    markPaid(id){
        return axios.get(INSTALLMENT_API_URL + '/mark-paid/' + id);
    }

    createCuotas(rut, limit){
        return axios.get(INSTALLMENT_API_URL + '/generar-cuotas/'+rut+'/'+limit);
    }

    getLimit(rut){
        return axios.get(INSTALLMENT_API_URL + '/calcular-limit/'+rut);
    }
    
}

export default new InstallmentService()