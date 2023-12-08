import httpServer from './http/expressServer.js';
import consume from './kafka/kafkaConsumer.js';

const port = 3000;
httpServer.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);
  consume(['postgres-dbserver1.public.room']).catch(e => console.error(`Error en el consumidor Kafka: ${e.message}`, e));
});
