import React, {Component} from 'react';
import {Button, FormControl, Navbar} from "react-bootstrap";
import './AdminPanel.css';
import 'react-bootstrap-table-next/dist/react-bootstrap-table2.min.css';
import 'react-bootstrap-table2-paginator/dist/react-bootstrap-table2-paginator.min.css';
import 'react-bootstrap-table2-toolkit/dist/react-bootstrap-table2-toolkit.min.css';
import BootstrapTable from 'react-bootstrap-table-next';
import paginationFactory from 'react-bootstrap-table2-paginator';
import ToolkitProvider, {CSVExport} from 'react-bootstrap-table2-toolkit';
import axios from "axios";

const {ExportCSVButton} = CSVExport;


class AdminPanel extends Component {
    constructor(props) {
        super(props);
        this.state = {
            logs: []
        }
    }

    componentDidMount() {
        this.getLogs();
    }

    getLogs(event) {
        let body = {};
        if (event !== undefined) {
            if (event.target.value.trim() !== "") {
                body = {
                    search: event.target.value.trim()
                }
            }
        }
        axios.post("http://localhost:8000/auditability/get", body).then(success => {
            this.setState({
                logs: success.data
            })
        })
    }

    render() {
        const columns = [{
            dataField: 'username',
            text: 'Username',
            sort: true
        }, {
            dataField: 'ip',
            text: 'IP',
            sort: true
        }, {
            dataField: 'date',
            text: 'Time of the event',
            sort: true
        }, {
            dataField: 'msg',
            text: 'Message',
            sort: true
        }];
        return (
            <div className="admin-panel">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Admin Panel</Navbar.Brand>
                    <Button variant="danger">Auditability</Button>
                </Navbar>
                <div className="table-container">


                    <ToolkitProvider
                        bootstrap4
                        keyField="id"
                        data={this.state.logs}
                        columns={columns}
                        >
                        {props => (
                            <div>
                                <ExportCSVButton className ="btn btn-success" style = {{marginBottom : 4}} {...props.csvProps}>Export CSV</ExportCSVButton>

                                <FormControl
                                    style = {{marginBottom : 4}}
                                    type='text'
                                    name='search'
                                    placeholder='Search'
                                    onChange={this.getLogs.bind(this)}
                                />
                                <BootstrapTable{...props.baseProps}
                                               pagination={ paginationFactory() } />
                            </div>
                        )}
                    </ToolkitProvider>
                </div>
            </div>
        )
            ;
    }
}

export default AdminPanel;