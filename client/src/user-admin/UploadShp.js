import React, {Component} from 'react';
import {Button, Form, Col, InputGroup, Alert} from "react-bootstrap";
import axios from "axios";
import Spinner from "../common/spinner.js";
import date from "../common/date";


class UploadShp extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: false,
            isFileUploading: false,
            isInvalidFile: false,
            invalidFileMsg: "Upload shape file",
            alert: {
                show: false,
                msg: ""
            }
        }
    }

    showAlert= (msg)=> {
        this.setState({
            alert: {
                show: true,
                msg: msg
            }
        })
    };

    hideAlert = () => {
        this.setState({
            alert: {
                show: false
            }
        })
    };

    hideSpinner = () => {
        this.setState({
            isFileUploading: false,
            validated: false
        })
    };

    uploadShp(event) {
        event.preventDefault();
        this.setState({
            validated: true,

        });


        const form = event.currentTarget;
        let returnToMap = this.props.showMap;
        let showAlert = this.showAlert;
        let hideSpinner = this.hideSpinner;
        if (this.state.isInvalidFile) {
            return;
        }

        if (form.checkValidity() === true) {
            this.setState({
                isFileUploading: true
            });
            let token = this.props.user.token;
            var reader = new FileReader();
            reader.readAsDataURL(form.shapeFile.files[0]);
            let user = this.props.user;
            reader.onload = function () {
                let data = {
                    "file": reader.result,
                    "shpFileName": form.shapeType.value,
                    "columnsToShow": form.columnsToDisplay.value,
                    "columnToColor": form.columnToColor.value
                };
                axios.post("http://localhost:9000/map/upload", data, {
                    headers: {
                        Authorization: "Bearer " + token
                    }
                }).then(response => {
                    axios.post("http://localhost:8000/auditability", {
                        "username": user.userDetails.username,
                        "ip": user.ip,
                        "date": date(),
                        "msg": "Uploaded " + data.shpFileName,
                        "dataType": data.shpFileName
                    });
                    returnToMap()
                }, error => {
                    hideSpinner();
                    showAlert(error.response.data.message);
                });
            };
        }
    }

    handleFileChange(e) {
        if (!e.target.value.endsWith(".zip")) {
            this.setState({
                isInvalidFile: true,
                invalidFileMsg: "Please upload .zip file"
            })
        } else {
            this.setState({
                isInvalidFile: false
            })
        }
    }

    showContent() {
        if (!this.state.isFileUploading) {
            return <div>
                <Alert style={{marginBottom: 20}} show={this.state.alert.show} variant="danger">
                    <p>{this.state.alert.msg}</p>
                    <div>
                        <Button variant="outline-danger" onClick={this.hideAlert}>
                            Alright!
                        </Button>
                    </div>
                </Alert>
                <Form
                    noValidate
                    validated={this.state.validated}
                    onSubmit={e => this.uploadShp(e)}
                >

                    <Form.Row>
                        <Form.Group as={Col} md="4" controlId="shapeFile" onChange={e => this.handleFileChange(e)}>
                            <Form.Control
                                required
                                type="file"
                                placeholder="Shape file"
                                style={{display: "none"}}
                                ref={fileInput => this.fileInput = fileInput}
                                isInvalid={this.state.isInvalidFile}
                            />
                            <Button style={{display: "block"}} variant="success" onClick={() => this.fileInput.click()}>Select
                                file</Button>
                            <Form.Control.Feedback type="invalid">{this.state.invalidFileMsg}</Form.Control.Feedback>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="4" controlId="shapeType">
                            <Form.Label>Shape file name</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Shape file type"
                            />
                            <Form.Control.Feedback type="invalid">Enter shape file type</Form.Control.Feedback>
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="6" controlId="columnsToDisplay">
                            <Form.Label>Columns to display</Form.Label>
                            <Form.Control type="text" placeholder="Enter columns separated by whitespaces" required/>
                            <Form.Control.Feedback type="invalid">
                                Please enter columns
                            </Form.Control.Feedback>
                        </Form.Group>
                        <Form.Group as={Col} md="3" controlId="columnToColor">
                            <Form.Label>Column to color</Form.Label>
                            <Form.Control type="text" placeholder="Column to color" required/>
                            <Form.Control.Feedback type="invalid">
                                Please enter column to color on the map
                            </Form.Control.Feedback>
                        </Form.Group>
                    </Form.Row>
                    <Button type="submit" variant="danger">Upload</Button>
                </Form>
            </div>
        } else {
            return <Spinner/>
        }
    }

    render() {
        return (
            <div>
                {this.showContent()}
            </div>
        );
    }
}

export default UploadShp;