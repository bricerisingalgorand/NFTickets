import React from 'react';
import {
  Container,
  Button,
} from "react-bootstrap";

import {Link} from "react-router-dom";

export default function About(props) {



  return (
    <Container className="d-flex flex-column align-items-center">
      <img className="w-25 pt-2" src="NFTickets.png" alt=''/>
        <p style={{fontSize: "30px", textAlign: "center"}}>A ticket management platform built on Algorand smart contract to enable the selling or purchasing of event tickets. </p>
      <Link to="/browse">
        <Button variant="primary" type="submit">
            Browse Events
        </Button>
      </Link>
    </Container>
  )
}