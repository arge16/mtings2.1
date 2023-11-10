import React, { Component } from 'react'
import InstallmentService from '../services/InstallmentService'

class ViewInstallmentsComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            rut: this.props.match.params.rut,
            installments: []
        }
    }

    markPaid(id) {
        InstallmentService.markPaid(id)
            .then(response => {
                alert("La cuota ha sido marcada como pagada.");
                window.location.reload(); // Recargar la página
            })
            .catch(error => {
                console.error("Error al marcar la cuota como pagada:", error);
            });
    }

    componentDidMount(){
        InstallmentService.getInstallments(this.state.rut).then((res) => {
            this.setState({ installments: res.data});
        });
    }

    render() {
        return (
            <div>
                <br></br>
                 <h2 className="text-center">Cuotas</h2>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Numero de Cuota</th>
                                    <th> Monto de Cuota</th>
                                    <th> Fecha de Pago</th>
                                    <th> Descuento</th>
                                    <th> Interés</th>
                                    <th> Rut</th>
                                    <th> Status</th>
                                    <th> Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.installments.map(
                                        (installment, index) => 
                                        <tr key = {installment.id}>
                                            <td> Cuota número {index + 1} </td> 
                                             <td> {installment.amount}</td>
                                             <td> {installment.due_date}</td>
                                             <td> {installment.discount} </td>   
                                             <td> {installment.interest}</td>
                                             <td> {installment.rut}</td>
                                             <td> {installment.status}</td>
                                             <td>
                                                 <button style={{marginLeft: "10px"}} onClick={() => this.markPaid(installment.id)} className="btn btn-info">Pagar</button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ViewInstallmentsComponent
