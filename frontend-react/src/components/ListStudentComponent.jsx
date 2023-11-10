import React, { Component } from 'react';
import StudentService from '../services/StudentService';
import InstallmentService from '../services/InstallmentService';
import ExamService from '../services/ExamService';

class ListStudentComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            students: [],
        };

        this.addStudent = this.addStudent.bind(this);
        this.viewInstallments = this.viewInstallments.bind(this);
        this.loadCsv = this.loadCsv.bind(this);
        this.viewStudent = this.viewStudent.bind(this);
        this.generateReport = this.generateReport.bind(this);
        this.generateSpreadsheet = this.generateSpreadsheet.bind(this);
        this.pagoCuotas = this.pagoCuotas.bind(this);
        this.pagoContado = this.pagoContado.bind(this);
    }

    addStudent() {
        this.props.history.push('/add-student');
    }

    loadCsv() {
        this.props.history.push('/load-csv');
    }

    viewStudent(id) {
        this.props.history.push('/view-student/' + id);
    }

    generateReport(rut) {
        this.props.history.push('/view-report/' + rut);
    }

    generateSpreadsheet(rut) {
        ExamService.generarPlanilla(rut)
            .then((response) => {
                alert('Se ha generado la planilla de pago.');
            })
            .catch((error) => {
                console.error('Error al generar la planilla de pago:', error);
            });
    }

    pagoCuotas(rut) {
        this.props.history.push('/create-installments/' + rut);
    }

    pagoContado(rut) {
        InstallmentService.createContado(rut)
            .then((response) => {
                alert('Se ha generado el pago contado.');
            })
            .catch((error) => {
                console.error('Error al generar el pago contado:', error);
            });
    }

    deleteStudent(id) {
        alert('Deleting a Book is still under construction...');
    }

    viewInstallments(rut) {
        this.props.history.push('/view-installments/' + rut);
    }

    componentDidMount() {
        StudentService.getStudents()
            .then((res) => {
                this.setState({ students: res.data });
            })
            .catch((error) => {
                console.error('Error fetching students:', error);
            });
    }

    render() {
        return (
            <div>
                <br />
                <h2 className="text-center">Estudiantes</h2>
                <div className="d-flex">
                    <button className="btn btn-info" onClick={this.addStudent}>
                        Add Student
                    </button>
                    <button className="btn btn-info ml-2" onClick={this.loadCsv}>
                        Cargar Csv
                    </button>
                </div>
                <br />
                <div className="row">
                    <table className="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Apellido</th>
                                <th>Rut</th>
                                <th>Año egreso</th>
                                <th>Colegio</th>
                                <th>Tipo Colegio</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.students.length > 0 ? (
                                this.state.students.map((student) => (
                                    <tr key={student.id}>
                                        <td>{student.name}</td>
                                        <td>{student.lastname}</td>
                                        <td>{student.rut}</td>
                                        <td>{student.graduation_year}</td>
                                        <td>{student.school}</td>
                                        <td>{student.school_type}</td>
                                        <td>
                                            <button
                                                onClick={() => this.pagoContado(student.rut)}
                                                className="btn btn-info mr-2"
                                            >
                                                Pago Contado
                                            </button>
                                            <button
                                                onClick={() => this.pagoCuotas(student.rut)}
                                                className="btn btn-info mr-2"
                                            >
                                                Generar Cuotas
                                            </button>
                                            <button
                                                onClick={() => this.viewInstallments(student.rut)}
                                                className="btn btn-info mr-2"
                                            >
                                                Listar Cuotas
                                            </button>
                                            <button
                                                onClick={() => this.generateSpreadsheet(student.rut)}
                                                className="btn btn-info mr-2"
                                            >
                                                Generar Planilla
                                            </button>
                                            <button
                                                onClick={() => this.generateReport(student.rut)}
                                                className="btn btn-info"
                                            >
                                                Generar Reporte
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="7" className="text-center">
                                        No hay estudiantes disponibles.
                                    </td>
                                </tr>
                            )}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

export default ListStudentComponent;
