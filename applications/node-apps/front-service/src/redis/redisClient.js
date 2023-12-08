import redis from 'redis';

const client = redis.createClient({
  url: 'redis://localhost:6379',
  password: 'SUPER_SECRET_PASSWORD', 
});

client.on('error', (err) => console.log('Error de Redis', err));

await client.connect();

export default client;
