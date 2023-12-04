const { spawn } = require('child_process');

function exec(serviceName, command, cwd) {
    console.log(`Stated service [${serviceName}]`);
    let cmd = spawn(command, [], { cwd, shell: true });
    cmd.stdout.on('data', function (data) {
        process.stdout.write(`[${serviceName}] ${data}`);
    });
    cmd.stderr.on('data', function (data) {
        process.stderr.write(`[${serviceName}] ${data}`);
    });
    return cmd;
}

const services = new Map();

services.set(exec('housekepping-api-service', 'mvn spring-boot:run', './applications/java-apps/house-keeping-service'));
services.set(exec('externalservice2', 'node --require ./instrumentation.cjs app.js', './applications/node-apps/front-service'));


process.on('SIGINT', async () => {
    for (var [cmd] of services) {
        cmd.stdin.pause();
        await cmd.kill();
    }
    process.exit();
});
