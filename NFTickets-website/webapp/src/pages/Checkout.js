import React, {useState} from 'react';
import {
  Container, Col,
  Button, Form, ButtonGroup, ToggleButton
} from "react-bootstrap";
const eventService = require('../services/eventService.js')

export default function Checkout(props) {
  const [name, setName] = useState("");
  const [desc, setDesc] = useState("");
  const [goal, setGoal] = useState("");
  // eslint-disable-next-line
  const [curr, setCurr] = useState("");
  const [acct, setAcct] = useState("");
  const [time, setTime] = useState("");
  const [checked, setChecked] = useState(false);
  const [radioValue, setRadioValue] = useState('1');

  const radios = [
    { name: 'USDC', value: '1' },
    { name: 'ALGO', value: '2' },
  ];

  return (
    <Container className="d-flex flex-column align-items-center">
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Checkout
      </h1>
      <h2 style={{textAlign: "center"}}>
          Order summary
      </h2>
      <h2 style={{textAlign: "center"}}>
          Pay with PayString
      </h2>
      <Form>
        <h5 className="d-flex justify-content-center">Your PayString</h5>
        <Form.Row className="d-flex justify-content-center">
          <Form.Group as={Col} controlId="formGridEventName">
            <Form.Control type="email" placeholder="alice@example.com" />
          </Form.Group>
        </Form.Row>
        <Form.Row className="d-flex justify-content-center">
          <ButtonGroup className="mb-2">
              {radios.map((radio, idx) => (
              <ToggleButton
                  key={idx}
                  id={`radio-${idx}`}
                  type="radio"
                  variant={idx % 2 ? 'outline-success' : 'outline-success'}
                  name="radio"
                  value={radio.value}
                  checked={radioValue === radio.value}
                  onChange={(e) => setRadioValue(e.currentTarget.value)}
              >
                  {radio.name}
              </ToggleButton>
              ))}
          </ButtonGroup>
        </Form.Row>
        <br />
        <Button variant="primary" type="submit">
          Checkout via PayString
        </Button>
      </Form>
    </Container>
  )
}