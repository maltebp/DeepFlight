import React from "react";
import axios from 'axios';

class DownloadMenu extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            isToggleOn: true,
            message: "",
            user: "pilot"
        };
        this.handleDownload = this.handleDownload.bind(this);
    }

    downloadGame() {
        // source: https://medium.com/@drevets/you-cant-prompt-a-file-download-with-the-content-disposition-header-using-axios-xhr-sorry-56577aa706d6
        const url = "htto://maltebp.dk:10000:10000/gameapi/downloadgame";
        axios({
            method: 'get',
            url: url,
            responseType: 'arraybuffer',
            headers: {
                'Content-Type': 'application/zip',
                'Access-Control-Allow-Origin': url,
                'Accept': 'json/application',
                'Authorization': 'Bearer ' + localStorage.getItem("dftoken")
            },
            withCredentials: true
        })
            .then(response => {
                console.log(response)
                const url = window.URL.createObjectURL(new Blob([response.data], { type: "octet/stream" }));
                const link = document.createElement('a');
                link.href = url;
                link.setAttribute('download', 'DeepFlight.zip');
                document.body.appendChild(link);
                link.click();
            })
            .catch(error => {
                console.log("axios download results error", error);
                //TODO: Grade errors based on server reply
                this.setState(state => ({ message: "Download failed." }));
                setTimeout(() => { this.setState(state => ({ message: "" })); }, 4000);
            });
    }

    handleDownload() {
        this.setState(state => ({ isToggleOn: false }));
        this.setState(state => ({ message: "Starting download." }));
        this.downloadGame();
        setTimeout(() => { this.setState(state => ({ isToggleOn: true })); }, 1000);

    }

    render() {
        return (
            <div className="box">
                <h2>Hello, {this.state.user}!</h2>
                <p>You are logged in and can download the game from here.</p>
                <button type="button"
                    disabled={!this.state.isToggleOn}
                    onClick={this.handleDownload}>
                    {this.state.isToggleOn ? 'Download' : 'Please wait...'}
                </button>
                <p className="alert">{this.state.message}</p>
                <p>Size: 8.3 MB</p>
            </div>
        );
    }
}

export default DownloadMenu;