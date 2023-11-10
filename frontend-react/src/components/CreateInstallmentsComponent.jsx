import React, { Component } from 'react';
import InstallmentService from '../services/InstallmentService';

class GenerateInstallmentsComponent extends Component {
    constructor(props) {
        super(props);

        this.state = {
            rut: this.props.match.params.rut,
            limit: 0, // Límite máximo de cuotas
        };

        this.handleInputChange = this.handleInputChange.bind(this);
        this.generateInstallments = this.generateInstallments.bind(this);
    }

    handleInputChange(event) {
        const { name, value } = event.target;
        this.setState({
            [name]: value,
        });
    }

    componentDidMount() {
        InstallmentService.getLimit(this.state.rut).then((res) => {
            this.setState({ limit: res.data });
        });
    }

    generateInstallments(event, rut, limit) {
        event.preventDefault(); // Evitar que el formulario se envíe por defecto

        InstallmentService.createCuotas(rut, limit)
            .then(response => {
                alert("Se han generado las cuotas.");
            })
            .catch(error => {
                console.error("Error al generar las cuotas:", error);
            });
    }

    render() {
        return (
            <div>
                <br></br>
                <h1>Generar Cuotas</h1>
                <form onSubmit={(e) => this.generateInstallments(e, this.state.rut, this.state.limit)}>
                    <label htmlFor="cantidadCuotas">Cantidad de Cuotas:</label>
                    <input
                        type="number"
                        id="cantidadCuotas"
                        name="numberOfInstallments"
                        min="2"
                        max={this.state.limit}
                        required
                        value={this.state.numberOfInstallments}
                        onChange={this.handleInputChange}
                    />
                    <input type="submit" value="Generar Cuotas" />
                </form>
            </div>
        );
    }
}

export default GenerateInstallmentsComponent;
