import { Redis } from 'ioredis';

const client = new Redis({
  url: 'redis://localhost:6379',
  username: "default",
  password: 'SUPER_SECRET_PASSWORD',
  port: 6379,
  host: "127.0.0.1",
  db: 1
});

client.on('error', (err) => console.log('Error de Redis', err));

export default client;

