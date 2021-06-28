const axios = require('axios')
const APP_SERVICE_URL = 'http://localhost:8080'

const getEvent = function (eventId, callback) {
  axios.get(`${APP_SERVICE_URL}/event/${eventId}`).then(
    (result) => {
      callback(JSON.flatten(JSON.parse(JSON.stringify(result.data))), null);
    })
    .catch((error) => {
      callback(null, error);
    });
}
JSON.flatten = function(data) {
  var result = {};
  function recurse (cur, prop) {
      if (Object(cur) !== cur) {
          result[prop] = cur;
      } else if (Array.isArray(cur)) {
           for(var i=0, l=cur.length; i<l; i++)
               recurse(cur[i], prop ? prop+"."+i : ""+i);
          if (l == 0)
              result[prop] = [];
      } else {
          var isEmpty = true;
          for (var p in cur) {
              isEmpty = false;
              recurse(cur[p], prop ? prop+"."+p : p);
          }
          if (isEmpty)
              result[prop] = {};
      }
  }
  recurse(data, "");
  return result;
}

const flattenElements = function(json_list) {
  for (var i = 0; i < json_list.length; i++) { 
    json_list[i] = JSON.flatten(json_list[i]);
  }
}

const getEvents = function (callback) {
  axios.get(`${APP_SERVICE_URL}/event`).then(
    (result) => {
      let data = JSON.parse(JSON.stringify(result.data))
      flattenElements(data.contents)
      callback(data, null);
    })
    .catch((error) => {
      callback(null, error);
    });
}

/* Example Event
{
  "startTime": "2021-06-27T18:59:29.837Z",
  "endTime": "2021-06-27T18:59:29.837Z",
  "performance": {
    "name": "string",
    "description": "string"
  },
  "venue": {
    "name": "string",
    "description": "string"
  },
  "zones": [
    {
      "name": "string",
      "seats": 0,
      "price": 0
    }
  ]
}
 */

const createEvent = function (event, callback) {
  axios.post(`${APP_SERVICE_URL}/event`, event, {
    headers: {
      'Content-Type': 'application/json'
    }
  })
  .then((result) => {
      callback(result.data, null);
    })
    .catch((error) => {
      callback(null, error);
    });
}

module.exports = {
  getEvent,
  getEvents,
  createEvent,
}
