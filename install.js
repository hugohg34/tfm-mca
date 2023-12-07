const { spawnSync } = require('child_process');

function exec(serviceName, command, cwd) {
  console.log(`Installing dependencies for [${serviceName}]`);
  console.log(`Folder: ${cwd} Command: ${command}`);
  spawnSync(command, [], {
    cwd,
    shell: true,
    stdio: 'inherit'
  });
}

exec('front-service-vue', 'npm install', 'applications/node-apps/front-service/frontend');
exec('front-service-vue', 'npm run build', 'applications/node-apps/front-service/frontend');
exec('front-service', 'npm install', 'applications/node-apps/front-service');
