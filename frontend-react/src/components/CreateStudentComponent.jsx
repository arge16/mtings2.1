import React, { Component } from 'react'
import StudentService from '../services/StudentService';

class CreateStudentComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            rut: '',
            name: '',
            lastname: '',
            birthdate: '',
            graduation_year: '',
            school: '',
            school_type: ''
        }
        this.changeRutHandler = this.changeRutHandler.bind(this);
        this.changeNombreHandler = this.changeNombreHandler.bind(this);
        this.changeApellidoHandler = this.changeApellidoHandler.bind(this);
        this.changeNacimientoHandler = this.changeNacimientoHandler.bind(this);
        this.changeAnioEgresoHandler = this.changeAnioEgresoHandler.bind(this);
        this.changeColegioHandler = this.changeColegioHandler.bind(this);
        this.changeTipoColegioHandler = this.changeTipoColegioHandler.bind(this);
        this.saveStudent = this.saveStudent.bind(this);

    }

    saveStudent = (e) => {
        e.preventDefault();
        let student = {rut: this.state.rut,
                    name: this.state.name,
                    lastname: this.state.lastname,
                    birthdate: this.state.birthdate,
                    graduation_year: this.state.graduation_year,
                    school: this.state.school,
                    school_type: this.state.school_type};

        StudentService.createStudent(student).then(res =>{
            this.props.history.push('/students');
        });
    }
    
    changeRutHandler= (event) => {
        this.setState({rut: event.target.value});
    }

    changeNombreHandler= (event) => {
        this.setState({name: event.target.value});
    }

    changeApellidoHandler= (event) => {
        this.setState({lastname: event.target.value});
    }

    changeNacimientoHandler= (event) => {
        this.setState({birthdate: event.target.value});
    }

    changeAnioEgresoHandler= (event) => {
        this.setState({graduation_year: event.target.value});
    }

    changeColegioHandler= (event) => {
        this.setState({school: event.target.value});
    }

    changeTipoColegioHandler= (event) => {
        this.setState({school_type: event.target.value});
    }

    cancel(){
        this.props.history.push('/students');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Add Student</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> Rut: </label>
                                            <input placeholder="Rut" name="rut" className="form-control" 
                                                value={this.state.rut} onChange={this.changeRutHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Nombre: </label>
                                            <input placeholder="Nombre" name="nombre" className="form-control" 
                                                value={this.state.name} onChange={this.changeNombreHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Apellido: </label>
                                            <input placeholder="Apellido" name="apellido" className="form-control" 
                                                value={this.state.lastname} onChange={this.changeApellidoHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Fecha de nacimiento: </label>
                                            <input placeholder="Nacimiento" name="nacimiento" className="form-control" 
                                                value={this.state.birthdate} onChange={this.changeNacimientoHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Año egreso: </label>
                                            <input placeholder="Anio egreso" name="anio_egreso" className="form-control" 
                                                value={this.state.graduation_year} onChange={this.changeAnioEgresoHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Colegio: </label>
                                            <input placeholder="Colegio" name="colegio" className="form-control" 
                                                value={this.state.school} onChange={this.changeColegioHandler}/>
                                        </div>
                                        <div className = "form-group">
                                            <label> Tipo de colegio: </label>
                                            <input placeholder="Tipo colegio" name="tipo_colegio" className="form-control" 
                                                value={this.state.school_type} onChange={this.changeTipoColegioHandler}/>
                                        </div>
                                        <button className="btn btn-success" onClick={this.saveStudent}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default CreateStudentComponent