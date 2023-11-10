import React, { Component } from 'react'

class HeaderComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                 
        }
    }

    render() {
        return (

            <div>  
            <header class="header">
                <div class="logo">
                    <h1>TopEducation</h1>
                </div>
                <nav>
                </nav>
                <a class="btn" href="/"><button>Volver al menú principal</button></a>
                <a class="btn" onClick={this.addStudent}><button>Ingresar nuevo Estudiante</button></a>
            </header>
            </div>
        )
    }
}

export default HeaderComponent
