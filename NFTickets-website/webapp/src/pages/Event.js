import React, {useState, useEffect} from 'react';
import {
  Container, Col,
  Button, Form, Row
} from "react-bootstrap";
import {useParams, withRouter} from "react-router-dom";
const eventService = require('../services/eventService.js')
const dateFormat = require('dateformat');


export default function Event(props) {
  const { eventId } = useParams();

  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [venue, setVenue] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [zones, setZones] = useState([]);
  const [zone, setZone] = useState(0);
  const [seats, setSeats] = useState([]);
  const [seat, setSeat] = useState("");
  const [price, setPrice] = useState("");
  const [tickets, setTickets] = useState([])

  function onSubmit(e) {
    e.preventDefault();
    console.log(name, desc, startTime) // use these as fields
  }

  useEffect(() => {
    // call api to fetch details
    // eventId is the eventId
    eventService.getEvent(eventId, (data, err) => {
      if (err) {
        console.log(err)
      } else {
        console.log(data.startTime);
        console.log(data.createTime);
        setName(data.performance.name);
        setDesc(data.performance.description);
        let startTime = new Date(data.startTime);
        let endTime = new Date(data.endTime);
        console.log("end time:", dateFormat(endTime, "yyyy-mm-dd h:MM:ss"));
        setStartTime(dateFormat(startTime, "yyyy-mm-dd h:MM:ss"));
        setEndTime(dateFormat(endTime, "yyyy-mm-dd h:MM:ss"));
        setVenue(data.venue.name);
        setZones(data.zones);
        const list = []
        for (let i = 1; i <= data.zones[0].seats; i++) {
          list.push(i);
        }
        setSeats(list);
        setPrice(data.zones[0].price);
        setTickets([...tickets, {zone: data.zones[0].name, seats: list, price: data.zones[0].price}]);
      }
    })
  }, []);

  useEffect(() => {
    if (zone) {
      console.log("zone changed:", zones[zone].seats)
      const list = []
      for (let i = 1; i <= zones[zone].seats; i++) {
        list.push(i);
      }
      setSeats(list);
      setPrice(zones[zone].price);
    }
  }, [zone])

  function handleRemoveClick(index) {
    let list = [...tickets];
    list.splice(index, 1);
    setTickets(list);
  };

  function handleAddTicket() {
    console.log("tickets:", tickets)
    setTickets([...tickets, {zone: zones[0].name, seats: seats, price: zones[0].price}]);
  }

  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Event Details
      </h1>
      <Row>
        <Col>
            <p>{`Event: ${name}`}</p>
        </Col>
        <Col>
            <p>{`Start Time: ${startTime}`}</p>
        </Col>
      </Row>
      <Row>
        <Col>
            <p>{`Location: ${venue}`}</p>
        </Col>
        <Col>
            <p>{`End Time: ${endTime}`}</p>
        </Col>
      </Row>

      <h3>Description</h3>
      <p>{desc}</p>
      <Form onSubmit={onSubmit}>
        {tickets.map((ticket, i) => {
          return(
            <>
              <Form.Label>Seating Details</Form.Label>
              <Form.Row className="mb-3">
                  <Form.Group as={Col}>
                    <Form.Label>Select Zone</Form.Label>
                    <Form.Control as="select" onChange={(e) => setZone(e.target.value)}>
                        {Array.from(zones).map((zone, i) => {
                            return <option value={i}>{zone.name}</option>
                        })}
                    </Form.Control>
                  </Form.Group>
                  <Form.Group as={Col}>
                    <Form.Label>Select a Seat</Form.Label>
                    <Form.Control as="select" onChange={(e) => setSeat(e.target.value)}>
                      {seats.map((seat) => {
                            return <option >{seat}</option>
                        })}
                    </Form.Control>
                  </Form.Group>
                  <Form.Group as={Col} controlId="formGridLocation">
                    <Form.Label>Price (ALGO)</Form.Label>
                    <Form.Control type="number" value={price} disabled/>
                  </Form.Group>
                  {tickets.length !== 1 && <Button variant="outline-danger" size="sm" onClick={() => handleRemoveClick(i)}>Remove</Button>}
              </Form.Row>
              <Form.Row className="d-flex justify-content-center">
                {tickets.length - 1 === i && <Button variant="outline-success" onClick={handleAddTicket}>Add Ticket</Button>}
              </Form.Row>
            </>
          )
        })}
        <br />
        <Form.Row className="d-flex justify-content-center">
          <Button variant="primary" type="submit">
            Checkout
          </Button>
        </Form.Row>
      </Form>
    </Container>
  )
}