const { spawn } = require('child_process');

const tasks = [
    {
        name: 'house-keeping-DB',
        upCommand: 'docker-compose -f infrastructure/docker/database/docker-compose.yaml up',
        downCommand: 'docker-compose -f infrastructure/docker/database/docker-compose.yaml down -v'
    },
    {
        name: 'performanceTest',
        upCommand: 'docker-compose -f infrastructure/docker/performanceTest/locust/docker-compose.yaml up',
        downCommand: 'docker-compose -f infrastructure/docker/performanceTest/locust/docker-compose.yaml down -v'
    },
    {
        name: 'observability',
        upCommand: 'docker-compose -f infrastructure/docker/observability/signoz/docker/clickhouse-setup/docker-compose.yaml up',
        downCommand: 'docker-compose -f infrastructure/docker/observability/signoz/docker/clickhouse-setup/docker-compose.yaml down -v'
    }
];

function execTask(task) {
    console.log(`Starting docker/podman image for [${task.name}]`);
    console.log(`Command: ${task.upCommand}`);
    console.log("");

    const process = spawn(task.upCommand, [], {
        shell: true,
        stdio: 'inherit'
    });

    return {
        process,
        downCommand: task.downCommand
    };
}

function showHeader() {
    console.log("#".repeat(64));
    console.log("#" + " ".repeat(62) + "#");
    console.log("#" + " ".repeat(20) + "STARTING INFRASTRUCTURE" + " ".repeat(19) + "#");
    console.log("#" + " ".repeat(62) + "#");
    console.log("#".repeat(64));
    console.log("");
}

showHeader();
const processes = tasks.map(execTask);

function shutdown() {
    console.log("\nShutting down...");
    processes.forEach(task => {
        if(task.downCommand){
            console.log(`Shutting down [${task.downCommand}]`);
            spawn(task.downCommand, [], {
                shell: true,
                stdio: 'inherit'
            });
        }
        task.process.kill();
    });

    setTimeout(() => process.exit(0), 20000);//20 seconds
}

// Captura la señal de Ctrl + C
process.on('SIGINT', () => {
    console.log("\nReceived Ctrl + C...");
    shutdown();
});
console.log("Press Ctrl + C to stop");