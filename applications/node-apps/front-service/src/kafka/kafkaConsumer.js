import kafka from './kafkaClient.js';
import handleRoomEvent from '../handlers/roomEventHandler.js';
//import handleOtherTableEvent from '../handlers/otherTableEventHandler.js';

const consumer = kafka.consumer({ groupId: 'front-grupo' });

const consume = async (topics) => {
  await consumer.connect();
  await Promise.all(topics.map(topic => consumer.subscribe({ topic, fromBeginning: true })));

  await consumer.run({
    eachMessage: async ({ topic, partition, message }) => {
      console.log(`Recibido mensaje en ${topic}: ${message.value ? message.value.toString() : 'No value, probably tombstone'}`);
      if (message.value === null) {
        return; // no process tombstone
      } const event = JSON.parse(message.value.toString());
      switch (topic) {
        case 'postgres-dbserver1.public.room':
          handleRoomEvent(event);
          break;
        default:
          console.log('Tópico no reconocido:', topic);
      }
    },
    autoCommitThreshold: 50,
  });
};

export default consume;
