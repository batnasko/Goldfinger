import React, {Component} from 'react';
import {Map, TileLayer, GeoJSON} from "react-leaflet";
import axios from "axios"
import {Navbar, Dropdown, Button} from 'react-bootstrap';
import "./WorldMap.css"


class WorldMap extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dataTypes: [],
            currentDataType: {
                id: 0,
                dataType: "Select data type",
                rowToColor: null,
                dataProperties: []
            },
            shapes: []
        };
    }

    componentDidMount() {
        axios.get("http://localhost:9000/map/datatype").then(response => {
            response.data.map((dataType) => {
                axios.get("http://localhost:9000/map/datatype/" + dataType.id + "/property").then(response => {
                    dataType["properties"] = response.data;

                    this.setState(prevState => ({
                        dataTypes: [...prevState.dataTypes, dataType]
                    }))
                })
            });
        });
    }

    changeDataType(dataType) {
        this.setState({
            currentDataType: {
                id: dataType.id,
                dataType: dataType.dataType,
                dataProperties: dataType.properties,
                rowToColor: dataType.rowToColor
            }
        });
        this.clearMap();
    }

    clearMap() {
        this.setState({
            shapes: []
        })
    }

    getShape(e){
        axios.post("http://localhost:9000/map/shape/"+this.state.currentDataType.id, {
            latitude : e.latlng.lng,
            longitude : e.latlng.lat
        }).then(response =>{
            this.setState({
                shapes : [...this.state.shapes, response.data]
            })
        })
    }

    stringToColor(str) {
        for (var i = 0, hash = 0; i < str.length; hash = str.charCodeAt(i++) + ((hash << 5) - hash));
         let color = Math.floor(Math.abs((Math.sin(hash) * 10000) % 1 * 16777216)).toString(16);
        return '#' + Array(6 - color.length + 1).join('0') + color;
    }

    render() {
        let marginNavItems = {
            marginLeft: 10,
            marginRight: 10
        };
        return (
            <div className="map-container">
                <Navbar expand="lg" variant="dark" bg="dark">
                    <Navbar.Brand>Map Options</Navbar.Brand>
                    <Button style={marginNavItems} variant="danger" onClick={e => this.clearMap()}>Clear Map</Button>
                    <Dropdown style={marginNavItems}>
                        <Dropdown.Toggle variant="danger" id="dropdown-basic-button">
                            {this.state.currentDataType.dataType}
                        </Dropdown.Toggle>
                        <Dropdown.Menu>
                            {this.state.dataTypes.map((dataType) =>
                                <Dropdown.Item key={dataType.id}
                                               onClick={() => this.changeDataType(dataType)}>{dataType.dataType}</Dropdown.Item>
                            )}
                        </Dropdown.Menu>
                    </Dropdown>
                </Navbar>
                <Map onClick={this.getShape.bind(this)} className="map"
                     center={[42.65, 23.37]}
                     zoom={6}>
                    <TileLayer
                        attribution='&copy; <a href="http://osm.org/copyright">OpenStreetMap</a> contributors'
                        url='http://{s}.tile.osm.org/{z}/{x}/{y}.png'
                    />
                    {this.state.shapes.map((shape, idx) =>
                        <GeoJSON color={this.stringToColor(shape.properties[this.state.currentDataType.rowToColor])}
                                 key = {"shape-"+idx}
                            data={shape.geometry}/>
                    )}
                </Map>
            </div>
        );
    }
}

export default WorldMap;
