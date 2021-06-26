import React, {useEffect, useState} from 'react';
import {
  Container,
  Table
} from "react-bootstrap";
const fundService = require('../services/fundService.js')

export default function About(props) {

  const [funds, setFunds] = useState([]);

  useEffect(() => {
    // call api to fetch details
    // projectId is the projectId
    fundService.listFunds((data, err) => {
      if (err) {
        console.log(err)
      } else {
        setFunds(data)
      }
    })
  }, []);


  return (
    <Container>
      <h1 className="pt-2" style={{textAlign: "center"}}>
        Available Events
      </h1>
      <Table responsive>
        <thead>
        <tr>
          <th>Event</th>
          <th>Location</th>
          <th>Date/Time</th>
        </tr>
        </thead>
        <tbody>
        {funds.map((fund) => {
          return (
            <tr>
              <td><a href={`/project/${fund.id}`}>{fund.name}</a></td>
              <td>{`${fund.goalAmount} Algos`}</td>
            </tr>
           )
        })}
        </tbody>
      </Table>
    </Container>
  )
}