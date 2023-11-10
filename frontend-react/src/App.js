import logo from './logo.svg';
import './App.css';

import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListStudentComponent from './components/ListStudentComponent';
import HeaderComponent from './components/HeaderComponent';
import CreateStudentComponent from './components/CreateStudentComponent';
import ViewStudentComponent from './components/ViewStudentComponent';
import ViewInstallmentsComponent from './components/ViewInstallmentsComponent';
import CreateInstallmentsComponent from './components/CreateInstallmentsComponent';
import LoadCsvComponent from './components/LoadCsvComponent';
import GenerateReportComponent from './components/GenerateReportComponent';



function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {ListStudentComponent}></Route>
                          <Route path = "/students" component = {ListStudentComponent}></Route>
                          <Route path = "/add-student" component = {CreateStudentComponent}></Route>
                          <Route path = "/view-student/:id" component = {ViewStudentComponent}></Route>
                          <Route path = "/view-installments/:rut" component = {ViewInstallmentsComponent}></Route>
                          <Route path = "/create-installments/:rut" component = {CreateInstallmentsComponent}></Route>
                          <Route path = "/load-csv" component = {LoadCsvComponent}></Route>
                          <Route path = "/view-report/:rut" component = {GenerateReportComponent}></Route>
                    </Switch>
                </div>
        </Router>
    </div>
    
  );
}

export default App;
