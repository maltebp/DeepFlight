import axios from 'axios';

const base_url = "http://maltebp.dk:10000/gameapi/"

export function getPlanetStats(setState) {
    const url = base_url + "round/current";
    axios({
        method: 'get',
        url: url,
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': url,
            'Accept': 'application/json',
        },
        withCredentials: true
    })
        .then(response => {
            const trackIds = response.data.trackIds;
            console.log(response.data.endDate)
            getCurrentStats(trackIds, setState, response.data.endDate)

        })
        .catch(error => {
            console.log("error downloading current round for planet stats", error);
        });
}

function getCurrentStats(trackIds, setState, date) {
    let tracks = [];

    var timeLeft = (Math.abs(new Date(date)-new Date()) / 60000);
    console.log(timeLeft + " minutes left.")

    for (let i in trackIds) {
        const url = base_url + "track/" + trackIds[i];
        //console.log(url);
        axios({
            method: 'get',
            url: url,
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Origin': url,
                'Accept': 'application/json',
            },
            withCredentials: true
        })
            .then(response => {
                //console.log(response)
                let times = []
                const entries = Object.entries(response.data.times)
                let objects = [];
                for (let item in entries) {
                    objects[item] = { "name": entries[item][0], "time": entries[item][1] }
                }
                objects.sort((a, b) => a.time - b.time);

                //console.log(objects)
                for (let i = 0; i < 5; i++) {
                    if (objects[i] === null || objects[i] === undefined) {
                        times[i] = {name: "-", time: " "};           
                    } else {
                        times[i] = objects[i];
                    }
                }

                tracks[i] = { name: response.data.name, "times": times }
                // Assert all tracks present before updating state
                if (tracks.length === 4) {
                    setState({ tracks: tracks });
                    //console.log(tracks)
                }
                
            })
            .catch(error => {
                console.log("error downloading track stats", error);
            });
    }
}