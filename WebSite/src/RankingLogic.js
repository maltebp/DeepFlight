import axios from 'axios';

export function getRankFromServer(setState) {
    const BASE_URL = "http://maltebp.dk:10000/gameapi/";
    const rankUrl = BASE_URL + "rankings/universal";
    const scoreUrl = BASE_URL + "round/previous";
    axios({
        method: 'get',
        url: rankUrl,
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': rankUrl,
            'Accept': 'application/json',
            //'Authorization': localStorage.getItem("dftoken")
        },
        withCredentials: true
    })
        .then(response => {
            const ranks = response.data;
            ranks.sort((a, b) => b.rating - a.rating);
            //console.log(ranks)
            let universal = [];
            // Show null users
            for (var i = 0; i < 5; i++) {
                if (ranks[i] != null) {
                    universal.push({
                        rank: i + 1,
                        name: ranks[i].username,
                        rating: parseFloat(ranks[i].rating).toFixed(1)
                    });
                } else {
                    universal.push({ rank: i + 1 });
                }
            }
            setState({ "universal": universal });
        })
        .catch(error => {
            console.log("error downloading ranks", error);
        });

    axios({
        method: 'get',
        url: scoreUrl,
        headers: {
            'Content-Type': 'application/json',
            'Access-Control-Allow-Origin': scoreUrl,
            'Accept': 'application/json',
            //'Authorization': localStorage.getItem("dftoken")
        },
        withCredentials: true
    })
        .then(response => {
            // Sort and list objects
            const rankings = response.data.rankings;
            const entries = Object.entries(rankings)
            let objects = [];
            for (let item in entries) {
                objects[item] = { "name": entries[item][0], "rating": entries[item][1] }
            }
            objects.sort((a, b) => b.rating - a.rating);

            //console.log(objects);
            let lastRound = [];
            // Show null users
            for (var i = 0; i < 5; i++) {
                if (entries[i] != null) {
                    lastRound.push({
                        rank: i + 1,
                        name: objects[i].name,
                        rating: parseFloat(objects[i].rating).toFixed(1)
                    });
                } else if (i === 0) {
                    lastRound.push({ rank: "No data from last round." });
                } else {
                    lastRound.push({ rank: "_ _ _" });
                }
            }
            setState({ "lastRound": lastRound });
        })
        .catch(error => {
            console.log("error downloading scores", error);
        });
}