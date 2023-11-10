import React, { Component } from 'react'
import StudentService from '../services/StudentService'

class ViewStudentComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            student: {}
        }
    
    }

    componentDidMount(){
        StudentService.getStudentById(this.state.id).then( res => {
            this.setState({student: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Student Details</h3>
                    <div className = "card-body">
                        <div class="card-body">
                            <label> Student rut: </label>
                            <div class="card"> { this.state.student.rut }</div>
                        </div>
                        <div class="card-body">
                            <label> Book name: </label>
                            <div class="card"> { this.state.student.name }</div>
                        </div>
                        <div class="card-body">
                            <label> Student lastname: </label>
                            <div class="card"> { this.state.student.lastname }</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}

export default ViewStudentComponent
