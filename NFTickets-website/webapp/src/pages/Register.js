import React, {useState} from 'react';
import {
  Container, Col,
  Button, Form
} from "react-bootstrap";
const eventService = require('../services/eventService.js')

export default function Register(props) {
  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [venue, setVenue] = useState("");
  const [venueDesc, setVenueDesc] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [zones, setZones] = useState([{name: "", seats: 0, price: 0}]);

  function onSubmit(e) {
    e.preventDefault();
    console.log(name, desc, venue, startTime, endTime, zones) // use these as fields
    const event = eventService.eventBuilder(name, desc, venue, venueDesc, startTime, endTime, zones);
    eventService.createEvent(event, ()=>{});
  }

  const handleInputChange = (e, index) => {
    const { name, value } = e.target;
    console.log(name)
    let list = [...zones];
    list[index][name] = value;
    setZones(list);
  };

  function handleRemoveClick(index) {
    let list = [...zones];
    list.splice(index, 1);
    setZones(list);
  };

  function handleAddZone() {
    console.log("Test")
    setZones([...zones, {name: "", seats: 0, price: 0}]);
  }

  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Register an Event
      </h1>
      <Form onSubmit={onSubmit}>
        <Form.Row className="mb-3">
          <Form.Group as={Col} controlId="formGridEventName">
            <Form.Label>Event Name</Form.Label>
            <Form.Control type="name" placeholder="Enter event name" onChange={(e) => setName(e.target.value)}/>
          </Form.Group>
          <Form.Group as={Col} controlId="formGridLocation">
            <Form.Label>Venue Location</Form.Label>
            <Form.Control type="location" placeholder="Enter event location" onChange={(e) => setVenue(e.target.value)} />
          </Form.Group>
        </Form.Row>
        <Form.Row className="mb-3">
          <Col>
            <Form.Group controlId="desc">
              <Form.Label>Event Description</Form.Label>
              <Form.Control as="textarea" rows="3" onChange={(e) => setDesc(e.target.value)}/>
            </Form.Group>
          </Col>
          <Col>
            <Form.Group controlId="desc">
              <Form.Label>Venue Description</Form.Label>
              <Form.Control as="textarea" rows="3" onChange={(e) => setVenueDesc(e.target.value)}/>
            </Form.Group>
          </Col>
        </Form.Row>
        <Form.Row className="mb-3">
          <Col>
            <Form.Label>Event Start Date</Form.Label>
            <Form.Control type="datetime-local" onChange={(e) => setStartTime(e.target.value)}/>
          </Col>
          <Col>
            <Form.Label>Event End Date</Form.Label>
            <Form.Control type="datetime-local" onChange={(e) => setEndTime(e.target.value)}/>
          </Col>
        </Form.Row>
        <br/>
        <h5 style={{textAlign: "center"}}>Seating Details</h5>
        <br/>
        {zones.map((zone, i) => {
          return(
            <>
            <Form.Row className="mb-3" xs="auto">
              <Form.Group as={Col} controlId="formGridEventName">
                <Form.Label>Zone</Form.Label>
                <Form.Control type="string" placeholder="Enter Zone" name="name" value={zone.zone} onChange={(e) => handleInputChange(e, i)}/>
              </Form.Group>
              <Form.Group as={Col} controlId="formGridEventName">
                <Form.Label>Seat Amount</Form.Label>
                <Form.Control type="number" placeholder="Enter Quantity" name="seats" value={zone.quantity} onChange={(e) => handleInputChange(e, i)}/>
              </Form.Group>
              <Form.Group as={Col} controlId="formGridLocation">
                <Form.Label>Price (ALGO)</Form.Label>
                <Form.Control type="number" placeholder="Price (ALGO)" name="price" value={zone.price} onChange={(e) => handleInputChange(e, i)}/>
              </Form.Group>
              {zones.length !== 1 && <Button variant="outline-danger" size="sm" onClick={() => handleRemoveClick(i)}>Remove</Button>}
            </Form.Row>
            <Form.Row className="d-flex justify-content-center">
              {zones.length - 1 === i && <Button variant="outline-success" onClick={handleAddZone}>Add Zones</Button>}
            </Form.Row>
            </>
          )
        })}
        
        <pre>
          {JSON.stringify(zones, null, 3)}
        </pre>
        <Form.Group controlId="desc">
          <Form.Label>Seat Mapping</Form.Label>
          <Form.Control as="textarea" rows="3" />
        </Form.Group>
        <Button variant="primary" type="submit">
          Create Event
        </Button>
      </Form>
    </Container>
  )
}