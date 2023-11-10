import React, { Component } from 'react'
import ExamService from '../services/ExamService'

class GenerateReportComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            rut: this.props.match.params.rut,
            student: {}
        }
    
    }

    componentDidMount(){
        ExamService.generarReporte(this.state.rut).then( res => {
            this.setState({student: res.data});
        })
    }


    render() {
        return (
            <div className="container mt-5">
                <div className="row justify-content-center">
                    <div className="col-md-8">
                        <div className="card">
                            <h1 className="card-header text-center">Reporte de Estudiante</h1>
                            <div className="card-body">
                                <ul className="list-group">
                                    <li className="list-group-item">
                                        <strong>RUT estudiante:</strong> {this.state.student.rut}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Nombre del estudiante:</strong> {this.state.student.name}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Apellido:</strong> {this.state.student.lastname}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Nro. exámenes rendidos:</strong> {this.state.student.totalExams}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Promedio puntaje exámenes:</strong> {this.state.student.scoresAverage}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Monto total arancel a pagar:</strong> ${this.state.student.totalDebt}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Tipo Pago:</strong> {this.state.student.paymentType}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Nro. total de cuotas pactadas:</strong> {this.state.student.installments}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Nro. cuotas pagadas:</strong> {this.state.student.installmentsPaid}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Monto total pagado:</strong> ${this.state.student.debtPaid}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Fecha último pago:</strong>
                                        {this.state.student.lastPayment ? (
                                            <span>{this.state.student.lastPayment}</span>
                                        ) : (
                                            <span>No hay cuotas pagadas</span>
                                        )}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Saldo por pagar:</strong> ${this.state.student.debtToPay}
                                    </li>
                                    <li className="list-group-item">
                                        <strong>Nro. Cuotas con retraso:</strong> {this.state.student.installmentsLate}
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

}

export default GenerateReportComponent

