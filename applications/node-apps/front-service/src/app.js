import httpServer from './http/expressServer.js';
import {consume, TOPICS} from './kafka/kafkaConsumer.js';

const port = 3000;
httpServer.listen(port, () => {
  console.log(`Servidor escuchando en http://localhost:${port}`);

  console.log('Consumiendo mensajes de Kafka', TOPICS);
  consume(TOPICS).catch(e => console.error(`Error en el consumidor Kafka: ${e.message}`, e));

});

httpServer.on('error', (error) => {
  console.error('Error en el servidor HTTP:', error);
});
