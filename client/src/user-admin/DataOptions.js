import React, {Component} from 'react';
import {Tab, Row, Col, Nav} from 'react-bootstrap';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel'
import FormControl from '@material-ui/core/FormControl'
import Checkbox from '@material-ui/core/Checkbox';
import Radio from '@material-ui/core/Radio'
import RadioGroup from '@material-ui/core/RadioGroup'
import axios from 'axios';

import "./DataOptions.css";
import date from "../common/date";


class DataOptions extends Component {
    constructor(props) {
        super(props);
        this.state = {
            rowToColor: "",


            showTables: false,
            dataTypes: [],
            currentDataType: 0,
            currentDataTypeName: "",
            currentDataOptions: {
            },

        };
    }

    componentDidMount() {
        axios.get("http://localhost:9000/map/datatype", {
            headers: {
                Authorization: "Bearer " + this.props.user.token
            }
        }).then(response => {
            this.setState({
                dataTypes: response.data
            })
        });
    }

    handleRadioChange = event => {
        axios.put("http://localhost:9000/map/datatype", {
            id: this.state.currentDataType,
            rowToColor: event.target.value
        }, {
            headers: {
                Authorization: "Bearer " + this.props.user.token
            }
        });

        axios.post("http://localhost:8000/auditability", {
            "username": this.props.user.userDetails.username,
            "ip": this.props.user.ip,
            "date": date(),
            "msg": "Set row to color " + event.target.value + " for "+this.state.currentDataTypeName,
            "dataType": this.state.currentDataTypeName
        });

        this.setState({rowToColor: event.target.value});
    };

    handleCheckChange = (name) => {
        let currDataOpt = this.state.currentDataOptions;
        currDataOpt[name] = !currDataOpt[name];

        axios.put("http://localhost:9000/map/datatype/property", {
            dataTypeId: this.state.currentDataType,
            properties: name,
            show: currDataOpt[name]
        }, {
            headers: {
                Authorization: "Bearer " + this.props.user.token
            }
        });
        let msg = currDataOpt[name]? "show":"hide";
        axios.post("http://localhost:8000/auditability", {
            "username": this.props.user.userDetails.username,
            "ip": this.props.user.ip,
            "date": date(),
            "msg": "Set " + name + " for "+this.state.currentDataTypeName +" to " + msg,
            "dataType": this.state.currentDataTypeName
        });

        this.setState({currentDataOptions: currDataOpt});
    };

    changeDataType(dataType) {
        let data = "";

        this.state.dataTypes.forEach(dataTypeFromState =>{
            if (dataTypeFromState.id === dataType){
                data = dataTypeFromState.dataType
            }
        });

        this.setState({
            showTables: true,
            currentDataType: dataType,
            currentDataTypeName : data,
            currentDataOptions: {}
        });


        axios.get("http://localhost:9000/map/datatype/" + dataType + "/property", {
            headers: {
                Authorization: "Bearer " + this.props.user.token
            }
        }).then(response => {
            response.data.map(property => {
                this.addDataType(property);
            })
        });

        axios.get("http://localhost:9000/map/datatype/" + dataType, {
            headers: {
                Authorization: "Bearer " + this.props.user.token
            }
        }).then(response => {
            this.setState({
                rowToColor: response.data.rowToColor
            })
        })
    }

    addDataType(property) {
        let curDataOpt = this.state.currentDataOptions;
        curDataOpt[property.properties] = property.show;
        this.setState({
            currentDataOptions: curDataOpt
        })
    }

    render() {
        return (
            <div>
                <Tab.Container id="data-types">
                    <Row>
                        <Col sm={3}>
                            <Nav variant="pills" className="flex-column">
                                {this.state.dataTypes.map(dataType => <Nav.Item
                                    onClick={e => this.changeDataType(dataType.id)}><Nav.Link
                                    eventKey={dataType.id}>{dataType.dataType} </Nav.Link></Nav.Item>)}
                            </Nav>
                        </Col>
                        <Col sm={4}>
                            <Tab.Content>
                                <Tab.Pane eventKey={this.state.currentDataType}>
                                    <div className="data-option-title">
                                        <div className="data-option-title" style={{"font-size": "18px"}}>
                                            Columns to show
                                        </div>
                                        <div style={{"text-align": "left", "padding-left": "10px"}}>
                                            <FormControl component="fieldset">
                                                <FormGroup>
                                                    {
                                                        Object.keys(this.state.currentDataOptions).map(key =>
                                                            <FormControlLabel
                                                                control={
                                                                    <Checkbox
                                                                        checked={this.state.currentDataOptions[key]}
                                                                        onChange={e => this.handleCheckChange(key)}
                                                                        value={this.state.currentDataOptions[key]}
                                                                        style={{
                                                                            color: "#dc3545"
                                                                        }}
                                                                    />
                                                                }
                                                                label={key}
                                                            />)
                                                    }
                                                </FormGroup>
                                            </FormControl>
                                        </div>
                                    </div>
                                </Tab.Pane>
                            </Tab.Content>
                        </Col>
                        <Col sm={4}>
                            <Tab.Content>
                                <Tab.Pane eventKey={this.state.currentDataType}>
                                    <div className="data-option-title">
                                        <div className="data-option-title" style={{"font-size": "18px"}}>
                                            Column to color
                                        </div>
                                        <div style={{"text-align": "left", "padding-left": "10px"}}>
                                            <FormControl component="fieldset">
                                                <RadioGroup
                                                    aria-label="Row to color"
                                                    name="rowToColor"
                                                    value={this.state.rowToColor}
                                                    onChange={this.handleRadioChange}
                                                >
                                                    {Object.keys(this.state.currentDataOptions).map(key =>
                                                        <FormControlLabel
                                                            value={key}
                                                            control={
                                                                <Radio style={{
                                                                    color: "#dc3545"
                                                                }}/>
                                                            }

                                                            label={key}
                                                        />)}
                                                </RadioGroup>
                                            </FormControl>
                                        </div>
                                    </div>
                                </Tab.Pane>
                            </Tab.Content>
                        </Col>
                    </Row>
                </Tab.Container>
            </div>
        );
    }
}

export default DataOptions;